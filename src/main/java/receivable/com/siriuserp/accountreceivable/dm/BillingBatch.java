package com.siriuserp.accountreceivable.dm;

import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Party;
import javolution.util.FastSet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "billing_batch")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BillingBatch extends Model {
    private static final long serialVersionUID = 6980678262446540016L;

    @Column(name = "code")
    private String code;

    @Column(name = "date")
    private Date date;

    @Column(name = "note")
    private String note;

    @Column(name = "amount")
    private BigDecimal amount = BigDecimal.ZERO;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="fk_party_customer")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Party customer;
    
    @OneToMany(mappedBy = "billingBatch", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Fetch(FetchMode.SELECT)
    @Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
    @OrderBy("id")
    private Set<BillingBatchItem> items = new FastSet<BillingBatchItem>();

    @Override
    public String getAuditCode() {
        return id + "," + code;
    }
}
