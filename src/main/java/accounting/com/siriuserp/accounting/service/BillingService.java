package com.siriuserp.accounting.service;

import com.siriuserp.accounting.adapter.BillingAdapter;
import com.siriuserp.accounting.dm.*;
import com.siriuserp.accounting.form.AccountingForm;
import com.siriuserp.sales.dm.DeliveryOrderRealization;
import com.siriuserp.sales.dm.DeliveryOrderRealizationItem;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.PartyBankAccount;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.*;
import javolution.util.FastMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Set;

@Component
@Transactional(rollbackFor = Exception.class)
public class BillingService extends Service {

    @Autowired
    private GenericDao genericDao;

    @Autowired
    private CodeSequenceDao codeSequenceDao;

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("filterCriteria", filterCriteria);
		map.put("billings", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));

		return map;
	}

	@AuditTrails(className = Billing.class, actionType = AuditTrailsActionType.CREATE)
	public void add(Billing billing) throws Exception { // Used in DOR Add
		AccountingForm form = (AccountingForm) billing.getForm();

		billing.setCode(GeneratorHelper.instance().generate(TableType.BILLING, codeSequenceDao));

		for (Item item : form.getItems())
		{
			BillingReferenceItem reference = genericDao.load(BillingReferenceItem.class, item.getBillingReferenceItem().getId());
			reference.setBilled(true);
			reference.setReferenceUom(reference.getProduct().getUnitOfMeasure().getMeasureId());

			genericDao.update(reference);

			BillingItem billingItem = new BillingItem();
			billingItem.setBilling(billing);
			billingItem.setBillingReferenceItem(reference);

			billing.getItems().add(billingItem);
		}

		// Set Billing Default Collecting Status
		BillingCollectingStatus collecting = new BillingCollectingStatus();
		collecting.setDueDate(billing.getDueDate());
		collecting.setBilling(billing);
		billing.setCollectingStatus(collecting);

		billing.setUpdatedBy(null);
		billing.setUpdatedDate(null);
		genericDao.add(billing);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public FastMap<String,Object> preedit(Long id) throws Exception {
		FastMap<String, Object> map = new FastMap<String, Object>();
		Billing billing = genericDao.load(Billing.class, id);
		AccountingForm form = FormHelper.bind(AccountingForm.class, billing);
		BillingAdapter adapter = new BillingAdapter(form.getBilling());

		// Find DO Code for Print Out
		for (BillingItem billingItem: billing.getItems()){
			if (billingItem.getBillingReferenceItem().getReferenceId() != null) {
				DeliveryOrderRealization dor = genericDao.load(DeliveryOrderRealization.class, billingItem.getBillingReferenceItem().getReferenceId());
				for (DeliveryOrderRealizationItem dorItem: dor.getItems()){
					if (dorItem.getDeliveryOrderItem().getDeliveryOrder().getCode() != null
						&& !dorItem.getDeliveryOrderItem().getDeliveryOrder().getCode().isEmpty()) {
						map.put("doCode", dorItem.getDeliveryOrderItem().getDeliveryOrder().getCode());
						break;
					}
				}
			}
		}

		// Get Active Organization BankAccount for Print Out
		Set<PartyBankAccount> partyBankAccounts = billing.getOrganization().getPartyBankAccounts();
		BankAccount activeBankAccount = partyBankAccounts.stream()
				.filter(PartyBankAccount::isEnabled) // Filter hanya yang enabled
				.map(PartyBankAccount::getBankAccount) // Ambil BankAccount dari PartyBankAccount
				.filter(bankAccount -> bankAccount != null) // Pastikan BankAccount tidak null
				.max(Comparator.comparing(Model::getCreatedDate)) // Ambil BankAccount dengan createdDate terbaru
				.orElse(null);

		map.put("bankAccount", activeBankAccount);
		map.put("billing_form", form);
		map.put("billing_edit", adapter);

		return map;
	}

	@AuditTrails(className = Billing.class, actionType = AuditTrailsActionType.UPDATE)
    public void edit(AccountingForm form) throws Exception {
        form.getBilling().setUpdatedBy(getPerson());
        form.getBilling().setUpdatedDate(DateHelper.now());

        genericDao.update(form.getBilling()); // Update Billing Directly from JSP
    }

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> viewJson(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("billings", genericDao.filter(QueryFactory.create(filterCriteria, queryclass)));

		return map;
	}
}
