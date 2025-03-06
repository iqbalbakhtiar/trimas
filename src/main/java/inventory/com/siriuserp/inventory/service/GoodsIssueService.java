package com.siriuserp.inventory.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.siriuserp.inventory.criteria.InventoryItemFilterCriteria;
import com.siriuserp.inventory.dm.GoodsIssue;
import com.siriuserp.inventory.dm.GoodsIssueItem;
import com.siriuserp.inventory.dm.InventoryConfiguration;
import com.siriuserp.inventory.dm.InventoryControl;
import com.siriuserp.inventory.dm.InventoryItem;
import com.siriuserp.inventory.dm.InventoryType;
import com.siriuserp.inventory.dm.ProductInOutTransaction;
import com.siriuserp.inventory.dm.StockControl;
import com.siriuserp.inventory.dm.WarehouseTransactionItem;
import com.siriuserp.inventory.dm.WarehouseTransactionType;
import com.siriuserp.inventory.form.InventoryForm;
import com.siriuserp.inventory.util.InventoryItemTagUtil;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.AutomaticSibling;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.dm.UrlCache;
import com.siriuserp.sdk.dm.User;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.FormHelper;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;
import com.siriuserp.sdk.utility.SiriusValidator;
import com.siriuserp.sdk.utility.UserHelper;

import javolution.util.FastMap;

@Component
@Transactional(rollbackFor = Exception.class)
public class GoodsIssueService
{
	@Autowired
	private CodeSequenceDao codeSequenceDao;

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private StockControlService stockControllService;

