package com.siriuserp.accountpayable.dm;

import com.siriuserp.accountreceivable.dm.BankAccount;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.PaymentMethodType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="payment_information")
public class PaymentInformation extends Model {
    private static final long serialVersionUID = 37092081305902289L;

    @Column(name="due_date")
    private Date dueDate;

    @Column(name="amount")
    private BigDecimal amount = BigDecimal.ZERO;

    @Column(name="bank_charges")
    private BigDecimal bankCharges = BigDecimal.ZERO;

    @Column(name="other_charges")
    private BigDecimal otherCharges = BigDecimal.ZERO;

    @Column(name="reference")
    private String reference;

    @Column(name="note",length=255)
    private String note;

    @Enumerated(EnumType.STRING)
    @Column(name="payment_method_type")
    private PaymentMethodType paymentMethodType;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_bank_account")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private BankAccount bankAccount;

    @Override
    public String getAuditCode() {
        return "";
    }
}
