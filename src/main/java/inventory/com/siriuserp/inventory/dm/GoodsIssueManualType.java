package com.siriuserp.inventory.dm;

import org.apache.commons.lang.WordUtils;

public enum GoodsIssueManualType {
    SALES, SERVICE, STANDARD;

    public String getNormalizedName()
    {
        return this.toString().replace("_", " ");
    }

    public String getCapitalizedName()
    {
        return WordUtils.capitalize(getNormalizedName().toLowerCase());
    }

}
