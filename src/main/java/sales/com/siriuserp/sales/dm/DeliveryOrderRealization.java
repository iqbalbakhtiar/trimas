package com.siriuserp.sales.dm;

import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.JSONSupport;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Party;
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
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "delivery_order_realization")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DeliveryOrderRealization extends Model implements JSONSupport {

    private static final long serialVersionUID = -1654445314172791626L;

    @Column(name = "code")
    private String code;

    @Column(name = "date")
    private Date date;

    @Column(name = "acceptance_date")
    private Date acceptanceDate;

    @Column(name = "return_date")
    private Date returnDate;

    @Column(name = "note")
    private String note;

    @Column(name = "note_ext")
    private String noteExt;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="fk_party_organization")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Party organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_facility")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Facility facility;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_party_customer")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Party customer;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_party_expedition")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Party expedition;

    @Column(name = "vehicle")
    private String vehicle;

    @Column(name = "driver")
    private String driver;

    @OneToMany(mappedBy = "deliveryOrderRealization", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("id")
	private Set<DeliveryOrderRealizationItem> items = new FastSet<DeliveryOrderRealizationItem>();

    @Override
    public String getAuditCode() {
        return id + "," + code;
    }
}
