package com.siriuserp.sdk.dm;

import org.apache.commons.lang.WordUtils;

public enum PaymentMethodType {
	CASH, TRANSFER, CLEARING;

	public String getCapitalizedName()
	{
		if (this.equals(TRANSFER))
			return "Bank";

		return WordUtils.capitalize(this.toString().toLowerCase());
	}

	public String getMessage()
	{
		return "payment." + this.toString().toLowerCase().replace("_", "");
	}
}
