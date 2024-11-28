package com.siriuserp.sdk.dm;

public enum Reference {
    REFERENCE,ITEM;

    public String getMessage()
    {
        return this.toString().toLowerCase();
    }
}
