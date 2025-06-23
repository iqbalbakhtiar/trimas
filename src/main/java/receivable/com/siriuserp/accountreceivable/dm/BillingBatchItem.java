package com.siriuserp.accountreceivable.dm;

import com.siriuserp.sdk.dm.Model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "billing_batch_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BillingBatchItem extends Model {
    private static final long serialVersionUID = -5870497298972371426L;

    @Column(name = "amount")
    private BigDecimal amount = BigDecimal.ZERO;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="fk_billing")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Billing billing;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="fk_billing_batch")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private BillingBatch billingBatch;

    @Override
    public String getAuditCode() {
        return "";
    }
}
