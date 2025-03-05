package com.siriuserp.procurement.adapter;

import com.siriuserp.procurement.dm.PurchaseOrder;
import com.siriuserp.procurement.dm.PurchaseOrderItem;
import com.siriuserp.sdk.adapter.AbstractUIAdapter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderAdapter extends AbstractUIAdapter {
    private static final long serialVersionUID = -7391182697006519917L;

    private PurchaseOrder purchaseOrder;

    public BigDecimal getTotalItemAmount() {
        BigDecimal total = BigDecimal.ZERO;

        for (PurchaseOrderItem item : purchaseOrder.getItems()) {
            total = total.add(item.getTotalAmount());
        }

        return total;
    }

    public BigDecimal getTaxAmount() {
        return getTotalItemAmount().multiply(getPurchaseOrder().getTax().getTaxRate().divide(BigDecimal.valueOf(100)));
    }

    // Used in Direct Purchase Order (TotalItemAmount + taxAmount)
    public BigDecimal getTotalTransaction() {
        return getTotalItemAmount().add(getTaxAmount());
    }
}
