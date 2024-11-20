package com.siriuserp.sdk.dm;

import java.math.BigDecimal;

public interface ReferenceItem {
    public Long getId();
    public BigDecimal getQuantity();
    public BigDecimal getQuantityBase();
    public Long getReferenceId();
    public BigDecimal getTaxRate();
}
