/**
 * 
 */
package com.siriuserp.inventory.service;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.siriuserp.inventory.criteria.StockAdjustmentFilterCriteria;
import com.siriuserp.inventory.dm.DWInventoryItemBalance;
import com.siriuserp.inventory.dm.DWInventoryItemBalanceDetail;
import com.siriuserp.inventory.dm.InventoryConfiguration;
import com.siriuserp.inventory.dm.InventoryItem;
import com.siriuserp.inventory.dm.InventoryType;
import com.siriuserp.inventory.dm.ProductInOutTransaction;
import com.siriuserp.inventory.dm.StockAdjustment;
import com.siriuserp.inventory.dm.StockAdjustmentItem;
import com.siriuserp.inventory.dm.StockAdjustmentItemStockableBridge;
import com.siriuserp.inventory.dm.StockControl;
import com.siriuserp.inventory.dm.StockControlType;
import com.siriuserp.inventory.form.InventoryForm;
import com.siriuserp.inventory.util.InventoryBalanceDetailUtil;
import com.siriuserp.inventory.util.InventoryBalanceUtil;
import com.siriuserp.inventory.util.InventoryItemTagUtil;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.ExchangeType;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.DecimalHelper;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastMap;

/**
 * @author ferdinand
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class StockAdjustmentService 
{
	@Autowired
	private GenericDao genericDao;
	
	@Autowired
	private CodeSequenceDao codeSequenceDao;
	
	@Autowired
	private InventoryBalanceUtil balanceUtil;
	
	@Autowired
	private InventoryItemTagUtil inventoryUtil;
	
	@Autowired
	private StockControlService stockControlService;
	
	@Autowired
	private InventoryBalanceDetailUtil balanceDetailUtil;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();

		StockAdjustmentFilterCriteria criteria = (StockAdjustmentFilterCriteria) filterCriteria;

		map.put("filterCriteria", filterCriteria);
		map.put("adjustments", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));
		map.put("org", SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()) ? genericDao.load(Party.class, criteria.getOrganization()) : null);
		map.put("facility", SiriusValidator.validateParamWithZeroPosibility(criteria.getFacility()) ? genericDao.load(Facility.class, criteria.getFacility()) : null);
		
		return map;
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@InjectParty(keyName = "adjustment_add")
	public Map<String, Object> preadd() throws ServiceException
	{
		Map<String, Object> map = new FastMap<String, Object>();

		map.put("adjustment_add", new InventoryForm());
		map.put("currencies", genericDao.loadAll(Currency.class));

		return map;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preedit(Long id) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("adjustment_edit", load(id));

		return map;
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public StockAdjustment load(Long id)
	{
		return genericDao.load(StockAdjustment.class, id);
	}
	
	@AuditTrails(className = StockAdjustment.class, actionType = AuditTrailsActionType.CREATE)
//	@AutomaticPosting(roleClasses = StockAdjustmentPostingRole.class)
	public void add(StockAdjustment stockAdjustment) throws Exception
	{
		Assert.notEmpty(stockAdjustment.getForm().getItems(), "Empty item transaction, please recheck !");
		
		InventoryConfiguration configuration = genericDao.load(InventoryConfiguration.class, Long.valueOf(1));
		Assert.notNull(configuration, "Inventory configuration does not exist !");
		
		stockAdjustment.setCode(GeneratorHelper.instance().generate(TableType.STOCK_ADJUSTMENT, codeSequenceDao, stockAdjustment.getOrganization()));
		
		for (Item item : stockAdjustment.getForm().getItems())
			if (SiriusValidator.nz(item.getQuantity()))
			{
				StockAdjustmentItem adjustmentItem = new StockAdjustmentItem();
				adjustmentItem.setStockAdjustment(stockAdjustment);
				adjustmentItem.setProduct(item.getProduct());
				adjustmentItem.setContainer(item.getContainer());
				adjustmentItem.setGrid(item.getGrid());
				adjustmentItem.setQuantity(item.getQuantity());
				
				adjustmentItem.getMoney().setExchangeType(ExchangeType.MIDDLE);
				adjustmentItem.getMoney().setCurrency(item.getCurrency());
				adjustmentItem.getMoney().setAmount(item.getPrice());
				
				stockAdjustment.getItems().add(adjustmentItem);
			}
		
		genericDao.add(stockAdjustment);
		
		for(StockAdjustmentItem item : stockAdjustment.getItems())
		{
			if (item.getQuantity().compareTo(BigDecimal.ZERO) > 0)
			{
				if (item.getTag() != null && item.getTag().getInventoryType() == null)
					item.getTag().setInventoryType(InventoryType.STOCK);
				
				inventoryUtil.in(InventoryItem.class, item.getControllableBridge());
				
				if (!configuration.getTransactionType().equals(StockControlType.AVERAGE))
					item.getControllableBridge().getInOuts().add(stockControlService.fifoLifo(ProductInOutTransaction.class, null, item.getControllableBridge(), item.getControllableBridge(), item.getMoney(), item.getQuantity()));
				else
					stockControlService.average(item.getProduct(), stockAdjustment.getOrganization(), item.getMoney().getAmount(), item.getQuantity());
				
				balanceUtil.in(DWInventoryItemBalance.class, item.getControllableBridge(), item.getQuantity());
				
				balanceDetailUtil.in(DWInventoryItemBalanceDetail.class, item.getControllableBridge(), item, stockAdjustment, item.getQuantity(), 
					item.getControllableBridge().getUnitPrice(), stockAdjustment.getReason());
			}
			else
			{
				if (item.getTag() != null && item.getTag().getInventoryType() == null)
					item.getTag().setInventoryType(InventoryType.STOCK);
				
				inventoryUtil.out(InventoryItem.class, item.getControllableBridge());
				
				StockAdjustmentItemStockableBridge bridgeItem  = new StockAdjustmentItemStockableBridge();
				bridgeItem.setStockAdjustmentItem(item);

				//ProductInOut get product
				bridgeItem.getStockControls().addAll(stockControlService.get(ProductInOutTransaction.class, item.getControllableBridge(), bridgeItem, configuration.getTransactionType()));
				
				if (bridgeItem.getStockControls().isEmpty())
					throw new ServiceException("Stock control does not exist!");

				balanceUtil.out(DWInventoryItemBalance.class, item.getControllableBridge(), DecimalHelper.positive(item.getQuantity()));
				
				for (StockControl stock : bridgeItem.getStockControls())
					balanceDetailUtil.out(DWInventoryItemBalanceDetail.class, item.getControllableBridge(), item, stockAdjustment, 
						stock.getQuantity(), stock.getPrice(), stockAdjustment.getReason());

				item.setStockableBridge(bridgeItem);
			}
			
			stockAdjustment.getItems().add(item);
		}
		
		genericDao.merge(stockAdjustment);
		
		throw new ServiceException();
	}
	
	@AuditTrails(className = StockAdjustment.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(StockAdjustment stockAdjustment) throws ServiceException
	{
		genericDao.update(stockAdjustment);
	}

	@AuditTrails(className = StockAdjustment.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(StockAdjustment stockAdjustment) throws Exception
	{
//		for (StockAdjustmentItem item : stockAdjustment.getItems())
//		{
//			if (item.getQuantity().compareTo(BigDecimal.ZERO) > 0)
//			{
//				inventoryitemUtil.out(InventoryItem.class, item, item.getQuantity());
//				balanceUtil.in(DWInventoryItemBalance.class, item, DecimalHelper.negative(item.getQuantity()));
//				balanceDetailUtil.in(DWInventoryItemBalanceDetail.class, item, DecimalHelper.negative(item.getQuantity()), item.getCogs());
//			} else
//			{
//				inventoryitemUtil.in(InventoryItem.class, item, item.getQuantity());
//				balanceUtil.out(DWInventoryItemBalance.class, item, item.getQuantity());
//				balanceDetailUtil.out(DWInventoryItemBalanceDetail.class, item, item.getQuantity(), item.getCogs());
//			}
//		}

		genericDao.delete(stockAdjustment);
		throw new ServiceException();
	}
}
