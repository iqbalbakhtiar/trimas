/**
 * File Name  : PurchaseRequisitionService.java
 * Created On : Feb 20, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.procurement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.procurement.dm.PurchaseRequisition;
import com.siriuserp.procurement.dm.PurchaseRequisitionItem;
import com.siriuserp.procurement.form.PurchaseForm;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.FormHelper;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;
import com.siriuserp.sdk.utility.UserHelper;

import javolution.util.FastMap;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class PurchaseRequisitionService
{
	@Autowired
	private GenericDao genericDao;

	@Autowired
	private CodeSequenceDao codeSequenceDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends AbstractGridViewQuery> queryclass) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("requisitions", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));
		map.put("filterCriteria", filterCriteria);

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	@InjectParty(keyName = "requisition_form")
	public FastMap<String, Object> preadd() throws Exception
	{
		PurchaseForm form = new PurchaseForm();
		form.setRequisitioner(UserHelper.activePerson());

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("requisition_form", form);

		return map;
	}

	@AuditTrails(className = PurchaseRequisition.class, actionType = AuditTrailsActionType.CREATE)
	public void add(PurchaseRequisition requisition) throws Exception
	{
		PurchaseForm form = (PurchaseForm) requisition.getForm();
		requisition.setCode(GeneratorHelper.instance().generate(TableType.PURCHASE_REQUISITION, codeSequenceDao));

		for (Item item : form.getItems())
		{
			if (item.getProduct() != null)
			{
				PurchaseRequisitionItem requisitionItem = new PurchaseRequisitionItem();
				requisitionItem.setPurchaseRequisition(requisition);
				requisitionItem.setProduct(item.getProduct());
				requisitionItem.setQuantity(item.getQuantity());
				requisitionItem.setNote(item.getNote());

				requisition.getItems().add(requisitionItem);
			}
		}

		genericDao.add(requisition);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id) throws Exception
	{
		PurchaseForm purchaseForm = FormHelper.bind(PurchaseForm.class, load(id));

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("requisition_form", purchaseForm);
		map.put("requisition_edit", purchaseForm.getPurchaseRequisition());

		return map;
	}

	@AuditTrails(className = PurchaseRequisition.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(PurchaseRequisition requisition) throws Exception
	{
		genericDao.update(requisition);
	}

	@AuditTrails(className = PurchaseRequisition.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(PurchaseRequisition requisition) throws ServiceException
	{
		genericDao.delete(requisition);
	}

	@Transactional(readOnly = false)
	public PurchaseRequisition load(Long id)
	{
		return genericDao.load(PurchaseRequisition.class, id);
	}
}
