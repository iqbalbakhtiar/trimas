package com.siriuserp.inventory.service;

import com.siriuserp.accounting.form.AccountingForm;
import com.siriuserp.accountreceivable.dm.BillingBatch;
import com.siriuserp.accountreceivable.dm.BillingBatchItem;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.CurrencyDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.dm.PostalAddress;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.FormHelper;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;
import javolution.util.FastMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(rollbackFor = Exception.class)
public class BillingBatchService extends Service {
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
        map.put("batches", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));

        return map;
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @InjectParty(keyName = "billing_batch_form")
	public FastMap<String, Object> preadd() throws ServiceException {
		FastMap<String, Object> map = new FastMap<String, Object>();

        map.put("billing_batch_form", new AccountingForm());

		return map;
	}

    @AuditTrails(className = BillingBatch.class, actionType = AuditTrailsActionType.CREATE)
    public void add(BillingBatch billBatch) throws Exception {
        AccountingForm form = (AccountingForm) billBatch.getForm();

        billBatch.setCode(GeneratorHelper.instance().generate(TableType.BILLING_BATCH, codeSequenceDao));

        for (Item item : form.getItems()) {
            if (item.getBilling() != null && item.isEnabled()) {
                BillingBatchItem contraBonItem = new BillingBatchItem();
                contraBonItem.setBilling(item.getBilling());
                contraBonItem.setAmount(item.getBilling().getUnpaid());
                contraBonItem.setBillingBatch(billBatch);

                billBatch.getItems().add(contraBonItem);
            }
        }

        genericDao.add(billBatch);
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public FastMap<String,Object> preedit(Long id) throws Exception {
        FastMap<String, Object> map = new FastMap<String, Object>();
		BillingBatch billingBatch = genericDao.load(BillingBatch.class, id);
		AccountingForm form = FormHelper.bind(AccountingForm.class, billingBatch);

        // Get first active customer address, filter by active / enabled
        // Not filter by "selected" / "default" because data is always false
        PostalAddress customerAddress = billingBatch.getCustomer().getPostalAddresses().stream()
                        .filter(PostalAddress::isEnabled).findFirst().orElse(null);

        map.put("billing_batch_form", form);
        map.put("customerAddress", customerAddress);
        map.put("defaultCurrency", currencyDao.loadDefaultCurrency());

        return map;
    }

    @AuditTrails(className = BillingBatch.class, actionType = AuditTrailsActionType.UPDATE)
    public void edit(BillingBatch billBatch) throws Exception {
        genericDao.update(billBatch);
    }
}
