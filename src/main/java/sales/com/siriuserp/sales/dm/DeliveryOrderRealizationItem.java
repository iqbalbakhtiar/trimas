package com.siriuserp.sales.dm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import com.siriuserp.inventory.dm.WarehouseReferenceItem;
import com.siriuserp.inventory.dm.WarehouseTransaction;
import com.siriuserp.inventory.dm.WarehouseTransactionSource;
import com.siriuserp.sdk.dm.Money;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "delivery_order_realization_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DeliveryOrderRealizationItem extends WarehouseReferenceItem
{
    private static final long serialVersionUID = 3807540226776848698L;

    @Column(name = "accepted")
    private BigDecimal accepted = BigDecimal.ZERO;

    @Column(name = "shrinkage")
    private BigDecimal shrinkage = BigDecimal.ZERO;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="fk_delivery_order_realization")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private DeliveryOrderRealization deliveryOrderRealization;

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="fk_delivery_order_item")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private DeliveryOrderItem deliveryOrderItem;

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
		return null;
	}

	@Override
	public WarehouseTransactionSource getTransactionSource() {
		return WarehouseTransactionSource.DELIVERY_ORDER_REALIZATION;
	}
}
