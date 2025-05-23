package com.siriuserp.accountreceivable.service;

import com.siriuserp.accounting.form.AccountingForm;
import com.siriuserp.accountreceivable.adapter.BillingAdapter;
import com.siriuserp.accountreceivable.dm.Billing;
import com.siriuserp.accountreceivable.dm.BillingCollectingStatus;
import com.siriuserp.accountreceivable.dm.BillingItem;
import com.siriuserp.accountreceivable.dm.BillingReferenceItem;
import com.siriuserp.accountreceivable.dm.BillingReferenceType;
import com.siriuserp.accountreceivable.dm.BillingType;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.CurrencyDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.dm.Money;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.dm.Tax;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.FormHelper;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;
import javolution.util.FastMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Component
@Transactional(rollbackFor = Exception.class)
public class BillingManualService extends Service {
    @Autowired
    private GenericDao genericDao;

    @Autowired
    private CodeSequenceDao codeSequenceDao;

    @Autowired
    private CurrencyDao currencyDao;

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws Exception
    {
        FastMap<String, Object> map = new FastMap<String, Object>();
        map.put("filterCriteria", filterCriteria);
        map.put("billings", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));

        return map;
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @InjectParty(keyName = "billing_form")
    public Map<String, Object> preadd() {
        FastMap<String, Object> map = new FastMap<String, Object>();

        map.put("billing_form", new AccountingForm());
        map.put("taxes", genericDao.loadAll(Tax.class));
        map.put("currencys", genericDao.loadAll(Currency.class));
        map.put("defaultCurrency", currencyDao.loadDefaultCurrency());
        return map;
    }

    @AuditTrails(className = Billing.class, actionType = AuditTrailsActionType.CREATE)
    public void add(Billing billing) throws ServiceException {
        billing.setMoney(new Money());
        billing.getMoney().setCurrency(billing.getForm().getCurrency());
        billing.getMoney().setAmount(billing.getForm().getAmount());
        billing.getMoney().setExchangeType(billing.getForm().getExchangeType());
        billing.getMoney().setRate(billing.getForm().getRate());

        billing.setCode(GeneratorHelper.instance().generate(TableType.BILLING, codeSequenceDao));
        billing.setBillingType(genericDao.load(BillingType.class, BillingType.MANUAL));

        billing.setUnpaid(billing.getForm().getAmount());
        billing.setDueDate(DateHelper.plusDays(billing.getDate(), billing.getTerm()));
        billing.setCreatedBy(getPerson());
        billing.setCreatedDate(DateHelper.now());

        // Looping Item
        for (Item item : billing.getForm().getItems()) {
            // TODO Copy from Billing form to Reference
            BillingReferenceItem referenceItem = new BillingReferenceItem();
            referenceItem.setMoney(new Money());
            referenceItem.getMoney().setCurrency(billing.getForm().getCurrency());
            referenceItem.getMoney().setAmount(item.getAmount());
            referenceItem.getMoney().setExchangeType(billing.getForm().getExchangeType());
            referenceItem.getMoney().setRate(billing.getForm().getRate());

            referenceItem.setReferenceName("MANUAL");
            referenceItem.setQuantity(item.getQuantity());
            referenceItem.setReferenceUom(item.getProduct().getUnitOfMeasure().getMeasureId());
            referenceItem.setOrganization(billing.getOrganization());
            referenceItem.setCustomer(billing.getCustomer());
            referenceItem.setReferenceType(BillingReferenceType.MANUAL);
            referenceItem.setCreatedBy(getPerson());
            referenceItem.setCreatedDate(DateHelper.now());
            referenceItem.setTax(billing.getForm().getTax());
            referenceItem.setProduct(item.getProduct());
            referenceItem.setReferenceDate(billing.getDate());

            referenceItem.setBilled(true);
            BillingItem billingItem = new BillingItem();
            billingItem.setBilling(billing);
            billingItem.setBillingReferenceItem(referenceItem);

            billing.getItems().add(billingItem);
        }


        // Set Billing Default Collecting Status
        BillingCollectingStatus collecting = new BillingCollectingStatus();
        collecting.setDueDate(billing.getDueDate());
        collecting.setBilling(billing);
        billing.setCollectingStatus(collecting);

        genericDao.add(billing);
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public FastMap<String,Object> preedit(Long id) throws Exception {
        FastMap<String, Object> map = new FastMap<String, Object>();
        Billing billing = genericDao.load(Billing.class, id);
        AccountingForm form = FormHelper.bind(AccountingForm.class, billing);
        BillingAdapter adapter = new BillingAdapter(form.getBilling());

        map.put("billing_form", form);
        map.put("billing_edit", adapter);
        map.put("defaultCurrency", currencyDao.loadDefaultCurrency());

        System.out.println(form.getBilling().getBillingType().getUrl());
        return map;
    }

    @AuditTrails(className = Billing.class, actionType = AuditTrailsActionType.UPDATE)
    public void edit(Billing billing) throws ServiceException {
        billing.setUpdatedBy(getPerson());
        billing.setUpdatedDate(DateHelper.now());

        genericDao.update(billing);
    }
}
