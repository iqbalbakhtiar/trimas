package com.siriuserp.inventory.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.inventory.criteria.GoodsReceiptFilterCriteria;
import com.siriuserp.inventory.dm.GoodsReceiptManual;
import com.siriuserp.inventory.dm.GoodsReceiptManualItem;
import com.siriuserp.inventory.dm.WarehouseTransactionItem;
import com.siriuserp.inventory.dm.WarehouseTransactionType;
import com.siriuserp.inventory.form.InventoryForm;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.AutomaticReverseSibling;
import com.siriuserp.sdk.annotation.AutomaticSibling;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;
import com.siriuserp.sdk.utility.ReferenceItemHelper;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastMap;

/**
 * @author Andres Nodas
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class GoodsReceiptManualService
{

	@Autowired
	private CodeSequenceDao codeSequenceDao;

	@Autowired
	private GenericDao genericDao;

	//@Autowired
	//private GoodsReceiptService goodsReceiptService;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();

		GoodsReceiptFilterCriteria criteria = (GoodsReceiptFilterCriteria) filterCriteria;

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
			map.put("organization", genericDao.load(Party.class, criteria.getOrganization()));

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getFacility()))
			map.put("facility", genericDao.load(Facility.class, criteria.getFacility()));

		map.put("filterCriteria", filterCriteria);
		map.put("receipts", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));

		return map;
	}

	@InjectParty(keyName = "receiptManual_form")
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preadd() throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("receiptManual_form", new InventoryForm());

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preedit(Long id) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("receiptManual_edit", load(id));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public GoodsReceiptManual load(Long id)
	{
		return genericDao.load(GoodsReceiptManual.class, id);
	}

	@AuditTrails(className = GoodsReceiptManual.class, actionType = AuditTrailsActionType.CREATE)
	@AutomaticSibling(roles= {"AddInventorySiblingRole"})
	@AutomaticReverseSibling(roles = "GoodsReceiptManualGenerateBarcodeSiblingRole")
	public void add(GoodsReceiptManual goodsReceiptManual) throws Exception
	{
		InventoryForm form = (InventoryForm) goodsReceiptManual.getForm();
		
		goodsReceiptManual.setCode(GeneratorHelper.instance().generate(TableType.GOODS_RECEIPT_MANUAL, codeSequenceDao, goodsReceiptManual.getOrganization()));

		for (Item item : form.getItems())
		{
			if (SiriusValidator.gz(item.getReceipted()))
			{
				GoodsReceiptManualItem receiptItem = new GoodsReceiptManualItem();
				receiptItem.setQuantity(item.getReceipted());
				receiptItem.setUnreceipted(item.getReceipted());
				receiptItem.setAmount(item.getPrice());
				receiptItem.setProduct(item.getProduct());
				receiptItem.setContainer(item.getContainer());
				receiptItem.setAmount(item.getPrice());
				receiptItem.setGrid(item.getGrid());
				receiptItem.setFacilitySource(goodsReceiptManual.getFacility());
				receiptItem.setFacilityDestination(item.getGrid().getFacility());
				receiptItem.setDestinationGrid(item.getGrid());
				receiptItem.setDestinationContainer(item.getContainer());
				receiptItem.getLot().setSerial(item.getSerial());;
				receiptItem.setReferenceCode(goodsReceiptManual.getCode());
				receiptItem.setReferenceFrom(goodsReceiptManual.getSupplier().getFullName());
				receiptItem.setReferenceTo(item.getContainer().getCode());
				receiptItem.setOrganization(goodsReceiptManual.getOrganization());
				receiptItem.setGoodsReceiptManual(goodsReceiptManual);
				
				WarehouseTransactionItem warehouseTransactionItem = ReferenceItemHelper.init(genericDao, receiptItem.getQuantity(), WarehouseTransactionType.IN, receiptItem);
				warehouseTransactionItem.setLocked(false);
				
				receiptItem.setTransactionItem(warehouseTransactionItem);
				
				receiptItem.setGoodsReceiptManual(goodsReceiptManual);
				goodsReceiptManual.getItems().add(receiptItem);
			}
		}

		genericDao.add(goodsReceiptManual);

		//autoWarehouseService.autoReceipt(goodsReceiptManual);
	}

	@AuditTrails(className = GoodsReceiptManual.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(GoodsReceiptManual goodsReceiptManual) throws ServiceException
	{
		genericDao.update(goodsReceiptManual);

		/*@AuditTrails(className = GoodsReceiptManual.class, actionType = AuditTrailsActionType.DELETE)
		public void delete(GoodsReceiptManual goodsReceiptManual) throws Exception
		{
			for (GoodsReceipt receipt : goodsReceiptManual.getReceipts())
				goodsReceiptService.delete(receipt);
	
			genericDao.delete(goodsReceiptManual);*/
	}

}
