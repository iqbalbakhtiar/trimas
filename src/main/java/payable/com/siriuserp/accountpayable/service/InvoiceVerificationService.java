package com.siriuserp.accountpayable.service;

import com.siriuserp.accountpayable.adapter.InvoiceVerificationUIAdapter;
import com.siriuserp.accountpayable.form.PaymentForm;
import com.siriuserp.inventory.dm.GoodsReceipt;
import com.siriuserp.inventory.form.TransactionForm;
import com.siriuserp.inventory.service.GoodsReceiptService;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.CurrencyDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.InvoiceVerification;
import com.siriuserp.sdk.dm.TableType;
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
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Transactional(rollbackFor = Exception.class)
public class InvoiceVerificationService {

    @Autowired
    private GenericDao genericDao;

    @Autowired
    private CurrencyDao currencyDao;

    @Autowired
    private CodeSequenceDao codeSequenceDao;

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws Exception {
        FastMap<String, Object> map = new FastMap<String, Object>();
        map.put("filterCriteria", filterCriteria);
        map.put("verifications", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));

        return map;
    }

    /**
     * Fungsi ini untuk Add Invoice Verification yang terbuat otomatis ketika membuat GoodsReceipt
     * @param invoiceVerification From {@link GoodsReceiptService#createInvoice}
     */
    @AuditTrails(className = InvoiceVerification.class, actionType = AuditTrailsActionType.CREATE)
    public void add(InvoiceVerification invoiceVerification) throws Exception {
        TransactionForm form = (TransactionForm) invoiceVerification.getForm();

        // Generate Code and set verificated to true
        invoiceVerification.setCode(GeneratorHelper.instance().generate(TableType.INVOICE_VERIFICATION, codeSequenceDao));
        invoiceVerification.setVerificated(true);

        genericDao.add(invoiceVerification);

        // Update GR Verificated to true
        GoodsReceipt goodsReceipt = form.getGoodsReceipt();
        goodsReceipt.setVerificated(true);
        genericDao.update(goodsReceipt);
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> preedit(Long id) throws Exception {
        FastMap<String, Object> map = new FastMap<String, Object>();
        InvoiceVerification invoiceVerification = genericDao.load(InvoiceVerification.class, id);
        PaymentForm form = FormHelper.bind(PaymentForm.class, invoiceVerification);
        InvoiceVerificationUIAdapter adapter = new InvoiceVerificationUIAdapter(invoiceVerification);

        // Setel daftar items pada adapter menggunakan method reference
        adapter.setItems(
                invoiceVerification.getReceipts().stream()
                        .map(InvoiceVerificationUIAdapter::new)
                        .collect(Collectors.toList())
        );

        if (!form.getInvoiceVerification().getReceipts().isEmpty()) {
            map.put("poNo", form.getInvoiceVerification().getReceipts().iterator().next() // Get First Invoice Receipt
                    .getGoodsReceiptItem().getWarehouseTransactionItem().getReferenceItem().getReferenceCode());
        }

        map.put("verification_edit", form);
        map.put("verification_adapter", adapter);

        return map;
    }

    @AuditTrails(className = InvoiceVerification.class, actionType = AuditTrailsActionType.UPDATE)
    public void edit(InvoiceVerification verification) throws Exception {
        genericDao.update(verification);
    }
}