	@Autowired
	private InventoryItemTagUtil inventoryitemUtil;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("filterCriteria", filterCriteria);
		//		map.put("sources", SourceOut.values());
		//		map.put("completes", Arrays.asList(SourceCompletion.values()).stream().filter(source -> !source.getType().equals("IN")).collect(Collectors.toList()));
		//		map.put("sequences", SourceSequence.values());
		map.put("issues", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));

		return map;
	}

	//  @AutomaticUpdateMemoable(className = WarehouseReferenceItem.class, memoName = PurchaseMemoable.class, signFactor = 1)
	//	@AutomaticPosting(roleClasses ={ GoodsIssuePostingRole.class })
	@AuditTrails(className = GoodsIssue.class, actionType = AuditTrailsActionType.CREATE)
	@AutomaticSibling(roles =
	{ "AddDWInventoryItemOutSiblingRole" })
	public void add(GoodsIssue goodsIssue) throws Exception
	{
		Assert.notEmpty(goodsIssue.getForm().getItems(), "Empty item transaction, please recheck !");

		InventoryConfiguration configuration = genericDao.load(InventoryConfiguration.class, Long.valueOf(1));
		Assert.notNull("Inventory configuration doesnot exist!");

		String code = GeneratorHelper.instance().generate(TableType.GOODS_ISSUE, codeSequenceDao, goodsIssue.getOrganization());
		goodsIssue.setCode(code);

		for (Item item : goodsIssue.getForm().getItems())
			if (SiriusValidator.gz(item.getIssued()) && item.getContainer() != null)
			{
				WarehouseTransactionItem transactionItem = genericDao.load(WarehouseTransactionItem.class, item.getWarehouseTransactionItem().getId());

				if (goodsIssue.getDate().compareTo(transactionItem.getReferenceItem().getDate()) < 0)
					throw new ServiceException("Date must be equal or greater than " + DateHelper.format(transactionItem.getReferenceItem().getDate()));

				GoodsIssueItem goodsIssueItem = new GoodsIssueItem();
				goodsIssueItem.setGoodsIssue(goodsIssue);
				goodsIssueItem.setIssued(item.getIssued());
				goodsIssueItem.setContainer(item.getContainer());
				goodsIssueItem.setGrid(item.getGrid());
				goodsIssueItem.setTransactionItem(transactionItem);

				transactionItem.setIssued(transactionItem.getIssued().add(goodsIssueItem.getIssued()));
				transactionItem.setUnissued(transactionItem.getUnissued().subtract(goodsIssueItem.getIssued()));

				if (transactionItem.getIssued().compareTo(transactionItem.getQuantity()) == 0)
					transactionItem.setLocked(true);

				goodsIssueItem.setTransactionItem(transactionItem);

				if (transactionItem.getType().equals(WarehouseTransactionType.INTERNAL))
				{
					transactionItem.getReferenceItem().setSourceContainer(goodsIssueItem.getContainer());
					transactionItem.setUnreceipted(transactionItem.getUnreceipted().add(goodsIssueItem.getIssued()));
				}

				List<InventoryControl> controls = inventoryitemUtil.out(InventoryItem.class, goodsIssueItem);

				for (InventoryControl control : controls)
				{
					control.setStockable(goodsIssueItem);
					control.setWarehouseTransactionItem(transactionItem);

					if (transactionItem.getTransactionSource().isInternal())
						inventoryitemUtil.trout(InventoryItem.class, control);

					goodsIssueItem.getInventoryControls().add(control);
				}

				if (transactionItem.getTransactionSource().isReservable() && transactionItem.getReferenceItem().getReserveBridge() != null)
					inventoryitemUtil.revout(InventoryItem.class, transactionItem.getReferenceItem().getReserveBridge().getReserveControls(), goodsIssueItem);

				//ProductInOut get product
				goodsIssueItem.getStockControls().addAll(stockControllService.get(ProductInOutTransaction.class, goodsIssueItem, goodsIssueItem, configuration.getTransactionType()));

				Assert.notEmpty(goodsIssueItem.getStockControls(), "Stock control does not exist!");

				transactionItem.getIssuedItems().add(goodsIssueItem);
				transactionItem.getDestinations().addAll(goodsIssueItem.getStockControls());
				transactionItem.getInventoryControls().addAll(goodsIssueItem.getInventoryControls());

				genericDao.update(transactionItem);

				goodsIssue.getItems().add(goodsIssueItem);
			}

		genericDao.add(goodsIssue);
	}

	public Map<String, Object> preedit(GridViewFilterCriteria filterCriteria) throws Exception
	{
		return loadData(filterCriteria);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> loadData(GridViewFilterCriteria filterCriteria) throws Exception
	{
		User user = UserHelper.activeUser();
		UrlCache printCogs = user.getUrls().get("/page/goodsissueprintcogs.htm");
		UrlCache print = user.getUrls().get("/page/goodsissuecompletionprint.htm");
		UrlCache viewCogs = user.getUrls().get("/page/inventoryviewcogs.htm");

		InventoryItemFilterCriteria criteria = (InventoryItemFilterCriteria) filterCriteria;
		InventoryForm form = FormHelper.bind(InventoryForm.class, load(criteria.getId()));

		// Sort By Product Code First And Then InventoryType (Stock > Shrink)
		List<StockControl> sortedItems = form.getGoodsIssue().getStockControlls().stream().sorted(Comparator.comparing((StockControl item) -> item.getStockable().getProduct().getCode())
				.thenComparing(item -> item.getStockable().getWarehouseTransactionItem().getTag().getInventoryType(), Comparator.comparing(type -> type == InventoryType.STOCK ? 0 : 1))).collect(Collectors.toList());

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("goodsIssue_edit", form.getGoodsIssue());
		map.put("ref", form.getGoodsIssue().getItems().iterator().next().getWarehouseTransactionItem().getReferenceItem());
		map.put("printCogs", printCogs != null ? printCogs.getAccessType() : null);
		map.put("printAll", print != null && print.getAccessType().isPrint());
		map.put("viewCogs", viewCogs != null ? viewCogs.getAccessType() : null);
		//		map.put("printable", (print != null && printableUtil.isPrintable("/page/goodsissuecompletionprint.htm", criteria.getId())));
		//		map.put("issues", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, GoodsIssueItemPrintViewQuery.class)));
		map.put("transaction_form", form);
		map.put("stockControlls", sortedItems);

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public GoodsIssue load(Long id)
	{
		return genericDao.load(GoodsIssue.class, id);
	}

	@AuditTrails(className = GoodsIssue.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(GoodsIssue goodsIssue) throws Exception
	{
		genericDao.update(goodsIssue);
	}
}
