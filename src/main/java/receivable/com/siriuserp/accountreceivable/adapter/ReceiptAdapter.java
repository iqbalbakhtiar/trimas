package com.siriuserp.accountreceivable.adapter;

import com.siriuserp.accountreceivable.dm.Receipt;
import com.siriuserp.accountreceivable.dm.ReceiptApplication;
import com.siriuserp.sdk.adapter.AbstractUIAdapter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptAdapter extends AbstractUIAdapter {
    private static final long serialVersionUID = 1895850580119133451L;

    private Receipt receipt;

    public BigDecimal getTotalPaidAmount() {
        BigDecimal amount = BigDecimal.ZERO;

        for (ReceiptApplication application: receipt.getApplications()) {
            amount = amount.add(application.getPaidAmount());
        }

        return amount;
    }
}
