package com.siriuserp.sdk.dm;

import com.siriuserp.inventory.dm.Product;
import com.siriuserp.sales.dm.DeliveryOrderItem;
import com.siriuserp.sales.dm.SalesType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "sales_reference_item")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class SalesReferenceItem extends Model implements JSONSupport {

	private static final long serialVersionUID = 4479431094475781734L;
	
	@Column(name = "reference_id")
	private Long referenceId;
	
	@Column(name = "reference_code")
	private String referenceCode;
	
	@Column(name = "date")
	private Date date;
	
	@Column(name = "sales_type")
	@Enumerated(EnumType.STRING)
	protected SalesType salesType = SalesType.STANDARD;
	
	@Column(name = "quantity")
	private BigDecimal quantity = BigDecimal.ZERO;
	
	@Column(name = "assigned")
	private BigDecimal assigned = BigDecimal.ZERO;
	
	@Column(name = "resend")
	private BigDecimal resend = BigDecimal.ZERO;
	
	@Column(name = "returned")
	private BigDecimal returned = BigDecimal.ZERO;
	
	@Column(name = "discount")
	private BigDecimal discount = BigDecimal.ZERO;

	@Column(name = "delivered")
	protected BigDecimal delivered = BigDecimal.ZERO;

	@Column(name = "accepted")
	protected BigDecimal accepted = BigDecimal.ZERO;

	@Column(name = "shrinkage")
	protected BigDecimal shrinkage = BigDecimal.ZERO;

	@Column(name = "note")
	private String note;
	
	@Column(name = "deliverable")
	@Type(type = "yes_no")
	private boolean deliverable = Boolean.TRUE;
	
	@Column(name = "approved")
	@Type(type = "yes_no")
	private boolean approved = Boolean.FALSE;
	
	@Embedded
	private Money money;
	
	@Embedded
	private Lot lot;
	
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_party_organization")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Party organization;
	
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_party_customer")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Party customer;
	
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_party_approver")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Party approver;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_facility")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Facility facility;
	
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_shipping_address")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private PostalAddress shippingAddress;
	
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_product")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Product product;
	
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_tax")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Tax tax;
	
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_tax_ext")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Tax extTax1;

	@OneToOne(mappedBy = "salesReferenceItem", fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private DeliveryOrderItem deliveryOrderItem;
}
