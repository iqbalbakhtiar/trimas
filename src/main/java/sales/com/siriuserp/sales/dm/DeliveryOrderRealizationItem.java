package com.siriuserp.sales.dm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

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
public class DeliveryOrderRealizationItem extends WarehouseReferenceItem {

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
}
