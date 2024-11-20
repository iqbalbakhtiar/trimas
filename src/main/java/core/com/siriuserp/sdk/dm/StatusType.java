package com.siriuserp.sdk.dm;

import org.apache.commons.lang.WordUtils;

public enum StatusType {
    STANDARD, COMPLETION, SEQUENCE;

    public String getNormalizedName()
    {
        return this.toString().replace("_", " ");
    }

    public String getCapitalizedName()
    {
        return WordUtils.capitalize(getNormalizedName().toLowerCase());
    }
}
