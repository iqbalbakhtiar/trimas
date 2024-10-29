/**
 * 
 */
package com.siriuserp.inventory.dm;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Party;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ferdinand
 */

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "product_in_out_transaction")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductInOutTransaction extends Model implements ProductTransaction
{
	private static final long serialVersionUID = 3775471906445410043L;

	@Column(name = "date")
    private Date date;
	
	@Column(name = "serial_no", length = 100, unique = true)
	private String serial;
	
	@Column(name = "code")
	private String code;

	@Column(name = "info")
	private String info;
	
    @Column(name = "quantity")
    private BigDecimal quantity = BigDecimal.ZERO;

    @Column(name = "receipted")
    private BigDecimal receipted = BigDecimal.ZERO;
    
    @Column(name = "rate")
	private BigDecimal rate = BigDecimal.ONE;
    
    @Column(name = "price")
    private BigDecimal price = BigDecimal.ZERO;
    
    @Column(name = "discount")
    private BigDecimal discount = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_currency")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Currency currency;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_product")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Product product;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_party_organization")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Party organization;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_container")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Container container;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_origin_item")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private WarehouseTransactionItem originItem;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_controllable")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Controllable controllable;
	
	@Override
	public String getAuditCode() {
		return this.id + "";
	}
}
