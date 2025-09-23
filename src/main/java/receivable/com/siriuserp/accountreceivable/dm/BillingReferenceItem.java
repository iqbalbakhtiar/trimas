/**
 * File Name  : BillingReferenceItem.java
 * Created On : Jul 23, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountreceivable.dm;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import com.siriuserp.inventory.dm.Product;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Money;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.Tax;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "billing_reference_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BillingReferenceItem extends Model
{
	private static final long serialVersionUID = 5221439395225775460L;

	@Column(name = "reference_id")
	private Long referenceId;

	@Column(name = "reference_id_ext")
	private Long referenceIdExt;

	@Column(name = "reference_code")
	private String referenceCode;

	@Column(name = "reference_code_ext")
	private String referenceCodeExt;

	@Column(name = "reference_name")
	private String referenceName;

	@Column(name = "reference_name_ext")
	private String referenceNameExt;

	@Column(name = "reference_date")
	private Date referenceDate;

	@Column(name = "quantity")
	private BigDecimal quantity = BigDecimal.ZERO;

	@Embedded
	private Money money = new Money();

	@Column(name = "discount")
	private BigDecimal discount = BigDecimal.ZERO;

	@Column(name = "discount_percent")
	private BigDecimal discountPercent = BigDecimal.ZERO;

	@Column(name = "reference_uri")
	private String referenceUri;

	@Column(name = "term")
	private Integer term = 1;

	@Column(name = "billed")
	@Type(type = "yes_no")
	private boolean billed = Boolean.FALSE;

	@Column(name = "paid")
	@Type(type = "yes_no")
	private boolean paid = Boolean.FALSE;

	@Column(name = "reference_type")
	private BillingReferenceType referenceType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_party_organization")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party organization;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_facility")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Facility facility;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_party_customer")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party customer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_tax")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Tax tax;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_product")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Product product;

	// Used In Billing Preedit
	public BigDecimal getSubtotal()
	{
		return money.getAmount().multiply(quantity);
	}

	public BigDecimal getTotalDiscount()
	{
		return discount.multiply(quantity);
	}

	public BigDecimal getTotalAfterDiscount()
	{
		return getSubtotal().subtract(getTotalDiscount());
	}

	/**
	 * Method to calculate the discounted price per item (Price - Discount).
	 * Used in Billing Print Out
	 */
	public BigDecimal getDiscountedPricePerItem()
	{
		return money.getAmount().subtract(discount);
	}

	/**
	 * Method to calculate the total amount after discount on a per-item basis
	 * i.e., (Price - Discount) * Qty.
	 * Used in Billing Print Out
	 */
	public BigDecimal getTotalAmountPerItemDiscounted()
	{
		return getDiscountedPricePerItem().multiply(quantity);
	}

	@Override
	public String getAuditCode()
	{
		return id + "," + referenceCode;
	}
	
	public BigDecimal getTotalWithTax() {
		
	    BigDecimal base = (money == null || money.getAmount() == null ? BigDecimal.ZERO : money.getAmount())
	                      .subtract(discount == null ? BigDecimal.ZERO : discount);

	    if (tax == null || tax.getTaxRate() == null) 
	        return base.setScale(2, RoundingMode.HALF_UP);

	    return base.multiply(
	                BigDecimal.ONE.add(tax.getTaxRate().divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP))
	           ).setScale(2, RoundingMode.HALF_UP);
	}

}
