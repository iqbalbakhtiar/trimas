/**
 * Mar 11, 2010 10:36:04 AM
 * com.siriuserp.accountpayable.criteria
 * APLedgerFilterCriteria.java
 */
package com.siriuserp.accountpayable.criteria;

import java.util.Date;

import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.filter.AbstractReportFilterCriteria;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 * Version 1.5
 */
public class APLedgerFilterCriteria extends AbstractReportFilterCriteria
{
	private static final long serialVersionUID = 4021524969907924976L;

	private Date dateFrom;
	private Date dateTo;
	private Party supplier;

	public APLedgerFilterCriteria()
	{
	}

	public Date getDateFrom()
	{
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom)
	{
		this.dateFrom = dateFrom;
	}

	public Date getDateTo()
	{
		return dateTo;
	}

	public void setDateTo(Date dateTo)
	{
		this.dateTo = dateTo;
	}

	public Party getSupplier()
	{
		return supplier;
	}

	public void setSupplier(Party supplier)
	{
		this.supplier = supplier;
	}
}
