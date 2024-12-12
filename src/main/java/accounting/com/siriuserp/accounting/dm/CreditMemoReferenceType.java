package com.siriuserp.accounting.dm;

public enum CreditMemoReferenceType {
    DISCOUNT("NONE"),
    RETURN("REF");

    private String type;

    private CreditMemoReferenceType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getMessage()
    {
        return this.toString().replace("_", "").toLowerCase();
    }

    public String getNormalizedName()
    {
        if (this.toString().equals("DISCOUNT"))
            return "SALES DISCOUNT";

        return this.toString().replace("_", " ");
    }
}
