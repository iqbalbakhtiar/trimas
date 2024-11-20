package com.siriuserp.inventory.dm;

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
@Table(name = "goods_issue_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GoodsIssueItem extends Stockable implements Inventoriable, ReferenceItem {
    private static final long serialVersionUID = -3202333517961853945L;

    @Column(name = "issued")
    private BigDecimal issued = BigDecimal.ZERO;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="fk_goods_issue")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private GoodsIssue goodsIssue;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_grid")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Grid grid;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_container")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Container container;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_warehouse_transaction_item")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private WarehouseTransactionItem transactionItem;

    @Override
    public WarehouseTransactionItem getWarehouseTransactionItem() {
        return this.transactionItem;
    }

    @Override
    public Container getSourceContainer() {
        return this.container;
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
    public Grid getDestinationGrid() {
        return getWarehouseTransactionItem().getDestinationGrid();
    }

    @Override
    public Lot getLot() {
        return getWarehouseTransactionItem().getLot();
    }

    @Override
    public Tag getTag() {
        return getWarehouseTransactionItem().getTag();
    }

    @Override
    public ProductCategory getProductCategory() {
        return getProduct().getProductCategory();
    }

    @Override
    public Date getDate() {
        return getGoodsIssue().getDate();
    }

    @Override
    public Product getProduct() {
        return getWarehouseTransactionItem().getProduct();
    }

    @Override
    public BigDecimal getQuantity() {
        return getIssued();
    }

    // Qty to Base on product not present
    @Override
    public BigDecimal getQuantityBase() {
        return null;
//        return getQuantity().multiply(getProduct().getQtyToBase());
    }

    @Override
    public Long getReferenceId() {
        return getWarehouseTransactionItem().getReferenceItem().getId();
    }

    @Override
    public BigDecimal getTaxRate() {
        return getWarehouseTransactionItem().getReferenceItem().getTax().getTaxRate();
    }

    @Override
    public Party getOrganization() {
        return getGoodsIssue().getOrganization();
    }

    @Override
    public String getAuditCode() {
        return "";
    }
}
