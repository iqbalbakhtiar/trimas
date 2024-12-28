package com.siriuserp.accountreceivable.dm;

import com.siriuserp.accounting.dm.BankAccount;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.PaymentMethodType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "receipt_information")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ReceiptInformation extends Model {
    private static final long serialVersionUID = -4441340959209700042L;

    @Column(name = "amount")
    private BigDecimal amount = BigDecimal.ZERO;

    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "bank_charges")
    private BigDecimal bankCharges = BigDecimal.ZERO;

    @Column(name = "other_charges")
    private BigDecimal otherCharges = BigDecimal.ZERO;

    @Column(name = "reference")
    private String reference;

    @Column(name = "note")
    private String note;

    @Column(name = "payment_method_type")
    @Enumerated(EnumType.STRING)
    private PaymentMethodType paymentMethodType;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_bank_account")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private BankAccount bankAccount;

    @OneToOne(mappedBy = "receiptInformation", fetch=FetchType.LAZY)
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Receipt receipt;

    @Override
    public String getAuditCode() {
        return id + "," + reference;
    }
}
