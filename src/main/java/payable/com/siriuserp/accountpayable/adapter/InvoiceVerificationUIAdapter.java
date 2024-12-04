package com.siriuserp.accountpayable.adapter;

import com.siriuserp.sdk.adapter.AbstractUIAdapter;
import com.siriuserp.sdk.dm.InvoiceVerification;
import com.siriuserp.sdk.dm.InvoiceVerificationReceipt;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class InvoiceVerificationUIAdapter extends AbstractUIAdapter {
    private static final long serialVersionUID = 7939536129053955867L;

    private InvoiceVerification invoiceVerification;
    private InvoiceVerificationReceipt invoiceVerificationReceipt;

    private List<InvoiceVerificationUIAdapter> items = new ArrayList<>();

    /**
     * On {@link InvoiceVerificationReceipt} calculate (Qty * Amount)
     */
    public BigDecimal getSubTotal() {
        if (invoiceVerificationReceipt != null) {
            return invoiceVerificationReceipt.getGoodsReceiptItem().getReceipted().multiply(
                    getInvoiceVerificationReceipt().getGoodsReceiptItem().getWarehouseTransactionItem().getMoney().getAmount()
            );
        }
        return BigDecimal.ZERO;
    }

    /**
     * Sum of getSubTotal method
     */
    public BigDecimal getTotalAmount() {
        return items.stream()
                .map(InvoiceVerificationUIAdapter::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Calculate tax based on TotalAmount and taxRate.
     * @return Calculated tax amount.
     */
    public BigDecimal getTaxAmount() {
        if (invoiceVerification != null && invoiceVerification.getTax() != null) {
            BigDecimal taxRate = invoiceVerification.getTax().getTaxRate();
            return getTotalAmount().multiply(taxRate).divide(BigDecimal.valueOf(100));
        }
        return BigDecimal.ZERO;
    }

    /**
     * Total Amount + Tax Amount
     */
    public BigDecimal getTotalTransaction() {
        return getTotalAmount().add(getTaxAmount());
    }

    // Constructor
    public InvoiceVerificationUIAdapter(InvoiceVerification invoiceVerification) {
        this.invoiceVerification = invoiceVerification;
    }

    public InvoiceVerificationUIAdapter(InvoiceVerificationReceipt receipt) {
        this.invoiceVerificationReceipt = receipt;
    }
}
