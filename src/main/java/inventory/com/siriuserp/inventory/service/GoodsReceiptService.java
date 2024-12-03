package com.siriuserp.inventory.service;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.siriuserp.inventory.adapter.WarehouseItemAdapter;
import com.siriuserp.inventory.criteria.GoodsReceiptFilterCriteria;
import com.siriuserp.inventory.dm.GoodsIssue;
import com.siriuserp.inventory.dm.GoodsIssueItem;
import com.siriuserp.inventory.dm.GoodsReceipt;
import com.siriuserp.inventory.dm.GoodsReceiptItem;
import com.siriuserp.inventory.dm.InventoryConfiguration;
import com.siriuserp.inventory.dm.InventoryItem;
import com.siriuserp.inventory.dm.ProductCategoryType;
import com.siriuserp.inventory.dm.ProductInOutTransaction;
import com.siriuserp.inventory.dm.WarehouseTransactionItem;
import com.siriuserp.inventory.dm.WarehouseTransactionSource;
import com.siriuserp.inventory.dm.WarehouseTransactionType;
import com.siriuserp.inventory.form.TransactionForm;
import com.siriuserp.inventory.sibling.AddDWInventoryItemInSiblingRole;
import com.siriuserp.inventory.util.InventoryitemTagUtil;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.AutomaticSibling;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.Reference;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.dm.Tax;
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
public class GoodsReceiptService extends Service {
    @Autowired
    private GenericDao genericDao;

    @Autowired
    private CodeSequenceDao codeSequenceDao;

	@Autowired
	private InventoryitemTagUtil inventoryitemUtil;

