/**
 * 
 */
package com.siriuserp.inventory.dm;

import java.math.BigDecimal;

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

import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Lot;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Money;

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
@Table(name = "stock_control")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StockControl extends Model implements LotComparable
{	
	private static final long serialVersionUID = -5503340638890866083L;

	@Column(name = "serial_no", length = 100, unique = true)
	private String serial;
	
	@Column(name = "code")
	private String code;

	@Column(name = "info")
	private String info;
	
    @Column(name = "quantity")
    private BigDecimal quantity = BigDecimal.ZERO;
    
    @Column(name = "buffer")
    private BigDecimal buffer = BigDecimal.ZERO;
    
    @Column(name = "price")
    private BigDecimal price = BigDecimal.ZERO;
    
    @Column(name = "rate")
	private BigDecimal rate = BigDecimal.ONE;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_currency")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Currency currency;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_destination_item")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private WarehouseTransactionItem destinationItem;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_source_item")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private WarehouseTransactionItem sourceItem;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_controllable")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Controllable controllable;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_stockable")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Stockable stockable;
	
    @Override
	public Lot getLot() 
	{
		Lot lot = new Lot();
		lot.setCode(getCode());
		lot.setSerial(getSerial());
		lot.setInfo(getInfo());
		
		return lot;
	}
    
    public Money getMoney()
	{
		Money money = new Money();
		money.setCurrency(getCurrency());
		money.setAmount(getPrice());
		money.setRate(BigDecimal.ONE);
		
		return money;
	}
    
	@Override
	public String getAuditCode() {
		return getId() +" "+ getCode();
	}
}
