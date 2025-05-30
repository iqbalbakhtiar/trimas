package com.siriuserp.accountpayable.dm;

public enum InvoiceVerificationType
{
	STANDARD, MANUAL;

	public String getUri()
	{
		switch (this)
		{
		case STANDARD:
			return "invoiceverificationpreedit.htm";
		case MANUAL:
			return "manualinvoiceverificationpreedit.htm";
		default:
			return "";
		}
	}
}
