package com.siriuserp.accountpayable.dm;

import com.siriuserp.accountreceivable.dm.WriteOffType;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Payable;
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
@Table(name = "payment_application")
public class PaymentApplication extends Model {
    private static final long serialVersionUID = 5521535215726930274L;

    @Column(name = "due_date")
    private Date appliedDate = new Date();

    @Column(name = "paid_amount")
    private BigDecimal paidAmount = BigDecimal.ZERO;

    @Column(name = "write_off")
    private BigDecimal writeOff = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "write_off_type")
    private WriteOffType writeoffType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_payable")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Payable payable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_payment")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Payment payment;

    @Override
    public String getAuditCode() {
        return "";
    }
}
