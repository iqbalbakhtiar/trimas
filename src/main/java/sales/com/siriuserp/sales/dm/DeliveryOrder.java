package com.siriuserp.sales.dm;

import com.siriuserp.sdk.dm.*;
import javolution.util.FastSet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "delivery_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DeliveryOrder extends Model implements JSONSupport {

    private static final long serialVersionUID = -8504495874811110216L;

    @Column(name = "code")
    private String code;

    @Column(name = "date")
    private Date date;

    @Column(name = "rit")
    private BigDecimal rit = BigDecimal.ONE;

    @Column(name = "realization")
    @Type(type = "yes_no")
    private boolean realization = Boolean.FALSE;

    @Column(name = "note")
    private String note;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    protected SOStatus status = SOStatus.OPEN;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_party_organization")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Party organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_facility")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Facility facility;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_party_customer")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Party customer;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_shipping_address")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private PostalAddress shippingAddress;

    @OneToMany(mappedBy = "deliveryOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("id")
	private Set<DeliveryOrderItem> items = new FastSet<DeliveryOrderItem>();

    @Override
    public String getAuditCode() {
        return id + "," + code;
    }
}
