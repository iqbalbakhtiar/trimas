package com.siriuserp.sales.dm;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.siriuserp.inventory.dm.Reservable;
import com.siriuserp.inventory.dm.WarehouseReferenceItem;
import com.siriuserp.inventory.dm.WarehouseTransaction;
import com.siriuserp.inventory.dm.WarehouseTransactionSource;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Grid;
import com.siriuserp.sdk.dm.Money;
import com.siriuserp.sdk.dm.Tag;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "delivery_order_realization_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DeliveryOrderRealizationItem extends WarehouseReferenceItem implements Reservable, Comparable<DeliveryOrderRealizationItem>
{
    private static final long serialVersionUID = 3807540226776848698L;

    @Column(name = "accepted")
    private BigDecimal accepted = BigDecimal.ZERO;

	@Column(name = "returned")
	private BigDecimal returned = BigDecimal.ZERO;

    @Column(name = "shrinkage")
    private BigDecimal shrinkage = BigDecimal.ZERO;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="fk_delivery_order_realization")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private DeliveryOrderRealization deliveryOrderRealization;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="fk_delivery_order_item")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private DeliveryOrderItem deliveryOrderItem;

    @OneToOne(mappedBy="realizationItem",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	private DeliveryOrderRealizationReserveBridge reserveBridge;
    
    @Override
	public Long getReferenceId() {
		return getDeliveryOrderRealization().getId();
	}
    
    @Override
	public Tag getOriginTag() {
		return Tag.stock();
	}
    
    @Override
	public Money getMoney() {
		Money money = new Money();
		money.setAmount(getDeliveryOrderItem().getSalesReferenceItem().getMoney().getAmount());
		money.setCurrency(getDeliveryOrderItem().getSalesReferenceItem().getMoney().getCurrency());
		money.setExchangeType(getDeliveryOrderItem().getSalesReferenceItem().getMoney().getExchangeType());
		money.setRate(getDeliveryOrderItem().getSalesReferenceItem().getMoney().getRate());
		
		return money;
	}

	@Override
	public WarehouseTransaction getWarehouseTransaction() {
		return this.getDeliveryOrderRealization();
	}

	@Override
	public WarehouseTransactionSource getTransactionSource() {
		return WarehouseTransactionSource.DELIVERY_ORDER_REALIZATION;
	}

    @Override
    public int compareTo(DeliveryOrderRealizationItem item) {
        return getAccepted().compareTo(item.getAccepted());
    }

    @Override
    public String getRefTo() {
        return this.getDeliveryOrderRealization().getCustomer().getFullName();
    }

    @Override
    public Container getSourceContainer() {
        return this.getDeliveryOrderItem().getContainer();
    }

	@Override
	public Grid getGrid() {
		return this.getSourceGrid();
	}
	
	@Override
	public Container getContainer() {
		return this.getSourceContainer();
	}
	
	@Override
	public BigDecimal getQuantity() {
		 if (getShrinkage().compareTo(BigDecimal.ZERO) > 0)
			 return getShrinkage();
		 
		 if (getAccepted().compareTo(BigDecimal.ZERO) > 0)
			 return getAccepted();
		 
		 return BigDecimal.ZERO;
	}
}
