package com.siriuserp.inventory.service;

import com.siriuserp.inventory.criteria.BarcodeGroupFilterCriteria;
import com.siriuserp.inventory.dm.Product;
import com.siriuserp.inventory.form.TransactionForm;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.*;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.FormHelper;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;
import com.siriuserp.sdk.utility.SiriusValidator;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ferdinand
 * @author Rama Almer Felix
 *  Sirius Indonesia, PT
 *  www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class BarcodeGroupService {
    @Autowired
    private GenericDao genericDao;

    @Autowired
    private CodeSequenceDao codeSequenceDao;

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		BarcodeGroupFilterCriteria criteria = (BarcodeGroupFilterCriteria) filterCriteria;

		map.put("filterCriteria", criteria);
		map.put("barcodes", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));

		return map;
	}

	@InjectParty(keyName = "barcode_add")
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preadd1() throws ServiceException
	{
		Map<String, Object> map = new FastMap<String, Object>();

		map.put("barcode_add", new TransactionForm());
		map.put("types", BarcodeGroupType.values());
		map.put("now", new Date());

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preadd2(TransactionForm form) throws ServiceException
	{
		Map<String, Object> map = new FastMap<String, Object>();
		map.put("barcode_add", form);

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preadd3(TransactionForm form) throws ServiceException
	{
		Map<String, Object> map = new FastMap<String, Object>();
		FastList<Barcode> barcodes = new FastList<Barcode>();

		for (Item item : form.getItems())
		{
			for (int i = 0; i < item.getRoll().intValue(); i++)
			{
				Barcode barcode = new Barcode();
				barcode.setProduct(item.getProduct());

				barcodes.add(barcode);
			}
		}

		map.put("barcode_add", form);
		map.put("barcodes", barcodes);

		return map;
	}

	@AuditTrails(className = BarcodeGroup.class, actionType = AuditTrailsActionType.CREATE)
	public void add(BarcodeGroup barcodeGroup) throws ServiceException
	{
		barcodeGroup.setCode(GeneratorHelper.instance().generate(TableType.BARCODE_GROUP, codeSequenceDao, barcodeGroup.getFacility().getCode()));

		for (Item item : barcodeGroup.getForm().getItems())
			if (SiriusValidator.gz(item.getQuantity()))
			{
				Barcode barcode = new Barcode();
				barcode.setCode(GeneratorHelper.instance().generate(TableType.BARCODE_PRODUCT, codeSequenceDao));
				barcode.setBarcodeGroup(barcodeGroup);
				barcode.setProduct(item.getProduct());
				barcode.setQuantity(item.getQuantity());

				barcodeGroup.getBarcodes().add(barcode);
			}
		
		genericDao.add(barcodeGroup);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preedit(Long id) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		BarcodeGroup barcode = genericDao.load(BarcodeGroup.class, id);
		TransactionForm form = FormHelper.bind(TransactionForm.class, barcode);

		map.put("barcode_edit", barcode);
		map.put("barcode_add", form);

		return map;
	}

	@AuditTrails(className = BarcodeGroup.class, actionType = AuditTrailsActionType.UPDATE)
	public void update(BarcodeGroup barcodeGroup) throws ServiceException
	{
		genericDao.update(barcodeGroup);

		for (Item item : barcodeGroup.getForm().getItems())
			if (SiriusValidator.gz(item.getQuantity()))
			{
				Barcode barcode = genericDao.load(Barcode.class, item.getReference());
				barcode.setQuantity(item.getQuantity());

				genericDao.update(barcode);
			}
	}

	@AuditTrails(className = BarcodeGroup.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(BarcodeGroup barcodeGroup) throws ServiceException
	{
		genericDao.delete(barcodeGroup);
	}

	@AuditTrails(className = BarcodeGroup.class, actionType = AuditTrailsActionType.DELETE)
	public void deleteBarcode(Barcode barcode) throws ServiceException
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
}
