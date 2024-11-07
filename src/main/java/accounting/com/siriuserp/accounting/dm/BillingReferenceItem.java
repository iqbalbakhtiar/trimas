package com.siriuserp.accounting.dm;

import com.siriuserp.inventory.dm.Product;
import com.siriuserp.sdk.dm.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "billing_reference_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BillingReferenceItem extends Model {

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

    // Sudah ada fk_product, mungkin field ini tidak diperlukan lagi
    // Konteks: Reference terbuat dari DOR
    @Column(name = "reference_uom")
    private String referenceUom;

    @Column(name = "reference_date")
    private Date referenceDate;

    @Column(name = "quantity")
    private BigDecimal quantity = BigDecimal.ZERO;

    @Embedded
    private Money money; // Exchange Type not used

    @Column(name = "discount")
    private BigDecimal discount = BigDecimal.ZERO;

    @Column(name = "reference_uri")
    private String referenceUri;

    @Column(name = "billed")
    @Type(type = "yes_no")
    private boolean billed = Boolean.FALSE;

    @Column(name = "paid")
    @Type(type = "yes_no")
    private boolean paid = Boolean.FALSE;

    @Column(name = "reference_type")
    private BillingReferenceType referenceType;

    @ManyToOne(fetch=FetchType.LAZY)
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

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_tax")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Tax tax;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_product")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Product product;

    // Used In Billing Preedit
    /* (Qty * Amount) */
    public BigDecimal getSubtotal() {
        BigDecimal amount = (money != null && money.getAmount() != null) ? money.getAmount() : BigDecimal.ZERO;
        BigDecimal qty = (quantity != null) ? quantity : BigDecimal.ZERO;
        return amount.multiply(qty);
    }

    /* Method to calculate the discount amount from subtotal and discount percentage */
    public BigDecimal getDiscountAmount() {
        BigDecimal subtotal = getSubtotal();
        BigDecimal discountPercentage = (discount != null) ? discount : BigDecimal.ZERO;

        return subtotal.multiply(discountPercentage).divide(new BigDecimal(100));
    }

    /* Method to calculate the total amount after discount */
    public BigDecimal getTotalAmount() {
        BigDecimal subtotal = getSubtotal();
        BigDecimal discountAmount = getDiscountAmount();

        /* TotalAmount = Subtotal - Discount Amount */
        return subtotal.subtract(discountAmount);
    }

    /**
     * Method to calculate the discounted price per item (Price - Discount).
     * Used in Billing Print Out
     */
    public BigDecimal getDiscountedPricePerItem() {
        return money.getAmount()
                .subtract(
                        money.getAmount().multiply(discount).divide(new BigDecimal(100))
                );
    }

    /**
     * Method to calculate the total amount after discount on a per-item basis
     * i.e., (Price - Discount) * Qty.
     * Used in Billing Print Out
     */
    public BigDecimal getTotalAmountPerItemDiscounted() {
        return getDiscountedPricePerItem().multiply(quantity);
    }

    @Override
    public String getAuditCode() {
        return id + "," + referenceCode;
    }
}
