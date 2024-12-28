/**
 * Dec 12, 2008 9:29:43 AM
 * com.siriuserp.reporting.accounting.adapter
 * ProfitLossReportAdapter.java
 */
package com.siriuserp.reporting.accounting.adapter;

import java.math.BigDecimal;

import com.siriuserp.accounting.adapter.AbstractAccountingReportAdapter;
import com.siriuserp.accounting.dm.GLAccount;
import com.siriuserp.sdk.dm.AccessType;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class ProfitLossReportAdapter extends AbstractAccountingReportAdapter
{
	private static final long serialVersionUID = 4178032276124897924L;
	
	private BigDecimal amount = BigDecimal.ZERO;
	
    public ProfitLossReportAdapter(){}
    
    public ProfitLossReportAdapter(GLAccount account, BigDecimal debet, BigDecimal credit)
    {
    	 setAccount(account);
         setDebet(debet);
         setCredit(credit);
    }
    
    public ProfitLossReportAdapter(GLAccount account, BigDecimal amount)
    {
    	 setAccount(account);
         setAmount(amount);
    }
    
	public BigDecimal getAmount() 
	{
		return amount;
	}

	public void setAmount(BigDecimal amount) 
	{
		this.amount = amount;
	}

	@Override
    public AccessType getAccessType()
    {
        return null;
    }

    @Override
    public void setAccessType(AccessType accessType)
    {
    }
}