	@Autowired
	private StockControlService stockControllService;

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends AbstractGridViewQuery> queryclass) throws Exception {
        FastMap<String, Object> map = new FastMap<String, Object>();
        map.put("receipts", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));
        map.put("filterCriteria", filterCriteria);

        return map;
    }

    @SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> preadd1(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws Exception
    {
        WarehouseItemAdapter adapter = new WarehouseItemAdapter();

        GoodsReceiptFilterCriteria criteria = (GoodsReceiptFilterCriteria) filterCriteria;

        if (SiriusValidator.validateLongParam(criteria.getOrganization()))
            adapter.setOrganization(genericDao.load(Party.class, criteria.getOrganization()));

        if (SiriusValidator.validateLongParam(criteria.getFacility()))
            adapter.setFacility(genericDao.load(Facility.class, criteria.getFacility()));

        if (SiriusValidator.validateLongParam(criteria.getSupplier()))
            adapter.setParty(genericDao.load(Party.class, criteria.getSupplier()));

        if (SiriusValidator.validateLongParam(criteria.getCurrency()))
            adapter.setCurrency(genericDao.load(Currency.class, criteria.getCurrency()));

        if (SiriusValidator.validateLongParam(criteria.getTax()))
            adapter.setTax(genericDao.load(Tax.class, criteria.getTax()));

        if (SiriusValidator.validateParam(criteria.getReferenceType()))
            adapter.setSource(WarehouseTransactionSource.valueOf(criteria.getReferenceType()));

        adapter.setReference(Reference.REFERENCE);

        adapter.getAdapters().clear();
        adapter.getAdapters().addAll(FilterAndPaging.filter(genericDao, QueryFactory.create(criteria, queryclass)));

        Map<String, Object> map = new FastMap<String, Object>();
        map.put("adapter", adapter);
        map.put("filterCriteria", criteria);
//        map.put("internals", SourceInternal.values());
//        map.put("completes", Arrays.asList(SourceCompletion.values()).stream().filter(source -> source.getType().equals("INTERNAL")).collect(Collectors.toList()));

        return map;
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preadd2(WarehouseItemAdapter adapter) throws Exception
	{
		Map<String, Object> map = new FastMap<String, Object>();

		TransactionForm form = new TransactionForm();

		StringBuilder builder = new StringBuilder();

		for (WarehouseItemAdapter itemAdapter : adapter.getEnableAdapters())
		{
			WarehouseTransactionItem transactionItem = genericDao.load(WarehouseTransactionItem.class, itemAdapter.getItem().getId());

			Item item = new Item();
			item.setProduct(transactionItem.getProduct());
			item.setWarehouseTransactionItem(transactionItem);
			item.setBuffer(itemAdapter.getIssued());

			form.getItems().add(item);

			if (!transactionItem.getType().equals(WarehouseTransactionType.OUT) && !transactionItem.getTransactionCode().contains(builder))
			{
				builder.append(builder.length() > 0 ? ", " : "");
				builder.append("[" + transactionItem.getTransactionCode() + "]");
			}

			if (form.getOrganization() == null)
				form.setOrganization(transactionItem.getReferenceItem().getOrganization());

			if (form.getFacility() == null)
				form.setFacility(genericDao.load(Facility.class, transactionItem.getReferenceItem().getFacilityDestination().getId()));

			if (map.get("goodsIssue") == null && itemAdapter.getIssueItem() != null)
				map.put("goodsIssue", genericDao.load(GoodsIssue.class, itemAdapter.getIssueItem().getGoodsIssue().getId()));

			if (map.get("ref") == null)
				map.put("ref", transactionItem.getReferenceItem());

            map.computeIfAbsent("transactionSource", k -> transactionItem.getTransactionSource());
		}

		form.setNote(builder.toString());

		map.put("goodsReceipt_add", form);

		return map;
	}

	/**
	 * Service ini akan execute kelas {@link AddDWInventoryItemInSiblingRole} setelah service selesai.
	 */
//    @AutomaticUpdateMemoable(className = WarehouseReferenceItem.class, memoName = SalesMemoable.class, signFactor = 1)
	@AuditTrails(className = GoodsReceipt.class, actionType = AuditTrailsActionType.CREATE)
//	@AutomaticPosting(roleClasses = GoodsReceiptPostingRole.class)
	@AutomaticSibling(roles = "AddDWInventoryItemInSiblingRole")
	public void add(GoodsReceipt goodsReceipt) throws Exception
	{
		InventoryConfiguration configuration = genericDao.load(InventoryConfiguration.class, Long.valueOf(1));
		Assert.notNull(configuration, "Inventory configuration doesnot exist!");

		goodsReceipt.setCode(GeneratorHelper.instance().generate(TableType.GOODS_RECEIPT, codeSequenceDao, goodsReceipt.getOrganization()));

		for (Item item : goodsReceipt.getForm().getItems())
		{
			GoodsReceiptItem receiptItem = new GoodsReceiptItem();
			receiptItem.setGoodsReceipt(goodsReceipt);
			receiptItem.setReceipted(item.getReceipted());
//			receiptItem.setActual(item.getActual());
			receiptItem.setContainer(item.getContainer());
			receiptItem.setGrid(item.getGrid());
			receiptItem.setWarehouseTransactionItem(item.getWarehouseTransactionItem());
			
			goodsReceipt.getItems().add(receiptItem);
		}
		
//		for (GoodsReceiptItem goodsReceiptItem : goodsReceipt.getItems())
//		{
//			if (goodsReceiptItem.getActual() != null)
//			{
//				BigDecimal diff = goodsReceiptItem.getReceipted().subtract(goodsReceiptItem.getActual());
//
//				if (goodsReceiptItem.getActual().compareTo(BigDecimal.ZERO) > 0 && diff.compareTo(BigDecimal.ZERO) > 0)
//				{
//					GoodsReceiptItem adjustmentItem = new GoodsReceiptItem();
//					adjustmentItem.setReceipted(diff);
//					adjustmentItem.setWarehouseTransactionItem(goodsReceiptItem.getWarehouseTransactionItem());
//					adjustmentItem.setGoodsReceipt(goodsReceipt);
//
//					goodsReceipt.getItems().add(adjustmentItem);
//
//					goodsReceiptItem.setReceipted(goodsReceiptItem.getActual());
//				}
//			}
//		}

//		for (GoodsReceiptItem goodsReceiptItem : goodsReceipt.getItems())
//			if (goodsReceiptItem.getContainer() == null || DecimalHelper.isZero(goodsReceiptItem.getActual()))
//			{
//				goodsReceiptItem.setType(ReceiptItemType.ADJUST);
//				goodsReceiptItem.setContainer(containerDao.loadAdjustment(goodsReceipt.getFacility().getId(), ContainerStatus.ADJUSTMENT));
//				goodsReceiptItem.setGrid(goodsReceiptItem.getContainer().getGrid());
//			}

		//Remove Unused Items
		goodsReceipt.getItems().removeIf(item -> item.getReceipted().compareTo(BigDecimal.ZERO) < 1 && item.getContainer() == null);

		Assert.notEmpty(goodsReceipt.getItems(), "Empty item transaction, please recheck !");
		
		for (GoodsReceiptItem goodsReceiptItem : goodsReceipt.getItems())
			init(goodsReceipt, goodsReceiptItem);

		genericDao.add(goodsReceipt);
	}
	
	public void init(GoodsReceipt goodsReceipt, GoodsReceiptItem goodsReceiptItem) throws Exception
	{
		WarehouseTransactionItem transactionItem = (WarehouseTransactionItem) genericDao.load(WarehouseTransactionItem.class, goodsReceiptItem.getWarehouseTransactionItem().getId());
		if (transactionItem.getReferenceItem().getDate().compareTo(goodsReceipt.getDate()) > 0)
			throw new ServiceException("Date must be equal or greater than " + DateHelper.format(transactionItem.getReferenceItem().getDate()));

		transactionItem.setReceipted(transactionItem.getReceipted().add(goodsReceiptItem.getReceipted()));
		transactionItem.setUnreceipted(transactionItem.getUnreceipted().subtract(goodsReceiptItem.getReceipted()));

		if (transactionItem.getTransactionSource().isAutoLocked() || transactionItem.getReceipted().compareTo(transactionItem.getQuantity()) == 0)
			transactionItem.setLocked(true);

		genericDao.update(transactionItem);

		goodsReceiptItem.setWarehouseTransactionItem(transactionItem);

		if (transactionItem.getProduct().getProductCategory().getType().compareTo(ProductCategoryType.STOCK) == 0)
		{
			goodsReceiptItem.getInOuts().clear();

			inventoryitemUtil.in(InventoryItem.class, goodsReceiptItem);

			if (!transactionItem.getInternalInventories().isEmpty())
				inventoryitemUtil.trin(InventoryItem.class, transactionItem.getInternalInventories(), goodsReceiptItem);

			if (transactionItem.getTransactionSource().isInternal() && (transactionItem.getReferenceItem().getDestinationContainer() == null || !goodsReceiptItem.getContainer().getName().contains("ADJUSTMENT")))
				transactionItem.getReferenceItem().setDestinationContainer(goodsReceiptItem.getContainer());

			if (!transactionItem.getInternalStocks().isEmpty())
				goodsReceiptItem.getInOuts().addAll(stockControllService.buffer(ProductInOutTransaction.class, transactionItem.getInternalStocks(), goodsReceiptItem));
			else
				goodsReceiptItem.getInOuts().add(stockControllService.fifoLifo(ProductInOutTransaction.class, transactionItem, goodsReceiptItem, goodsReceiptItem, transactionItem.getMoney(), goodsReceiptItem.getQuantity()));

			if (goodsReceiptItem.getInOuts().isEmpty())
				stockControllService.average(transactionItem.getProduct(), transactionItem.getReferenceItem().getOrganization(), transactionItem.getMoney().getAmount(), goodsReceiptItem.getReceipted());
		}

		if (goodsReceipt.getOrganization() == null)
			goodsReceipt.setOrganization(transactionItem.getReferenceItem().getOrganization());

//		if (goodsReceipt.getApprover() == null && transactionItem.getReferenceItem().getApprover() != null)
//			goodsReceipt.setApprover(transactionItem.getReferenceItem().getApprover());
//
//		if (goodsReceipt.getSecondaryApprover() == null && transactionItem.getReferenceItem().getSecondaryApprover() != null)
//			goodsReceipt.setSecondaryApprover(transactionItem.getReferenceItem().getSecondaryApprover());
		
		transactionItem.getReceiptedItems().add(goodsReceiptItem);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preedit(Long id) throws Exception
	{
		TransactionForm form = FormHelper.bind(TransactionForm.class, load(id));

		FastMap<String, Object> map = new FastMap<String, Object>();

		map.put("adapter", form);
		map.put("goodsReceipt_edit", form.getGoodsReceipt());
		map.put("ref", form.getGoodsReceipt().getItems().iterator().next().getWarehouseTransactionItem().getReferenceItem());
		map.put("transactionSource", form.getGoodsReceipt().getItems().iterator().next().getWarehouseTransactionItem().getTransactionSource());

		if (map.get("goodsIssue") == null)
		{
			GoodsReceiptItem goodsReceiptItem = genericDao.load(GoodsReceiptItem.class, form.getGoodsReceipt().getItems().iterator().next().getId());

			if (goodsReceiptItem.getStockable() != null)
				map.put("goodsIssue", genericDao.load(GoodsIssueItem.class, goodsReceiptItem.getStockable().getId()).getGoodsIssue());
		}

		User user = UserHelper.activeUser();
		UrlCache viewCogs = user.getUrls().get("/page/inventoryviewcogs.htm");

		map.put("viewCogs", viewCogs != null ? viewCogs.getAccessType() : null);

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public GoodsReceipt load(Long id)
	{
		return genericDao.get(GoodsReceipt.class, id);
	}
	
	@AuditTrails(className = GoodsReceipt.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(GoodsReceipt goodsReceipt) throws Exception
	{
		genericDao.update(goodsReceipt);
	}
}
