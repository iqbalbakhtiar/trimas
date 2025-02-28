package com.siriuserp.sales.dm;

import com.siriuserp.inventory.dm.Product;

import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.JSONSupport;
import com.siriuserp.sdk.dm.Lot;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Money;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PostalAddress;
import com.siriuserp.sdk.dm.Tax;
import javolution.util.FastMap;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "sales_reference_item")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class SalesReferenceItem extends Model implements JSONSupport {

	private static final long serialVersionUID = 4479431094475781734L;

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

	@Column(name = "term")
	private Integer term = 1;

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

    /* Many-to-One Relationship */
	
	@ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="fk_party_organization")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Party organization;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_party_customer")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Party customer;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_party_approver")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Party approver;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_facility")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Facility facility;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_shipping_address")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private PostalAddress shippingAddress;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_product")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Product product;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_tax")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Tax tax;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_tax_ext")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Tax extTax1;

	@OneToOne(mappedBy = "salesReferenceItem", fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private DeliveryOrderItem deliveryOrderItem;
	
    public BigDecimal getDiscountedPricePerItem() {
        return money.getAmount()
                .subtract(
                        money.getAmount().multiply(discount).divide(new BigDecimal(100))
                );
    }
    
    public BigDecimal getTotalAmountPerItemDiscounted() {
        return getDiscountedPricePerItem().multiply(quantity);
    }
    
    public Long getReferenceId()
	{
		return null;
	}
	
	@Override
	public Map<String, Object> val()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("price", getMoney().getAmount());
		return map;
	}
}
