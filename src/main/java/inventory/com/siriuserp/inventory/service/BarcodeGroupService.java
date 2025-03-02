package com.siriuserp.inventory.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.inventory.criteria.BarcodeGroupFilterCriteria;
import com.siriuserp.inventory.dm.Product;
import com.siriuserp.inventory.form.InventoryForm;
import com.siriuserp.procurement.dm.PurchaseOrder;
import com.siriuserp.procurement.dm.PurchaseOrderItem;
import com.siriuserp.procurement.service.StandardPurchaseOrderService;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.Barcode;
import com.siriuserp.sdk.dm.BarcodeGroup;
import com.siriuserp.sdk.dm.BarcodeGroupType;
import com.siriuserp.sdk.dm.BarcodeStatus;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.FormHelper;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastList;
import javolution.util.FastMap;

/**
 * @author Ferdinand
 * @author Rama Almer Felix
 *  Sirius Indonesia, PT
 *  www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class BarcodeGroupService
{
	@Autowired
	private GenericDao genericDao;

	@Autowired
	private CodeSequenceDao codeSequenceDao;

	@Autowired
	private StandardPurchaseOrderService purchaseOrderService;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		BarcodeGroupFilterCriteria criteria = (BarcodeGroupFilterCriteria) filterCriteria;

		map.put("filterCriteria", criteria);
		map.put("barcodes", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));

		return map;
	}

	@InjectParty(keyName = "barcode_form")
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preadd1(Long referenceId, BarcodeGroupType barcodeType) throws Exception
	{
		InventoryForm form = new InventoryForm();
		form.setReferenceId(referenceId);
		form.setBarcodeGroupType(barcodeType);

		if (form.getBarcodeGroupType().equals(BarcodeGroupType.PURCHASE_ORDER))
		{
			PurchaseOrder purchaseOrder = genericDao.load(PurchaseOrder.class, form.getReferenceId());
			form.setFacility(purchaseOrder.getShipTo());
			form.setPurchaseOrder(purchaseOrder);
		}

		Map<String, Object> map = new FastMap<String, Object>();
		map.put("barcode_form", form);
		map.put("types", BarcodeGroupType.values());

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preadd2(InventoryForm form) throws Exception
	{
		List<Item> items = new FastList<Item>();

		if (form.getBarcodeGroupType().equals(BarcodeGroupType.PURCHASE_ORDER))
		{
			PurchaseOrder purchaseOrder = genericDao.load(PurchaseOrder.class, form.getReferenceId());
			form.setPurchaseOrder(purchaseOrder);

			for (PurchaseOrderItem purchaseItem : purchaseOrder.getItems())
			{
				if (purchaseItem.getProduct().isSerial() && purchaseItem.getItemParent() == null)
				{
					Item item = new Item();
					item.setProduct(purchaseItem.getProduct());
					item.setQuantity(purchaseItem.getQuantity());
					item.setReference(purchaseItem.getId());

					items.add(item);
				}
			}
		}

		Map<String, Object> map = new FastMap<String, Object>();
		map.put("barcode_form", form);
		map.put("items", items);

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preadd3(InventoryForm form) throws Exception
	{
		Map<String, Object> map = new FastMap<String, Object>();
		FastList<Barcode> barcodes = new FastList<Barcode>();

		for (Item item : form.getItems())
		{
			for (int i = 0; i < item.getRoll().intValue(); i++)
			{
				Barcode barcode = new Barcode();
				barcode.setProduct(item.getProduct());
				barcode.setReferenceId(item.getReference());

				barcodes.add(barcode);

				if (i == item.getRoll().intValue() - 1 && form.getPurchaseOrder() != null)
				{
					Barcode empty = new Barcode();
					empty.setQuantity(item.getQuantity());
					empty.setReferenceId(item.getReference());

					barcodes.add(empty);
				}
			}
		}

		map.put("barcode_form", form);
		map.put("itemReferences", form.getItems());
		map.put("barcodes", barcodes);

		return map;
	}

	@AuditTrails(className = BarcodeGroup.class, actionType = AuditTrailsActionType.CREATE)
	public void add(BarcodeGroup barcodeGroup) throws Exception
	{
		InventoryForm form = (InventoryForm) barcodeGroup.getForm();
		barcodeGroup.setCode(GeneratorHelper.instance().generate(TableType.BARCODE_GROUP, codeSequenceDao, barcodeGroup.getFacility().getCode()));

		for (Item item : barcodeGroup.getForm().getItems())
		{
			if (item.getProduct() != null && SiriusValidator.gz(item.getQuantity()))
			{
				Barcode barcode = new Barcode();
				barcode.setCode(GeneratorHelper.instance().generate(TableType.BARCODE_PRODUCT, codeSequenceDao));
				barcode.setBarcodeGroup(barcodeGroup);
				barcode.setProduct(item.getProduct());
				barcode.setQuantity(item.getQuantity());
				barcode.setQuantityReal(item.getQuantityReal());
				item.setSerial(barcode.getCode());

				barcodeGroup.getBarcodes().add(barcode);
			}
		}

		genericDao.add(barcodeGroup);

		if (form.getPurchaseOrder() != null)
			purchaseOrderService.addItem(form.getPurchaseOrder().getId(), barcodeGroup.getForm().getItems());
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preedit(Long id) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		BarcodeGroup barcode = genericDao.load(BarcodeGroup.class, id);
		InventoryForm form = FormHelper.bind(InventoryForm.class, barcode);

		map.put("barcode_edit", barcode);
		map.put("barcode_form", form);

		return map;
	}

	@AuditTrails(className = BarcodeGroup.class, actionType = AuditTrailsActionType.UPDATE)
	public void update(BarcodeGroup barcodeGroup) throws Exception
	{
		genericDao.update(barcodeGroup);

		for (Item item : barcodeGroup.getForm().getItems())
		{
			if (SiriusValidator.gz(item.getQuantity()))
			{
				Barcode barcode = genericDao.load(Barcode.class, item.getReference());
				barcode.setQuantity(item.getQuantity());

				genericDao.update(barcode);
			}
		}
	}

	@AuditTrails(className = BarcodeGroup.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(BarcodeGroup barcodeGroup) throws Exception
	{
		genericDao.delete(barcodeGroup);
	}

	@AuditTrails(className = BarcodeGroup.class, actionType = AuditTrailsActionType.DELETE)
	public void deleteBarcode(Barcode barcode) throws Exception
	{
		genericDao.delete(barcode);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public BarcodeGroup load(Long id)
	{
		return genericDao.load(BarcodeGroup.class, id);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Barcode loadBarcode(Long id)
	{
		return genericDao.load(Barcode.class, id);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Map<String, Object> barcodeGroupPrint(Long id)
	{
		Map<String, Object> map = new HashMap<String, Object>();

		BarcodeGroup barcodeGroup = genericDao.load(BarcodeGroup.class, id);

		map.put("list", barcodeGroup.getBarcodes());
		map.put("groupId", barcodeGroup.getId());
		map.put("type", "4");

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Map<String, Object> barcodePrint(Long id)
	{
		Map<String, Object> map = new HashMap<String, Object>();

		Barcode barcode = genericDao.load(Barcode.class, id);

		map.put("list", barcode);
		map.put("type", "4");

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Map<String, Object> packingList(Long id)
	{
		BarcodeGroup barcodeGroup = genericDao.load(BarcodeGroup.class, id);
		Map<Product, List<Barcode>> products = new FastMap<Product, List<Barcode>>();

		for (Barcode item : barcodeGroup.getBarcodes())
		{
			if (!products.containsKey(item.getProduct()))
			{
				List<Barcode> items = new FastList<Barcode>();
				items.add(item);

				products.put(item.getProduct(), items);
			} else
			{
				List<Barcode> items = products.get(item.getProduct());
				items.add(item);

				products.put(item.getProduct(), items);
			}
		}

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("barcode_edit", barcodeGroup);
		map.put("products", products);

		return map;
	}

	@AuditTrails(className = BarcodeGroup.class, actionType = AuditTrailsActionType.UPDATE)
	public void updateStatus(Long id, BarcodeStatus available) throws Exception
	{
		BarcodeGroup barcodeGroup = load(id);

		barcodeGroup.setStatus(available);

		genericDao.update(barcodeGroup);
	}
}
