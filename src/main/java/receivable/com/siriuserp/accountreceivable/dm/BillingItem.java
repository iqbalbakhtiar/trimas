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

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "billing_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BillingItem extends Model {

    private static final long serialVersionUID = 5227583714908763004L;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="fk_billing")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Billing billing;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_billing_reference")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private BillingReferenceItem billingReferenceItem;

    @Override
    public String getAuditCode() {
        return "";
    }
}
