package com.siriuserp.accountpayable.service;

import com.siriuserp.accountpayable.adapter.InvoiceVerificationAdapter;
import com.siriuserp.accountpayable.dm.InvoiceVerification;
import com.siriuserp.accountpayable.dm.InvoiceVerificationItem;
import com.siriuserp.accountpayable.dm.InvoiceVerificationType;
import com.siriuserp.accountpayable.form.PaymentForm;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.CreditTermDao;
import com.siriuserp.sdk.dao.CurrencyDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dao.PartyRelationshipDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.CreditTerm;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.dm.PartyRelationship;
import com.siriuserp.sdk.dm.PartyRelationshipType;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.dm.Tax;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.EnglishNumber;
import com.siriuserp.sdk.utility.EnglishNumberHelper;
import com.siriuserp.sdk.utility.FormHelper;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;
import javolution.util.FastMap;
import org.apache.commons.lang.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.RoundingMode;
import java.util.Map;

/**
 * @author Rama Almer Felix
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class ManualInvoiceVerificationService
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
	private CurrencyDao currencyDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("filterCriteria", filterCriteria);
		map.put("verifications", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));
		map.put("taxes", genericDao.loadAll(Tax.class));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	@InjectParty(keyName = "verification_form")
	public FastMap<String, Object> preadd() {
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("verification_form", new PaymentForm());
		map.put("taxes", genericDao.loadAll(Tax.class));

		return map;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> viewJson(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("verifications", genericDao.filter(QueryFactory.create(filterCriteria, queryclass)));

		return map;
	}

	@AuditTrails(className = InvoiceVerification.class, actionType = AuditTrailsActionType.CREATE)
	public void add(InvoiceVerification invoiceVerification) throws Exception
	{
		PaymentForm form = (PaymentForm) invoiceVerification.getForm();
		invoiceVerification.setCode(GeneratorHelper.instance().generate(TableType.INVOICE_VERIFICATION, codeSequenceDao, invoiceVerification.getOrganization()));
		invoiceVerification.getMoney().setCurrency(currencyDao.loadDefaultCurrency());
		invoiceVerification.getMoney().setAmount(form.getAmount());
		invoiceVerification.setInvoiceType(InvoiceVerificationType.MANUAL);
		invoiceVerification.setUnpaid(form.getAmount());

		for (Item item : form.getItems()) {
			if (item.getProduct() != null) {
				InvoiceVerificationItem invoiceItem = new InvoiceVerificationItem();
				invoiceItem.setProduct(item.getProduct());
				invoiceItem.setQuantity(item.getQuantity());
				invoiceItem.getMoney().setAmount(item.getAmount());
				invoiceItem.setDiscount(item.getDiscount());
				invoiceItem.setInvoiceVerification(invoiceVerification);

				invoiceVerification.getItems().add(invoiceItem);
			}
		}

		// Set Due Date by Load active Credit Term from Supplier
		PartyRelationship relationship = partyRelationshipDao.load(invoiceVerification.getSupplier().getId(), invoiceVerification.getOrganization().getId(), PartyRelationshipType.SUPPLIER_RELATIONSHIP);
		CreditTerm creditTerm = creditTermDao.loadByRelationship(relationship.getId(), true, invoiceVerification.getDate());
		if (creditTerm == null) {
			throw new ServiceException("Supplier doesn't have active Credit Term, please set it first on supplier page.");
		}
		invoiceVerification.setDueDate(DateHelper.plusDays(invoiceVerification.getDate(), creditTerm.getTerm()));

//		throw new ServiceException("not implemented yet!");
		genericDao.add(invoiceVerification);

//		if (form.getGoodsReceipt() != null)
//		{
//			GoodsReceipt goodsReceipt = form.getGoodsReceipt();
//			goodsReceipt.setVerificated(true);
//			genericDao.update(goodsReceipt);
//		}
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preedit(Long id) throws Exception
	{
		PaymentForm form = FormHelper.bind(PaymentForm.class, genericDao.load(InvoiceVerification.class, id));
		InvoiceVerificationAdapter adapter = new InvoiceVerificationAdapter(form.getInvoiceVerification());

		String said = EnglishNumberHelper.convert(adapter.getTotalAfterTax().setScale(2, RoundingMode.HALF_UP));
		String saidId = EnglishNumber.convertIdComma(adapter.getTotalAfterTax().setScale(2, RoundingMode.HALF_UP));

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("verification_form", form);
		map.put("verification_edit", form.getInvoiceVerification());
		map.put("adapter", adapter);
		map.put("said", WordUtils.capitalizeFully(said)); // Capitalize all words
		map.put("said_id", WordUtils.capitalizeFully(saidId));

		return map;
	}

	@AuditTrails(className = InvoiceVerification.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(InvoiceVerification verification) throws Exception
	{
		genericDao.update(verification);
	}
}
