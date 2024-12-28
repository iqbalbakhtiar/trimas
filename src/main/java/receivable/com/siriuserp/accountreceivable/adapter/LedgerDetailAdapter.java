package com.siriuserp.accountreceivable.adapter;

import com.siriuserp.accountreceivable.dm.Billing;
import com.siriuserp.accountreceivable.dm.CreditMemo;
import com.siriuserp.accountreceivable.dm.ReceiptApplication;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.utility.DecimalHelper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class LedgerDetailAdapter implements Serializable {
    private static final long serialVersionUID = -8047393490684898995L;

    //opening for billing auto dor
    private BigDecimal openingStd = BigDecimal.ZERO;

    //opening for billing manual balance
    private BigDecimal openingMan = BigDecimal.ZERO;

    private BigDecimal openingCredit = BigDecimal.ZERO;

    private BigDecimal debit = BigDecimal.ZERO;
    private BigDecimal credit = BigDecimal.ZERO;
    private BigDecimal memo = BigDecimal.ZERO;

    private Party customer;
    private Billing billing;
    private CreditMemo creditMemoManual;
    private ReceiptApplication receiptApplication;
    private Date date;

    // Constructor
    public LedgerDetailAdapter(Party customer, BigDecimal openingStd, BigDecimal openingMan, BigDecimal openingCredit, BigDecimal debit, BigDecimal credit, BigDecimal memo)
    {
        this.customer = customer;

        this.openingStd = DecimalHelper.safe(openingStd);
        this.openingMan = DecimalHelper.safe(openingMan);
        this.openingCredit = DecimalHelper.safe(openingCredit);

        this.debit = DecimalHelper.safe(debit);
        this.credit = DecimalHelper.safe(credit);
        this.memo = memo = DecimalHelper.safe(memo);
    }

    public LedgerDetailAdapter(Party customer, BigDecimal openingStd, BigDecimal openingCredit, BigDecimal debit, BigDecimal credit, BigDecimal memo)
    {
        this.customer = customer;

        this.openingStd = DecimalHelper.safe(openingStd);
        this.openingCredit = DecimalHelper.safe(openingCredit);

        this.debit = DecimalHelper.safe(debit);
        this.credit = DecimalHelper.safe(credit);

        this.memo = memo = DecimalHelper.safe(memo);
    }

    public LedgerDetailAdapter(Party customer, BigDecimal openingStd, BigDecimal openingMan, BigDecimal openingCredit)
    {
        this.customer = customer;

        this.openingStd = DecimalHelper.safe(openingStd);
        this.openingMan = DecimalHelper.safe(openingMan);
        this.openingCredit = DecimalHelper.safe(openingCredit);
    }

    public LedgerDetailAdapter(Billing billing, CreditMemo creditMemoManual, ReceiptApplication receiptApplication)
    {
        this.billing = billing;
        this.creditMemoManual = creditMemoManual;
        this.receiptApplication = receiptApplication;
    }

    public LedgerDetailAdapter(Date date, Billing billing, BigDecimal debit)
    {
        this.date = date;
        this.billing = billing;
        this.debit = debit;
    }

    public LedgerDetailAdapter(Date date, CreditMemo creditMemoManual, BigDecimal credit)
    {
        this.date = date;
        this.creditMemoManual = creditMemoManual;
        this.credit = credit;
    }

    public LedgerDetailAdapter(Date date, ReceiptApplication receiptApplication, BigDecimal credit)
    {
        this.date = date;
        this.receiptApplication = receiptApplication;
        this.credit = credit;
    }

    // Adapter Method
    public BigDecimal getSupremeCredit()
    {
        return getCredit().setScale(2, RoundingMode.FLOOR).add(getMemo().setScale(2, RoundingMode.FLOOR));
    }

    public BigDecimal getOpeningBalance()
    {
        return getOpeningStd().setScale(2, RoundingMode.FLOOR).subtract(getOpeningMan().setScale(2, RoundingMode.FLOOR)).subtract(getOpeningCredit().setScale(2, RoundingMode.FLOOR));
    }

    public BigDecimal getClosingBalance()
    {
        return getOpeningBalance().setScale(2, RoundingMode.FLOOR).add(getDebit().setScale(2, RoundingMode.FLOOR)).subtract(getSupremeCredit().setScale(2, RoundingMode.FLOOR));
    }
}
