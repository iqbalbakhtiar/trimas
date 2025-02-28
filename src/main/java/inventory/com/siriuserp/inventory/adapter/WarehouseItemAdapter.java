package com.siriuserp.inventory.adapter;

import com.siriuserp.inventory.dm.GoodsIssue;
import com.siriuserp.inventory.dm.GoodsIssueItem;
import com.siriuserp.inventory.dm.GoodsReceipt;
import com.siriuserp.inventory.dm.GoodsReceiptItem;
import com.siriuserp.inventory.dm.Product;
import com.siriuserp.inventory.dm.WarehouseTransactionItem;
import com.siriuserp.inventory.dm.WarehouseTransactionSource;
import com.siriuserp.sdk.adapter.AbstractUIAdapter;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Grid;
import com.siriuserp.sdk.dm.Lot;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.Reference;
import com.siriuserp.sdk.dm.Tax;
import javolution.util.FastList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class WarehouseItemAdapter extends AbstractUIAdapter {
    private static final long serialVersionUID = 3425194757070932635L;

    private boolean enabled = false;
    private Lot lot;

    private Party organization;
    private Facility facility;
    private Grid grid;
    private Party party;
    private Currency currency;
    private Tax tax;
    private Reference reference;
    private WarehouseTransactionSource source;

    private Product product;
    private WarehouseTransactionItem item;

    private GoodsReceipt goodsReceipt;
    private GoodsReceiptItem receiptItem;

    private GoodsIssue goodsIssue;
    private GoodsIssueItem issueItem;

    private BigDecimal receipted = BigDecimal.ZERO;
    private BigDecimal issued = BigDecimal.ZERO;

    private BigDecimal amount = BigDecimal.ZERO;
    private BigDecimal available = BigDecimal.ZERO;

    private BigDecimal quantity = BigDecimal.ZERO;

    private FastList<GoodsReceiptItem> receiptItems = new FastList<GoodsReceiptItem>();
    private FastList<GoodsIssueItem> issueItems = new FastList<GoodsIssueItem>();

    private FastList<WarehouseItemAdapter> adapters = new FastList<WarehouseItemAdapter>();

    public WarehouseItemAdapter(WarehouseTransactionItem item)
    {
        this(BigDecimal.ZERO, null, item);
    }

    public WarehouseItemAdapter(GoodsIssueItem issueItem)
    {
        this(BigDecimal.ZERO, issueItem, null);
    }

    public WarehouseItemAdapter(BigDecimal issued, GoodsIssueItem issueItem, WarehouseTransactionItem item)
    {
        this.issued = issued;
        this.item = item;
        this.issueItem = issueItem;
    }

    public WarehouseItemAdapter(GoodsReceipt goodsReceipt, GoodsIssue goodsIssue)
    {
        this(null, null, goodsReceipt, goodsIssue);
    }

    public WarehouseItemAdapter(Lot lot, GoodsIssue goodsIssue)
    {
        this(lot, null, goodsIssue);
    }

    public WarehouseItemAdapter(Lot lot, GoodsReceipt goodsReceipt)
    {
        this(lot, goodsReceipt, null);
    }

    public WarehouseItemAdapter(Lot lot, GoodsReceipt goodsReceipt, GoodsIssue goodsIssue)
    {
        this(lot, null, goodsReceipt, goodsIssue);
    }

    public WarehouseItemAdapter(Lot lot, Grid grid, GoodsReceipt goodsReceipt, GoodsIssue goodsIssue)
    {
        this.lot = lot;
        this.grid = grid;
        this.goodsReceipt = goodsReceipt;
        this.goodsIssue = goodsIssue;
    }

    public WarehouseItemAdapter(Lot lot, GoodsIssueItem issueItem, WarehouseTransactionItem item)
    {
        this.lot = lot;
        this.item = item;
        this.issueItem = issueItem;
    }

    public WarehouseItemAdapter(Lot lot, Facility facility, Grid grid,  GoodsIssue goodsIssue)
    {
        this.lot = lot;
        this.facility = facility;
        this.grid = grid;
        this.goodsIssue = goodsIssue;
    }

    public WarehouseItemAdapter(Facility facility, BigDecimal receipted, BigDecimal issued, BigDecimal amount, BigDecimal available)
    {
        this(facility, receipted, issued, amount, available, BigDecimal.ZERO);
    }

    public WarehouseItemAdapter(Facility facility, BigDecimal receipted, BigDecimal issued, BigDecimal amount, BigDecimal available, BigDecimal quantity)
    {
        this.facility = facility;
        this.issued = issued;
        this.receipted = receipted;
        this.amount = amount;
        this.available = available;
        this.quantity = quantity;
    }

    public List<WarehouseItemAdapter> getEnableAdapters()
    {
        return getAdapters().stream().filter(item -> item.isEnabled()).collect(Collectors.toList());
    }
}
