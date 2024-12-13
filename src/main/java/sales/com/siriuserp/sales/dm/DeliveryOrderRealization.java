package com.siriuserp.sales.dm;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import com.siriuserp.inventory.dm.GoodsIssue;
import com.siriuserp.inventory.dm.Issueable;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.JSONSupport;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PostalAddress;
import com.siriuserp.sdk.dm.Tax;

import javolution.util.FastSet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "delivery_order_realization")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DeliveryOrderRealization extends Model implements JSONSupport, Issueable {

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
    @Where(clause="accepted > 0")
	private Set<DeliveryOrderRealizationItem> accepteds = new FastSet<DeliveryOrderRealizationItem>();
    
    @OneToMany(mappedBy = "deliveryOrderRealization", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   	@LazyCollection(LazyCollectionOption.EXTRA)
   	@Fetch(FetchMode.SELECT)
   	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
   	@OrderBy("id")
    @Where(clause="shrinkage > 0")
   	private Set<DeliveryOrderRealizationItem> shrinks = new FastSet<DeliveryOrderRealizationItem>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_currency")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Currency currency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_tax")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Tax tax;

    public Set<DeliveryOrderRealizationItem> getItems()
	{
		Set<DeliveryOrderRealizationItem> items = new FastSet<DeliveryOrderRealizationItem>();
		items.addAll(getAccepteds());
		items.addAll(getShrinks());
		
		return items;
	}
    
    @Override
    public String getAuditCode() {
        return id + "," + code;
    }

    @Override
    public Set<DeliveryOrderRealizationItem> getIssueables()
    {
        return getItems();
    }

    @Override
    public Set<GoodsIssue> getIssueds() {
        HashSet<GoodsIssue> issueds = new HashSet<>();

        issueds.addAll(getIssueables().stream().flatMap(item -> item.getIssueds().stream()).collect(Collectors.toSet()));

        return issueds;
    }

    @Override
    public Tax getTax() {
        return this.tax;
    }

    @Override
    public Currency getCurrency() {
        return this.currency;
    }

    @Override
    public String getSelf()
    {
        return "Delivery Order Realization";
    }

    @Override
    public Party getParty() {
        return getCustomer();
    }

    @Override
    public String getRef() {
        return "";
    }
    
    public PostalAddress getShippingAddress() {
    	return getItems().stream().filter(item 
    		-> item.getDeliveryOrderItem().getSalesReferenceItem().getShippingAddress() != null).findFirst().get().getDeliveryOrderItem().getSalesReferenceItem().getShippingAddress();
    }
    
    public DeliveryOrder getDeliveryOrder() {
    	return getItems().stream().filter(item -> item.getDeliveryOrderItem().getDeliveryOrder() != null).findFirst().get().getDeliveryOrderItem().getDeliveryOrder();    	
    }
}
