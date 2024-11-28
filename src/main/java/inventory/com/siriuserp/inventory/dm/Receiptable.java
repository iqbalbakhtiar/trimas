package com.siriuserp.inventory.dm;

import java.util.Set;

public interface Receiptable extends WarehouseTransaction {
    public Set<? extends WarehouseReferenceItem> getReceiptables();

    public Set<GoodsReceipt> getReceipts();
}
