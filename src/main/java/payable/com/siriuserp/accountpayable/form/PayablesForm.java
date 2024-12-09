package com.siriuserp.accountpayable.form;

import com.siriuserp.accountpayable.dm.Payment;
import com.siriuserp.accountpayable.dm.PaymentInformation;
import com.siriuserp.sdk.dm.Form;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayablesForm extends Form {
    private static final long serialVersionUID = -8755566007524678306L;

    private PaymentInformation paymentInformation;
    private Payment payment;
}
