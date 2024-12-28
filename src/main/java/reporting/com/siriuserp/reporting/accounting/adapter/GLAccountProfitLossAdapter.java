/**
 * Apr 19, 2011
 * Sirius Indonesia, PT
 * www.siriuserp.com
 * GLAccountProfitLossAdapter.java
 */
package com.siriuserp.reporting.accounting.adapter;

import java.math.BigDecimal;

import com.siriuserp.accounting.dm.GLAccount;

/**
 * @author Iqbal Sirius Indonesia, 
 * PT www.siriuserp.com
 */

public class GLAccountProfitLossAdapter extends ProfitLossReportAdapter
{
	private static final long serialVersionUID = -2927597659371636567L;

	private BigDecimal january = BigDecimal.ZERO;
	private BigDecimal febuary = BigDecimal.ZERO;
	private BigDecimal march = BigDecimal.ZERO;
	private BigDecimal april = BigDecimal.ZERO;
	private BigDecimal may = BigDecimal.ZERO;
	private BigDecimal june = BigDecimal.ZERO;
	private BigDecimal july = BigDecimal.ZERO;
	private BigDecimal august = BigDecimal.ZERO;
	private BigDecimal september = BigDecimal.ZERO;
	private BigDecimal october = BigDecimal.ZERO;
	private BigDecimal november = BigDecimal.ZERO;
	private BigDecimal december = BigDecimal.ZERO;

	public GLAccountProfitLossAdapter() {}

	public GLAccountProfitLossAdapter(GLAccount account, 
			BigDecimal jan,BigDecimal feb,
			BigDecimal mar,BigDecimal apr,
			BigDecimal may,BigDecimal jun,
			BigDecimal jul,BigDecimal aug,
			BigDecimal sep,BigDecimal oct,
			BigDecimal nov,BigDecimal dec)
	{
		setAccount(account);
		setJanuary(jan);
		setFebuary(feb);
		setMarch(mar);
		setApril(apr);
		setMay(may);
		setJune(jun);
		setJuly(jul);
		setAugust(aug);
		setSeptember(sep);
		setOctober(oct);
		setNovember(nov);
		setDecember(dec);
	}

	public BigDecimal getJanuary() 
	{
		return january;
	}

	public void setJanuary(BigDecimal january) 
	{
		this.january = january;
	}

	public BigDecimal getFebuary()
	{
		return febuary;
	}

	public void setFebuary(BigDecimal febuary) 
	{
		this.febuary = febuary;
	}

	public BigDecimal getMarch() 
	{
		return march;
	}

	public void setMarch(BigDecimal march)
	{
		this.march = march;
	}

	public BigDecimal getApril() 
	{
		return april;
	}

	public void setApril(BigDecimal april)
	{
		this.april = april;
	}

	public BigDecimal getMay() 
	{
		return may;
	}

	public void setMay(BigDecimal may) 
	{
		this.may = may;
	}

	public BigDecimal getJune()
	{
		return june;
	}

	public void setJune(BigDecimal june)
	{
		this.june = june;
	}

	public BigDecimal getJuly() 
	{
		return july;
	}

	public void setJuly(BigDecimal july)
	{
		this.july = july;
	}

	public BigDecimal getAugust() 
	{
		return august;
	}

	public void setAugust(BigDecimal august)
	{
		this.august = august;
	}

	public BigDecimal getSeptember() 
	{
		return september;
	}

	public void setSeptember(BigDecimal september) 
	{
		this.september = september;
	}

	public BigDecimal getOctober() 
	{
		return october;
	}

	public void setOctober(BigDecimal october) 
	{
		this.october = october;
	}

	public BigDecimal getNovember() 
	{
		return november;
	}

	public void setNovember(BigDecimal november)
	{
		this.november = november;
	}

	public BigDecimal getDecember() 
	{
		return december;
	}

	public void setDecember(BigDecimal december) 
	{
		this.december = december;
	}
}
