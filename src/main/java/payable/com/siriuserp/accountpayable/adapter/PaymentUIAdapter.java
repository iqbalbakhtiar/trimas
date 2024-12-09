package com.siriuserp.accountpayable.adapter;

import com.siriuserp.accountpayable.dm.Payment;
import com.siriuserp.accountpayable.dm.PaymentApplication;
import com.siriuserp.sdk.adapter.AbstractUIAdapter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class PaymentUIAdapter extends AbstractUIAdapter {
    private static final long serialVersionUID = 4488305643655510146L;

    private Payment payment;

    public BigDecimal getWriteoff()
    {
        BigDecimal decimal = BigDecimal.valueOf(0);

        for(PaymentApplication application:payment.getApplications()){
            if(application.getWriteOff() != null){
                decimal = decimal.add(application.getWriteOff());
            }
        }
        return decimal;
    }

    public BigDecimal getDefWriteoff()
    {
        BigDecimal decimal = BigDecimal.valueOf(0);

        for(PaymentApplication application:payment.getApplications()){
            if(application.getWriteOff() != null){
                decimal = decimal.add(application.getWriteOff().multiply(payment.getExchange().getRate()));
            }
        }

        return decimal;
    }

    public BigDecimal getPaid()
    {
        BigDecimal decimal = BigDecimal.valueOf(0);

        for(PaymentApplication application:payment.getApplications())
            decimal = decimal.add(application.getPaidAmount());

        return decimal;
    }

    public BigDecimal getDefPaid()
    {
        BigDecimal decimal = BigDecimal.valueOf(0);

        for(PaymentApplication application:payment.getApplications())
            decimal = decimal.add(application.getPaidAmount().multiply(payment.getExchange().getRate()));

        return decimal;
    }

}
