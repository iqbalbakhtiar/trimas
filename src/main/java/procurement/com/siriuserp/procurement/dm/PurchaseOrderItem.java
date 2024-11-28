package com.siriuserp.procurement.dm;

import com.siriuserp.inventory.dm.WarehouseReferenceItem;
import com.siriuserp.inventory.dm.WarehouseTransaction;
import com.siriuserp.inventory.dm.WarehouseTransactionSource;
import com.siriuserp.sdk.dm.JSONSupport;
import com.siriuserp.sdk.dm.Money;
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
@Table(name = "purchase_order_item")
public class PurchaseOrderItem extends WarehouseReferenceItem implements JSONSupport {
    private static final long serialVersionUID = 6323790446769057283L;

    @Column(name = "delivery_date")
    private Date deliveryDate;

    @Column(name = "quantity")
    private BigDecimal quantity = BigDecimal.ZERO;

    @Column(name = "discount")
    private BigDecimal discount = BigDecimal.ZERO;

    @Column(name = "locked")
    @Type(type = "yes_no")
    private boolean locked = Boolean.FALSE;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_purchase_order")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private PurchaseOrder purchaseOrder;

    @Override
    public Money getMoney() {
        return super.getMoney();
    }

    @Override
    public WarehouseTransaction getWarehouseTransaction() {
        return getPurchaseOrder();
    }

    @Override
    public WarehouseTransactionSource getTransactionSource() {
        return WarehouseTransactionSource.DIRECT_PURCHASE_ORDER;
    }

    // (Qty * Amount)
    public BigDecimal getTotalAmount() {
        return this.quantity.multiply(this.getMoney().getAmount());
    }

    @Override
    public String getRefFrom()
    {
        return getPurchaseOrder().getSupplier().getFullName();
    }

    @Override
    public Date getDeliveryDate() {
        return this.deliveryDate;
    }
}
