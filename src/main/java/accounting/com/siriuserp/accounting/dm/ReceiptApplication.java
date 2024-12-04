package com.siriuserp.accounting.dm;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.siriuserp.sdk.dm.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
