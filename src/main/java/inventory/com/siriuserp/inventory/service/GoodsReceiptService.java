/**
 * File Name  : GoodsReceiptService.java
 * Created On : Jul 26, 2023
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.inventory.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.siriuserp.accountpayable.dm.InvoiceVerification;
import com.siriuserp.accountpayable.dm.InvoiceVerificationItem;
import com.siriuserp.accountpayable.dm.InvoiceVerificationReferenceItem;
import com.siriuserp.accountpayable.dm.InvoiceVerificationReferenceType;
import com.siriuserp.accountpayable.service.InvoiceVerificationService;
import com.siriuserp.accountpayable.util.InvoiceVerificationReferenceUtil;
import com.siriuserp.administration.util.LotHelper;
import com.siriuserp.inventory.adapter.WarehouseItemAdapter;
import com.siriuserp.inventory.criteria.GoodsReceiptFilterCriteria;
import com.siriuserp.inventory.dm.DWInventoryItemBalance;
import com.siriuserp.inventory.dm.DWInventoryItemBalanceDetail;
import com.siriuserp.inventory.dm.GoodsIssue;
import com.siriuserp.inventory.dm.GoodsIssueItem;
import com.siriuserp.inventory.dm.GoodsReceipt;
import com.siriuserp.inventory.dm.GoodsReceiptItem;
import com.siriuserp.inventory.dm.InventoryConfiguration;
import com.siriuserp.inventory.dm.InventoryItem;
import com.siriuserp.inventory.dm.ProductCategoryType;
import com.siriuserp.inventory.dm.ProductInOutTransaction;
import com.siriuserp.inventory.dm.StockControl;
import com.siriuserp.inventory.dm.WarehouseTransactionItem;
import com.siriuserp.inventory.dm.WarehouseTransactionSource;
import com.siriuserp.inventory.dm.WarehouseTransactionType;
import com.siriuserp.inventory.form.InventoryForm;
import com.siriuserp.inventory.util.InventoryBalanceDetailUtil;
import com.siriuserp.inventory.util.InventoryBalanceUtil;
import com.siriuserp.inventory.util.InventoryItemTagUtil;
import com.siriuserp.procurement.dm.PurchaseOrderItem;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.AutomaticSibling;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.CreditTermDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dao.PartyRelationshipDao;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.CodeSequence;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.CreditTerm;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.dm.Money;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PartyRelationship;
import com.siriuserp.sdk.dm.PartyRelationshipType;
import com.siriuserp.sdk.dm.Reference;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.dm.Tax;
import com.siriuserp.sdk.dm.UrlCache;
import com.siriuserp.sdk.dm.User;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.DecimalHelper;
import com.siriuserp.sdk.utility.FormHelper;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;
import com.siriuserp.sdk.utility.SiriusValidator;
import com.siriuserp.sdk.utility.UserHelper;

import javolution.util.FastMap;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@SuppressWarnings("unchecked")
@Component
@Transactional(rollbackFor = Exception.class)
public class GoodsReceiptService extends Service
{
	@Autowired
	private GenericDao genericDao;

	@Autowired
	private CodeSequenceDao codeSequenceDao;

	@Autowired
	private PartyRelationshipDao partyRelationshipDao;

	@Autowired
	private CreditTermDao creditTermDao;

	@Autowired
	private InventoryItemTagUtil inventoryItemUtil;

	@Autowired
	private InventoryBalanceUtil balanceUtil;

	@Autowired
	private InventoryBalanceDetailUtil balanceDetailUtil;

	@Autowired
	private StockControlService stockControllService;

	@Autowired
	private InvoiceVerificationService invoiceVerificationService;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends AbstractGridViewQuery> queryclass) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("receipts", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));
		map.put("filterCriteria", filterCriteria);

		return map;
	}

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

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preadd2(WarehouseItemAdapter adapter) throws Exception
	{
		Map<String, Object> map = new FastMap<String, Object>();
		InventoryForm form = new InventoryForm();
		StringBuilder builder = new StringBuilder();

		for (WarehouseItemAdapter itemAdapter : adapter.getEnableAdapters())
		{
			WarehouseTransactionItem transactionItem = genericDao.load(WarehouseTransactionItem.class, itemAdapter.getItem().getId());

			Item item = new Item();
			item.setProduct(transactionItem.getProduct());
			item.setWarehouseTransactionItem(transactionItem);
			item.setBuffer(itemAdapter.getIssued());
			item.setAmount(transactionItem.getMoney().getAmount());

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

	@AuditTrails(className = GoodsReceipt.class, actionType = AuditTrailsActionType.CREATE)
	//	@AutomaticPosting(roleClasses = GoodsReceiptPostingRole.class)
	@AutomaticSibling(roles = "AddDWInventoryItemInSiblingRole")
	public void add(GoodsReceipt goodsReceipt) throws Exception
	{
		InventoryConfiguration configuration = genericDao.load(InventoryConfiguration.class, Long.valueOf(1));
		Assert.notNull(configuration, "Inventory configuration doesnot exist!");

		goodsReceipt.setCode(GeneratorHelper.instance().generate(TableType.GOODS_RECEIPT, codeSequenceDao, goodsReceipt.getOrganization().getCode(), null, null, CodeSequence.MONTH, null));

		boolean createInvoice = false;
		for (Item item : goodsReceipt.getForm().getItems())
		{
			if (item.getReceipted().compareTo(BigDecimal.ZERO) > 0)
			{
				GoodsReceiptItem receiptItem = new GoodsReceiptItem();
				receiptItem.setGoodsReceipt(goodsReceipt);
				receiptItem.setReceipted(item.getReceipted());
				receiptItem.setContainer(item.getContainer());
				receiptItem.setGrid(item.getGrid());
				receiptItem.setWarehouseTransactionItem(item.getWarehouseTransactionItem());

				goodsReceipt.getItems().add(receiptItem);

				if (item.getWarehouseTransactionItem().getTransactionSource().equals(WarehouseTransactionSource.DIRECT_PURCHASE_ORDER)
						|| item.getWarehouseTransactionItem().getTransactionSource().equals(WarehouseTransactionSource.STANDARD_PURCHASE_ORDER))
				{
					PurchaseOrderItem purchaseItem = genericDao.load(PurchaseOrderItem.class, item.getWarehouseTransactionItem().getReferenceItem().getId());
					if (purchaseItem != null && !purchaseItem.getPurchaseOrder().isInvoiceBeforeReceipt())
						createInvoice = true;
				}
			}
		}

		goodsReceipt.getItems().removeIf(item -> item.getReceipted().compareTo(BigDecimal.ZERO) < 1 && item.getContainer() == null);

		Assert.notEmpty(goodsReceipt.getItems(), "Empty item transaction, please recheck !");

		for (GoodsReceiptItem goodsReceiptItem : goodsReceipt.getItems())
			init(goodsReceipt, goodsReceiptItem);

		genericDao.add(goodsReceipt);

		// Auto Create Invoice Verification for PO & DPO (Auto Create Invoice Before Receipt = false)
		if (createInvoice)
			createInvoice(goodsReceipt);
	}

	@AuditTrails(className = InvoiceVerification.class, actionType = AuditTrailsActionType.CREATE)
	private void createInvoice(GoodsReceipt goodsReceipt) throws Exception
	{
		InventoryForm form = new InventoryForm();
		InvoiceVerification invoiceVerification = new InvoiceVerification();
		invoiceVerification.setForm(form);
		invoiceVerification.setDate(goodsReceipt.getDate());
		invoiceVerification.setOrganization(goodsReceipt.getOrganization());
		invoiceVerification.setMoney(new Money());

		BigDecimal totalAmount = BigDecimal.ZERO;
		for (GoodsReceiptItem goodsReceiptItem : goodsReceipt.getItems())
		{
			if (goodsReceiptItem.getReceipted().compareTo(BigDecimal.ZERO) > 0)
			{
				PurchaseOrderItem purchaseItem = genericDao.load(PurchaseOrderItem.class, goodsReceiptItem.getWarehouseTransactionItem().getReferenceItem().getId());

				InvoiceVerificationReferenceItem invoiceReference = new InvoiceVerificationReferenceItem();
				invoiceReference.setCode(goodsReceipt.getCode());
				invoiceReference.setDate(goodsReceipt.getDate());
				invoiceReference.setOrganization(goodsReceipt.getOrganization());
				invoiceReference.setFacility(goodsReceipt.getFacility());
				invoiceReference.setSupplier(goodsReceiptItem.getWarehouseTransactionItem().getReferenceItem().getParty());
				invoiceReference.setPurchaseOrderItem(purchaseItem);
				invoiceReference.setGoodsReceiptItem(goodsReceiptItem);
				invoiceReference.setVerificated(true);
				invoiceReference.setReferenceType(InvoiceVerificationReferenceType.GOODS_RECEIPT);
				genericDao.add(invoiceReference);

				InvoiceVerificationItem invoiceItem = InvoiceVerificationReferenceUtil.initItem(invoiceReference);
				invoiceItem.setInvoiceVerification(invoiceVerification);

				if (invoiceVerification.getSupplier() == null)
					invoiceVerification.setSupplier(purchaseItem.getParty());

				if (invoiceVerification.getTax() == null)
					invoiceVerification.setTax(purchaseItem.getTax());

				if (invoiceVerification.getMoney().getCurrency() == null)
					invoiceVerification.getMoney().setCurrency(purchaseItem.getMoney().getCurrency());

				if (goodsReceiptItem.getProduct().isSerial())
					totalAmount = totalAmount.add(purchaseItem.getBarcodeQuantity().multiply(purchaseItem.getMoney().getAmount()));
				else
					totalAmount = totalAmount.add(goodsReceiptItem.getReceipted().multiply(purchaseItem.getMoney().getAmount()));

				Item item = new Item();
				item.setInvoiceVerificationItem(invoiceItem);
				form.getItems().add(item);

				invoiceVerification.getItems().add(invoiceItem);
			}
		}

		BigDecimal taxAmount = totalAmount.multiply(invoiceVerification.getTax().getTaxRate()).divide(BigDecimal.valueOf(100));
		totalAmount = totalAmount.add(taxAmount);
		invoiceVerification.setUnpaid(totalAmount);
		invoiceVerification.getMoney().setAmount(totalAmount);

		PartyRelationship relationship = partyRelationshipDao.load(invoiceVerification.getSupplier().getId(), invoiceVerification.getOrganization().getId(), PartyRelationshipType.SUPPLIER_RELATIONSHIP);
		CreditTerm creditTerm = creditTermDao.loadByRelationship(relationship.getId(), true, invoiceVerification.getDate());
		if (creditTerm == null)
			throw new ServiceException("Supplier doesn't have active Credit Term, please set it first on supplier page.");

		invoiceVerification.setDueDate(DateHelper.plusDays(invoiceVerification.getDate(), creditTerm.getTerm()));

		form.setInvoiceVerification(invoiceVerification);
		form.setGoodsReceipt(goodsReceipt);
		invoiceVerificationService.add(form.getInvoiceVerification());
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

			inventoryItemUtil.in(InventoryItem.class, goodsReceiptItem);

			if (!transactionItem.getInternalInventories().isEmpty())
				inventoryItemUtil.trin(InventoryItem.class, transactionItem.getInternalInventories(), goodsReceiptItem);

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

		transactionItem.getReceiptedItems().add(goodsReceiptItem);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preedit(Long id) throws Exception
	{
		InventoryForm form = FormHelper.bind(InventoryForm.class, load(id));
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
	public Map<String, Object> print(Long id, Long container) throws Exception
	{
		InventoryForm form = FormHelper.bind(InventoryForm.class, load(id));
		FastMap<String, Object> map = new FastMap<String, Object>();

		if (container == null)
		{
			List<Container> containers = form.getGoodsReceipt().getItems().stream().map(GoodsReceiptItem::getContainer).distinct().collect(Collectors.toList());
			map.put("containers", containers);
		} else
		{

			List<GoodsReceiptItem> filteredItems = form.getGoodsReceipt().getItems().stream().filter(it -> it.getContainer() != null // jaga‑jaga null
					&& it.getContainer().getId().equals(container)).collect(Collectors.toList());
			map.put("items", filteredItems);
		}

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

	@AuditTrails(className = GoodsReceipt.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(GoodsReceipt goodsReceipt) throws Exception
	{
		if (goodsReceipt.isDeletable())
		{
			for (GoodsReceiptItem goodsReceiptItem : goodsReceipt.getItems())
			{
				WarehouseTransactionItem transactionItem = (WarehouseTransactionItem) genericDao.load(WarehouseTransactionItem.class, goodsReceiptItem.getWarehouseTransactionItem().getId());

				if (transactionItem != null)
				{
					BigDecimal receipted = transactionItem.getReceipted().subtract(goodsReceiptItem.getReceipted());
					if (receipted.compareTo(BigDecimal.ZERO) < 0)
						receipted = BigDecimal.ZERO;

					transactionItem.setLocked(false);
					transactionItem.setReceipted(receipted);
					transactionItem.setUnreceipted(transactionItem.getUnreceipted().add(goodsReceiptItem.getReceipted()));

					genericDao.update(transactionItem);

					inventoryItemUtil.out(InventoryItem.class, goodsReceiptItem);

					if (goodsReceiptItem.getWarehouseTransactionItem().getType().equals(WarehouseTransactionType.INTERNAL))
					{
						inventoryItemUtil.trout(InventoryItem.class, goodsReceiptItem);

						BigDecimal buffer = goodsReceiptItem.getQuantity();

						for (StockControl control : transactionItem.getDestinations())
						{
							if (control.getLot() == null || LotHelper.getKey(control.getLot()).compareTo(LotHelper.getKey(goodsReceiptItem.getLot())) == 0)
							{
								if (buffer.compareTo(BigDecimal.ZERO) <= 0)
									break;

								if (control.getQuantity().compareTo(buffer) >= 0)
								{
									control.setBuffer(control.getBuffer().add(buffer));
									buffer = BigDecimal.ZERO;
								} else
								{
									buffer = buffer.subtract(control.getQuantity());
									control.setBuffer(control.getQuantity());

									genericDao.update(control);
								}
							}
						}
					}
				}

				balanceUtil.in(DWInventoryItemBalance.class, goodsReceiptItem, DecimalHelper.negative(goodsReceiptItem.getReceipted()));
				balanceDetailUtil.in(DWInventoryItemBalanceDetail.class, goodsReceiptItem, transactionItem, goodsReceipt, DecimalHelper.negative(goodsReceiptItem.getReceipted()), goodsReceiptItem.getCogs(), goodsReceipt.getNote());
			}

			genericDao.delete(goodsReceipt);
		}
	}
}
