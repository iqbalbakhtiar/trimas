package com.siriuserp.inventory.dm;

import com.siriuserp.sdk.dm.*;
import com.siriuserp.sdk.utility.DecimalHelper;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * Merepresentasikan item dalam goods receipt.
 * Berisi informasi tentang kuantitas yang diterima, produk,
 * dan transaksi warehouse terkait.
 */
@Getter
@Setter
@Entity
@Table(name = "goods_receipt_item")
public class GoodsReceiptItem extends Controllable implements ReferenceItem, JSONSupport {
    private static final long serialVersionUID = 3926488454351390346L;

    @Column(name = "receipted")
    private BigDecimal receipted = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_goods_receipt")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private GoodsReceipt goodsReceipt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_grid")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Grid grid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_container")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Container container;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_warehouse_transaction_item")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private WarehouseTransactionItem warehouseTransactionItem;

    @Override
    public BigDecimal getUnitPrice() {
        BigDecimal price = getInOuts().stream().map((ProductInOutTransaction inout) -> inout.getReceipted().multiply(inout.getPrice())).collect(DecimalHelper.sum());;

        return price.divide(getQuantity(), 23, RoundingMode.HALF_UP);
    }

    @Override
    public Date getDate() {
        return getGoodsReceipt().getDate();
    }

    @Override
    public Product getProduct() {
        return getWarehouseTransactionItem().getProduct();
    }

    @Override
    public BigDecimal getQuantity() {
        return getReceipted();
    }

    @Override
    public BigDecimal getQuantityBase() {
//        return getQuantity().multiply(getProduct().getQtyToBase());
        return BigDecimal.ONE;
    }

    @Override
    public Long getReferenceId() {
        return getWarehouseTransactionItem().getReferenceItem().getId();
    }

    @Override
    public BigDecimal getTaxRate() {
        return null;
    }

    @Override
    public Party getOrganization() {
        return getGoodsReceipt().getOrganization();
    }

    @Override
    public Container getContainer() {
        return this.container;
    }

    @Override
    public Grid getGrid() {
        return this.grid;
    }

    @Override
    public Container getSourceContainer() {
        return getWarehouseTransactionItem().getSourceContainer();
    }

    @Override
    public Grid getSourceGrid() {
        return getWarehouseTransactionItem().getSourceGrid();
    }

    @Override
    public Container getDestinationContainer() {
        return getWarehouseTransactionItem().getDestinationContainer();
    }

    @Override
    public Grid getDestinationGrid()
    {
        return getWarehouseTransactionItem().getDestinationGrid();
    }

    @Override
    public Lot getLot()
    {
//        if(getWarehouseTransactionItem().getTransactionSource().isCleanLot())
//            return new Lot();
//
//        if(getWarehouseTransactionItem().getTransactionSource().isExtLot())
//            return getWarehouseTransactionItem().getExtLot();

        return getWarehouseTransactionItem().getLot();
    }

    @Override
    public Tag getTag() {
//        if(getWarehouseTransactionItem().getTransactionSource().isAutoTag())
//            return Tag.stock(InventoryReference.TRANSFER_ORDER);

        return getWarehouseTransactionItem().getTag();
    }

    @Override
    public Lot getOriginLot()
    {
        return getWarehouseTransactionItem().getLot();
    }

    @Override
    public String getAuditCode() {
        return "";
    }
}
