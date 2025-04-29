/**
 * File Name  : PurchaseDocumentType.java
 * Created On : Apr 29, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.procurement.dm;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public enum PurchaseDocumentType
{
	SPAREPART, PACKING, SARANA, PERAWATAN, BAHAN_BAKU;

	public String getNormalizedName()
	{
		return this.toString().replace("_", " ");
	}

	public String getMessageName()
	{
		return this.toString().replace("_", "").toLowerCase();
	}

	public String getCode()
	{
		switch (this)
		{
		case SPAREPART:
			return "SPART";
		case PACKING:
			return "PACKING";
		case SARANA:
			return "SARANA";
		case PERAWATAN:
			return "PERAWATAN";
		case BAHAN_BAKU:
			return "BAHANBAKU";
		default:
			return null;
		}
	}
}
