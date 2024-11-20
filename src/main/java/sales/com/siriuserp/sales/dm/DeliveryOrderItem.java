package com.siriuserp.sales.dm;

import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "delivery_order_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DeliveryOrderItem extends Model {

    private static final long serialVersionUID = -4841672668391969021L;

    @Column(name = "note")
    private String note;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="fk_delivery_order")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private DeliveryOrder deliveryOrder;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="fk_container")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Container container;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_sales_reference", referencedColumnName = "id")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private SalesReferenceItem salesReferenceItem;

    @OneToOne(mappedBy = "deliveryOrderItem", fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private DeliveryOrderRealizationItem deliveryOrderRealizationItem;

    @Override
    public String getAuditCode() {
        return id + "," + salesReferenceItem.getReferenceCode();
    }
}
