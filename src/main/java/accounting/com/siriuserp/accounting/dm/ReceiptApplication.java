package com.siriuserp.accounting.dm;

import com.siriuserp.sdk.dm.Model;
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
@Table(name = "receipt_application")
public class ReceiptApplication extends Model {
    private static final long serialVersionUID = -1192304649271117720L;

    @Column(name = "paid_amount")
    private BigDecimal paidAmount = BigDecimal.ZERO;

    @Column(name = "write_off")
    private BigDecimal writeOff = BigDecimal.ZERO;

    @Column(name = "write_off_type")
    @Enumerated(EnumType.STRING)
    private WriteOffType writeOffType;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="fk_billing")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Billing billing;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="fk_receipt")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Receipt receipt;

    @Override
    public String getAuditCode() {
        return "";
    }
}
