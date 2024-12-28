/**
 * Dec 11, 2008 3:17:32 PM
 * com.siriuserp.reporting.accounting.adapter
 * GLRegisterReportAdapter.java
 */
package com.siriuserp.reporting.accounting.adapter;

import java.math.BigDecimal;
import java.util.Map;

import com.siriuserp.accounting.dm.GLAccount;
import com.siriuserp.accounting.dm.GLPostingType;
import com.siriuserp.accounting.dm.JournalEntryDetail;
import com.siriuserp.sdk.dm.JSONSupport;
import com.siriuserp.sdk.utility.DecimalHelper;

import javolution.util.FastList;
import javolution.util.FastMap;

/**
 * @author Muhammad Rizal
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public class GLRegisterReportAdapter extends GLAccountProfitLossAdapter implements JSONSupport
{
    private static final long serialVersionUID = -303449237365251458L;
    
    private BigDecimal openingdebet = BigDecimal.ZERO;
    private BigDecimal openingcredit = BigDecimal.ZERO;
    private BigDecimal closingdebet = BigDecimal.ZERO;
    private BigDecimal closingcredit = BigDecimal.ZERO;

    private FastList<JournalEntryDetail> entrys = new FastList<JournalEntryDetail>();
    
    public GLRegisterReportAdapter(){}
    
    public GLRegisterReportAdapter(GLAccount account,
    		BigDecimal openingdebet, BigDecimal openingcredit)
    {
        setAccount(account);

        setOpeningdebet(openingdebet);
        setOpeningcredit(openingcredit);
    }
    
    public GLRegisterReportAdapter(GLAccount account,
    		BigDecimal openingdebet, BigDecimal openingcredit, 
    		BigDecimal closingdebet, BigDecimal closingcredit)
    {
        setAccount(account);

        setOpeningdebet(openingdebet);
        setOpeningcredit(openingcredit);
        setClosingdebet(closingdebet);
        setClosingcredit(closingcredit);
    }
    
    public GLRegisterReportAdapter(GLAccount account, BigDecimal amount,
    		BigDecimal openingdebet, BigDecimal openingcredit, 
    		BigDecimal closingdebet, BigDecimal closingcredit)
    {
        setAccount(account);
        setAmount(amount);

        setOpeningdebet(openingdebet);
        setOpeningcredit(openingcredit);
        
        setClosingdebet(closingdebet);
        setClosingcredit(closingcredit);
    }
    
    public GLRegisterReportAdapter(GLAccount account)
    {
        setAccount(account);
    }
    
    public GLRegisterReportAdapter(GLAccount account,BigDecimal amount,GLPostingType type)
    {
        setAccount(account);

        switch (type)
        {
            case DEBET:
                setDebet(debet);
                break;
            case CREDIT:
                setCredit(credit);
                break;
        }
    }

	public BigDecimal getOpeningdebet()
    {
        return openingdebet;
    }

    public void setOpeningdebet(BigDecimal openingdebet)
    {
        this.openingdebet = openingdebet;
    }

    public BigDecimal getOpeningcredit()
    {
        return openingcredit;
    }

    public void setOpeningcredit(BigDecimal openingcredit)
    {
        this.openingcredit = openingcredit;
    }

    public BigDecimal getClosingdebet()
    {
        return closingdebet;
    }

    public void setClosingdebet(BigDecimal closingdebet)
    {
        this.closingdebet = closingdebet;
    }

    public BigDecimal getClosingcredit()
    {
        return closingcredit;
    }

    public void setClosingcredit(BigDecimal closingcredit)
    {
        this.closingcredit = closingcredit;
    }

    public FastList<JournalEntryDetail> getEntrys()
    {
        return entrys;
    }

    public void setEntrys(FastList<JournalEntryDetail> entrys)
    {
        this.entrys = entrys;
    }

    public BigDecimal totalDebet()
    {
        BigDecimal decimal = BigDecimal.valueOf(0);
        
        for(JournalEntryDetail detail:entrys)
        {
            if(detail.getPostingType().equals(GLPostingType.DEBET))
                decimal = decimal.add(DecimalHelper.safe(detail.getAmount()));
        }
        
        return decimal;
    }
    
    public BigDecimal totalCredit()
    {
        BigDecimal decimal = BigDecimal.valueOf(0);

        for(JournalEntryDetail detail:entrys)
        {
            if(detail.getPostingType().equals(GLPostingType.CREDIT))
                decimal = decimal.add(DecimalHelper.safe(detail.getAmount()));
        }
        
        return decimal;
    }
    
    public BigDecimal getReduce()
    {
    	return getAmount().add(getOpeningdebet()).add(getClosingdebet()).subtract(getOpeningcredit()).subtract(getClosingcredit());
    }

	@Override
	public Map<String, Object> val() 
	{
		FastMap<String, Object> response = new FastMap<String, Object>();
		
		response.put("id", getAccount().getId());
		response.put("account", getAccount().getCode()+" - "+getAccount().getName());
		
		response.put("balance",getOpeningdebet().subtract(getOpeningcredit()));
		response.put("debet", getOpeningdebet());
		response.put("credit", getOpeningcredit());

		response.put("details", getEntrys());
		
		return response;
	}
}
