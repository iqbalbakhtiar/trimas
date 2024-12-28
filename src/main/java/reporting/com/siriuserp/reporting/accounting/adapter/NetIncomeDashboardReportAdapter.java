package com.siriuserp.reporting.accounting.adapter;

import java.math.BigDecimal;

import com.siriuserp.sdk.adapter.AbstractUIAdapter;
import com.siriuserp.sdk.dm.Month;

public class NetIncomeDashboardReportAdapter extends AbstractUIAdapter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8856216310645877676L;
	
	
	private BigDecimal amount;
	private Month month;
	
	public NetIncomeDashboardReportAdapter(BigDecimal amount, Month month) {
		super();
		this.amount = amount;
		this.month = month;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Month getMonth() {
		return month;
	}
	public void setMonth(Month month) {
		this.month = month;
	}
	
	
}
