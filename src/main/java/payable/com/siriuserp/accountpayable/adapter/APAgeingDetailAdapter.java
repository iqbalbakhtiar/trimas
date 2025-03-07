package com.siriuserp.accountpayable.adapter;

import com.siriuserp.accountpayable.dm.Payable;
import com.siriuserp.accountpayable.dm.PaymentApplication;
import com.siriuserp.sdk.adapter.AbstractUIAdapter;
import com.siriuserp.sdk.dm.Party;
import javolution.util.FastList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Betsu Brahmana Restu
 * @author Rama Almer Felix
 * PT. Sirius Indonesia
 * betsu@siriuserp.com
 */

@Getter
@Setter
@NoArgsConstructor
public class APAgeingDetailAdapter extends AbstractUIAdapter {
    private static final long serialVersionUID = 8544742545943861546L;

    private Date date;
    private Party supplier;
    private Payable invoice;
    private PaymentApplication application;
    private Party organization;

//    private APAgeingUninvoiceAdapter uninvoice;

    private BigDecimal balance = BigDecimal.ZERO;
    private BigDecimal notYetDue = BigDecimal.ZERO;
    private BigDecimal fstOverDue = BigDecimal.ZERO;
    private BigDecimal sndOverDue = BigDecimal.ZERO;
    private BigDecimal thdOverDue = BigDecimal.ZERO;
    private BigDecimal fthOverDue = BigDecimal.ZERO;

    private FastList<APAgeingDetailAdapter> items = new FastList<APAgeingDetailAdapter>();
}
