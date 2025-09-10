/**
 * File Name  : LotInfoUtil.java
 * Created On : Sep 2, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sdk.utility;

import com.siriuserp.sdk.dm.Lot;
import com.siriuserp.sdk.dm.LotInfoable;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public class LotInfoUtil
{
	public static Lot initLot(Lot lot, LotInfoable infoable)
	{
		//Lot Info :Info | No Container | Barcode/Serial Source | Date
		StringBuilder info = new StringBuilder();
		if (lot.getInfo() != null)
			info.append(lot.getInfo() + "|");
		else
			info.append("-|");

		if (infoable.getContainerNo() != null)
			info.append(infoable.getContainerNo() + "|");
		else
			info.append("-|");

		if (infoable.getSerialSource() != null)
			info.append(infoable.getSerialSource() + "|");
		else
			info.append("-|");

		if (infoable.getDate() != null)
			info.append(DateHelper.format(infoable.getDate()) + "|");
		else
			info.append("-");

		lot.setInfo(info.toString());

		return lot;
	}
}
