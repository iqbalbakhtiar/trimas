/**
 * Jan 21, 2009 9:53:17 AM
 * com.siriuserp.reporting.accounting.adapter
 * TrialBalanceReportAdapter.java
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
public class TrialBalanceReportAdapter extends AbstractAccountingReportAdapter
{
    private static final long serialVersionUID = -3600064604309448638L;

    private BigDecimal opening = BigDecimal.valueOf(0);

    public TrialBalanceReportAdapter() {}
    
    public TrialBalanceReportAdapter(GLAccount account, BigDecimal opening,
		BigDecimal debet, BigDecimal credit)
	{	
    	this.account = account;
    	this.opening = opening;
    	this.debet = debet;
    	this.credit = credit;
	}
    
    public TrialBalanceReportAdapter(GLAccount account, BigDecimal opening,
    		BigDecimal debet, BigDecimal credit, 
    		BigDecimal adjustmentdebet, BigDecimal adjustmentcredit) 
    {
    	this.account = account;
    	this.opening = opening;
    	this.debet = debet;
    	this.credit = credit;
    	this.adjustmentdebet = adjustmentdebet;
    	this.adjustmentcredit = adjustmentcredit;
    }
    
    public BigDecimal getOpening()
    {
        return opening;
    }

    public void setOpening(BigDecimal opening)
    {
        this.opening = opening;
    }
    
    public BigDecimal getClosing()
    {
    	return getOpening().add(getDebet()).add(getAdjustmentdebet()).subtract(getCredit()).subtract(getAdjustmentcredit());
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
