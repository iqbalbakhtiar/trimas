package com.siriuserp.inventory.dm;

import java.util.Set;

public interface Issueable extends WarehouseTransaction {

    public Set<? extends WarehouseReferenceItem> getIssueables();

    public Set<GoodsIssue> getIssueds();
}
