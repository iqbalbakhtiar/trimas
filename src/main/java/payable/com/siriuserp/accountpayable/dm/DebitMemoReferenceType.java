package com.siriuserp.accountpayable.dm;

public enum DebitMemoReferenceType 
{
    PURCHASE_DISCOUNT("NONE"),
	PURCHASE_RETURN("NONE"),
	PURCHASE_RETURN_FROM_BARCODE("REFERENCE"),
	ADJUSTMENT("NONE");
	
	private String type;
	
	private DebitMemoReferenceType(String type)
	{
		this.type = type;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getNormalizedName()
	{				
		return this.toString().replace("_", " ");
	}
}
