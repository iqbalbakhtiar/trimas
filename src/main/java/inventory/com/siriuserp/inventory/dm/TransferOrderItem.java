package com.siriuserp.inventory.dm;

import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.ExchangeType;
import com.siriuserp.sdk.dm.Grid;
import com.siriuserp.sdk.dm.Money;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author ferdinand
 */

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "transfer_order_item")
public class TransferOrderItem extends WarehouseReferenceItem implements Reservable
{
    private static final long serialVersionUID = -5998057666694226888L;

    @Column(name = "quantity")
    private BigDecimal quantity = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_transfer_order")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private TransferOrder transferOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_container_source")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Container sourceContainer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_container_destination")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Container destinationContainer;

    @Override
    public Money getMoney()
    {
        Money money = new Money();
        money.setCurrency(Currency.newInstance("1"));
        money.setExchangeType(ExchangeType.MIDDLE);

        return money;
    }

    @Override
    public Grid getGrid() {
        return null;
    }

    @Override
    public Container getSourceContainer()
    {
        return sourceContainer;
    }

    @Override
    public Container getDestinationContainer()
    {
        return destinationContainer;
    }

    @Override
    public Grid getSourceGrid()
    {
        return getSourceContainer().getGrid();
    }

    @Override
    public Grid getDestinationGrid()
    {
        return getDestinationContainer().getGrid();
    }

    @Override
    public Long getReferenceId()
    {
        return getTransferOrder().getId();
    }

    @Override
    public String getReferenceCode()
    {
        return getTransferOrder().getCode();
    }

    @Override
    public String getRefFrom()
    {
        return getTransferOrder().getSource().getName();
    }

    @Override
    public String getRefTo()
    {
        return getTransferOrder().getDestination().getName();
    }

    @Override
    public WarehouseTransaction getWarehouseTransaction() {
        return getTransferOrder();
    }

    @Override
    public WarehouseTransactionSource getTransactionSource() {
        return WarehouseTransactionSource.TRANSFER_ORDER;
    }
}
