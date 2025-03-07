/**
 * File Name  : InvoiceVerificationService.java
 * Created On : Feb 26, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountpayable.service;

import java.math.RoundingMode;
import java.util.Map;

import com.siriuserp.sdk.dm.Tax;
import com.siriuserp.sdk.utility.EnglishNumber;
import com.siriuserp.sdk.utility.EnglishNumberHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accountpayable.adapter.InvoiceVerificationAdapter;
import com.siriuserp.accountpayable.dm.InvoiceVerification;
import com.siriuserp.accountpayable.form.PaymentForm;
import com.siriuserp.inventory.dm.GoodsReceipt;
import com.siriuserp.inventory.form.InventoryForm;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.FormHelper;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;

import javolution.util.FastMap;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class InvoiceVerificationService
{
	@Autowired
	private GenericDao genericDao;

	@Autowired
	private CodeSequenceDao codeSequenceDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("filterCriteria", filterCriteria);
		map.put("verifications", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));
		map.put("taxes", genericDao.loadAll(Tax.class));

		return map;
	}

	@AuditTrails(className = InvoiceVerification.class, actionType = AuditTrailsActionType.CREATE)
	public void add(InvoiceVerification invoiceVerification) throws Exception
	{
		InventoryForm form = (InventoryForm) invoiceVerification.getForm();
		invoiceVerification.setCode(GeneratorHelper.instance().generate(TableType.INVOICE_VERIFICATION, codeSequenceDao, invoiceVerification.getOrganization()));

		genericDao.add(invoiceVerification);

		if (form.getGoodsReceipt() != null)
		{
			GoodsReceipt goodsReceipt = form.getGoodsReceipt();
			goodsReceipt.setVerificated(true);
			genericDao.update(goodsReceipt);
		}
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preedit(Long id) throws Exception
	{
		PaymentForm form = FormHelper.bind(PaymentForm.class, genericDao.load(InvoiceVerification.class, id));
		InvoiceVerificationAdapter adapter = new InvoiceVerificationAdapter(form.getInvoiceVerification());

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("verification_form", form);
		map.put("verification_edit", form.getInvoiceVerification());
		map.put("adapter", adapter);
		map.put("said", EnglishNumberHelper.convert(adapter.getTotalAfterTax().setScale(2, RoundingMode.HALF_UP)));
		map.put("said_id", EnglishNumber.convertIdComma(adapter.getTotalAfterTax().setScale(2, RoundingMode.HALF_UP)));

		return map;
	}

	@AuditTrails(className = InvoiceVerification.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(InvoiceVerification verification) throws Exception
	{
		genericDao.update(verification);
	}
}
