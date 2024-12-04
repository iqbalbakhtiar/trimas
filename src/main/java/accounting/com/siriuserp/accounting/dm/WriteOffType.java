package com.siriuserp.accounting.dm;

public enum WriteOffType {
    ADJUSTMENT,BANKCHARGE,UNDERTABLE;

    public String getNormalizedName()
    {
        return this.toString().replace("_", " ");
    }
}
