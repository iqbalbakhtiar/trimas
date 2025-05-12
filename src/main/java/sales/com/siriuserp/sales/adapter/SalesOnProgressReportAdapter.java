/**
 * File Name  : SalesOnProgressReportAdapter.java
 * Created On : May 10, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.adapter;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.siriuserp.sales.dm.SalesOrderItem;
import com.siriuserp.sdk.adapter.AbstractUIAdapter;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Getter
@Setter
public class SalesOnProgressReportAdapter extends AbstractUIAdapter
{
	private static final long serialVersionUID = -4378508826387149429L;

	private BigDecimal standardBale = BigDecimal.valueOf(181.44);
	private SalesOrderItem salesOrderItem;

	public SalesOnProgressReportAdapter(SalesOrderItem salesOrderItem)
	{
		this.salesOrderItem = salesOrderItem;
	}

	public BigDecimal getKg()
	{
		return getSalesOrderItem().getQuantity();
	}

	public BigDecimal getBale()
	{
		return getKg().divide(getStandardBale(), 2, RoundingMode.HALF_UP);
	}

	public BigDecimal getUnassignedKg()
	{
		return getKg().subtract(getSalesOrderItem().getAssigned());
	}

	public BigDecimal getUnassignedBale()
	{
		return getUnassignedKg().divide(getStandardBale(), 2, RoundingMode.HALF_UP);
	}

	public BigDecimal getDeliveredKg()
	{
		return getSalesOrderItem().getDelivered();
	}

	public BigDecimal getDeliveredBale()
	{
		return getDeliveredKg().divide(getStandardBale(), 2, RoundingMode.HALF_UP);
	}

	public BigDecimal getUndeliveredKg()
	{
		return getKg().subtract(getDeliveredKg());
	}

	public BigDecimal getUndeliveredBale()
	{
		return getUndeliveredKg().divide(getStandardBale(), 2, RoundingMode.HALF_UP);
	}
}
