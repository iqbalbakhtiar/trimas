package com.siriuserp.accountpayable.dm;

import com.siriuserp.accounting.dm.BankAccount;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Exchange;
import com.siriuserp.sdk.dm.ExchangeType;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PaymentMethodType;

import java.math.BigDecimal;
import java.util.Date;

public interface CashBankTransactionReferenceable {
    public Long getReferenceId();
    public Long getExtReferenceId();
    public String getCode();
    public Date getDate();
    public BigDecimal getAmount();
    public ExchangeType getExchangeType();
    public PaymentMethodType getPaymentMethodType();
    public String getUri();
    public String getNote();
    public Party getOrganization();
    public Party getParty();
    public Currency getCurrency();
    public Exchange getExchange();
    public BankAccount getBankAccount();
//    public JournalEntry getCancellationJournalEntry();
}
