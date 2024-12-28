/**
 * Dec 2, 2008 9:55:01 AM
 * com.siriuserp.reporting.accounting.dto
 * GeneralJournalReport.java
 */
package com.siriuserp.reporting.accounting.adapter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import com.siriuserp.accounting.adapter.AbstractAccountingReportAdapter;
import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.accounting.dm.GLAccount;
import com.siriuserp.accounting.dm.GLPostingType;
import com.siriuserp.accounting.dm.JournalEntryDetail;
import com.siriuserp.accounting.dm.JournalEntryType;
import com.siriuserp.sdk.dm.AccessType;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastList;
import javolution.util.FastMap;
import javolution.util.FastSet;

/**
 * @author Muhammad Rizal
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class ComplexAccountingReportAdapter extends AbstractAccountingReportAdapter
{
    private static final long serialVersionUID = 2364425132440949084L;

    private boolean correction;

    private boolean entry;

    private boolean adjustment;

    private Date dateFrom;

    private Date dateTo;

    private GLAccount account;

    private Long accountType;

    private GLPostingType postingType;

    private AccountingPeriod accountingPeriod;

    private FastList<Long> periods = new FastList<Long>();
    
    private FastList<Long> prevPeriods = new FastList<Long>();

    private Set<IndexTypeReportAdapter> indexes = new FastSet<IndexTypeReportAdapter>();

    private FastList<JournalEntryType> types = new FastList<JournalEntryType>();

    private FastList<ProfitLossReportAdapter> assets = new FastList<ProfitLossReportAdapter>();

    private FastList<ProfitLossReportAdapter> liabilities = new FastList<ProfitLossReportAdapter>();

    private FastList<ProfitLossReportAdapter> equitys = new FastList<ProfitLossReportAdapter>();

    private FastList<ProfitLossReportAdapter> revenues = new FastList<ProfitLossReportAdapter>();

    private FastList<ProfitLossReportAdapter> cogs = new FastList<ProfitLossReportAdapter>();

    private FastList<ProfitLossReportAdapter> expenses = new FastList<ProfitLossReportAdapter>();

    private FastList<ProfitLossReportAdapter> otherexpenses = new FastList<ProfitLossReportAdapter>();

    private FastList<ProfitLossReportAdapter> otherrevenues = new FastList<ProfitLossReportAdapter>();

    private BigDecimal grand = BigDecimal.ZERO;
    
    private BigDecimal prevGrand = BigDecimal.ZERO;

    public ComplexAccountingReportAdapter(){}
    
    public ComplexAccountingReportAdapter(JournalEntryDetail journalEntryDetail)
    {
        if(journalEntryDetail != null)
        {
            this.account = journalEntryDetail.getAccount();

            if(journalEntryDetail.getPostingType() != null && journalEntryDetail.getAmount() != null)
            {
                switch(journalEntryDetail.getPostingType())
                {
                    case DEBET:
                        this.debet = journalEntryDetail.getAmount();
                        break;
                    case CREDIT:
                        this.credit = journalEntryDetail.getAmount();
                }
            }
        }
    }

    public ComplexAccountingReportAdapter(GLAccount account,BigDecimal amount,GLPostingType postingType)
    {
        this.account = account;

        if(postingType != null && amount != null)
        {
            switch(postingType)
            {
                case DEBET:
                    this.debet = amount;
                    break;
                case CREDIT:
                    this.credit = amount;
            }
        }
    }

    public FastList<ProfitLossReportAdapter> getRevenues()
    {
        return revenues;
    }

    public void setRevenues(FastList<ProfitLossReportAdapter> revenues)
    {
        this.revenues = revenues;
    }

    public FastList<ProfitLossReportAdapter> getCogs()
    {
        return cogs;
    }

    public void setCogs(FastList<ProfitLossReportAdapter> cogs)
    {
        this.cogs = cogs;
    }

    public FastList<ProfitLossReportAdapter> getExpenses()
    {
        return expenses;
    }

    public void setExpenses(FastList<ProfitLossReportAdapter> expenses)
    {
        this.expenses = expenses;
    }

    public FastList<ProfitLossReportAdapter> getOtherexpenses()
    {
        return otherexpenses;
    }

    public void setOtherexpenses(FastList<ProfitLossReportAdapter> otherexpenses)
    {
        this.otherexpenses = otherexpenses;
    }

    public FastList<ProfitLossReportAdapter> getOtherrevenues()
    {
        return otherrevenues;
    }

    public void setOtherrevenues(FastList<ProfitLossReportAdapter> otherrevenues)
    {
        this.otherrevenues = otherrevenues;
    }
    
    public FastList<ProfitLossReportAdapter> getAssets()
    {
        return assets;
    }

    public void setAssets(FastList<ProfitLossReportAdapter> assets)
    {
        this.assets = assets;
    }

    public FastList<ProfitLossReportAdapter> getLiabilities()
    {
        return liabilities;
    }

    public void setLiabilities(FastList<ProfitLossReportAdapter> liabilities)
    {
        this.liabilities = liabilities;
    }

    public FastList<ProfitLossReportAdapter> getEquitys()
    {
        return equitys;
    }

    public void setEquitys(FastList<ProfitLossReportAdapter> equitys)
    {
        this.equitys = equitys;
    }
    
	public FastList<Long> getPeriods()
    {
        return periods;
    }

    public void setPeriods(FastList<Long> periods)
    {
        this.periods = periods;
    }

	public FastList<Long> getPrevPeriods() 
	{
		return prevPeriods;
	}

	public void setPrevPeriods(FastList<Long> prevPeriods) 
	{
		this.prevPeriods = prevPeriods;
	}

	public FastList<JournalEntryType> getTypes()
    {
        if(isAdjustment())
            types.add(JournalEntryType.ADJUSTMENT);

        if(isCorrection())
            types.add(JournalEntryType.CORRECTION);

        if(isEntyry())
            types.add(JournalEntryType.ENTRY);

        return types;
    }

    public void setTypes(FastList<JournalEntryType> types)
    {
        this.types = types;
    }

    public boolean isCorrection()
    {
        return correction;
    }

    public void setCorrection(boolean correction)
    {
        this.correction = correction;
    }

    public boolean isEntyry()
    {
        return entry;
    }

    public void setEntry(boolean entry)
    {
        this.entry = entry;
    }

    public boolean isAdjustment()
    {
        return adjustment;
    }

    public void setAdjustment(boolean adjustment)
    {
        this.adjustment = adjustment;
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

    public Long getAccountType()
    {
        return accountType;
    }

    public void setAccountType(Long accountType)
    {
        this.accountType = accountType;
    }

    public GLAccount getAccount()
    {
        return account;
    }

    public void setAccount(GLAccount account)
    {
        this.account = account;
    }

    public Set<IndexTypeReportAdapter> getIndexes()
    {
        return indexes;
    }

    public void setIndexes(Set<IndexTypeReportAdapter> indexes)
    {
        this.indexes = indexes;
    }

    public boolean isEntry()
    {
        return entry;
    }

    public GLPostingType getPostingType()
    {
        return postingType;
    }

    public void setPostingType(GLPostingType postingType)
    {
        this.postingType = postingType;
    }

    public AccountingPeriod getAccountingPeriod()
    {
        return accountingPeriod;
    }

    public void setAccountingPeriod(AccountingPeriod accountingPeriod)
    {
        this.accountingPeriod = accountingPeriod;
    }

	public Set<IndexTypeReportAdapter> getAppliedIndex()
    {
        Set<IndexTypeReportAdapter> sets = new FastSet<IndexTypeReportAdapter>();

        for(IndexTypeReportAdapter adapter:this.indexes)
        {
            if(adapter.isUsed() && SiriusValidator.validateParam(adapter.getContent()))
                sets.add(adapter);
        }

        return sets;
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

    public BigDecimal getTotalRevenue()
    {
        BigDecimal decimal = BigDecimal.ZERO;

        for(ProfitLossReportAdapter adapter:revenues)
            decimal = decimal.add(adapter.getAmount());

        return decimal;
    }

    public BigDecimal getTotalCogs()
    {
        BigDecimal decimal = BigDecimal.ZERO;

        for(ProfitLossReportAdapter adapter:cogs)
            decimal = decimal.add(adapter.getAmount());

        return decimal;
    }

    public BigDecimal getTotalExpense()
    {
        BigDecimal decimal = BigDecimal.ZERO;

        for(ProfitLossReportAdapter adapter:expenses)
            decimal = decimal.add(adapter.getAmount());

        return decimal;
    }

    public BigDecimal getTotalOtherExpense()
    {
        BigDecimal decimal = BigDecimal.ZERO;

        for(ProfitLossReportAdapter adapter:otherexpenses)
            decimal = decimal.add(adapter.getAmount());

        return decimal;
    }

    public BigDecimal getTotalOtherRevenue()
    {
        BigDecimal decimal = BigDecimal.ZERO;

        for(ProfitLossReportAdapter adapter:otherrevenues)
            decimal = decimal.add(adapter.getAmount());

        return decimal;
    }
    
    public BigDecimal getIncome()
    {
        return getTotalRevenue().add(getTotalCogs()).add(getTotalOtherExpense()).add(getTotalExpense()).add(getTotalOtherRevenue());
    }
    
    public BigDecimal getGrand() 
    {
		return grand;
	}

	public void setGrand(BigDecimal grand) 
	{
		this.grand = grand;
	}
	
	public BigDecimal getPrevGrand()
	{
		return prevGrand;
	}

	public void setPrevGrand(BigDecimal prevGrand) 
	{
		this.prevGrand = prevGrand;
	}
	
	public FastList<FastMap<String, Object>> getGrouped()
    {
    	FastList<FastMap<String, Object>> list = new FastList<FastMap<String,Object>>();
    	
    	if(!getAssets().isEmpty())
    		list.addAll(getGrouped("Assets", getAssets(), false));
    	
    	if(!getLiabilities().isEmpty())
    		list.addAll(getGrouped("Liabilities", getLiabilities(), true));
    	
    	if(!getEquitys().isEmpty())
    		list.addAll(getGrouped("Equities", getEquitys(), true));
    		
    	if(!getRevenues().isEmpty())
    		list.addAll(getGrouped("Revenues", getRevenues(), false));
    	
    	if(!getCogs().isEmpty())
    		list.addAll(getGrouped("COGS", getCogs(), false));
    	
    	if(!getRevenues().isEmpty() && !getCogs().isEmpty())
    	{
    		FastMap<String, Object> gp =  new FastMap<String, Object>();
    		gp.put("foot", "Gross Profit ");
    		gp.put("total", getGrand());
    		gp.put("prevTotal", getPrevGrand());
    	
        	list.add(gp);
    	}
    	
    	BigDecimal otherGrand = getGrand();
    	BigDecimal otherPrevGrand = getPrevGrand();
        
    	if(!getExpenses().isEmpty())
    		list.addAll(getGrouped("Expenses", getExpenses(), false));
    	
    	if(!getOtherrevenues().isEmpty())
    		list.addAll(getGrouped("Other Revenues", getOtherrevenues(), false));
    	
    	if(!getOtherexpenses().isEmpty())
    		list.addAll(getGrouped("Other Expenses", getOtherexpenses(), false));
    	
    	if(!getExpenses().isEmpty() && !getOtherrevenues().isEmpty() && !getOtherexpenses().isEmpty())
    	{
	    	FastMap<String, Object> other =  new FastMap<String, Object>();
	    	other.put("foot", "Total Expenses / Other Revenues");
	    	other.put("total", getGrand().subtract(otherGrand));
	    	other.put("prevTotal", getPrevGrand().subtract(otherPrevGrand));
	    	
	        list.add(other);
    	}
    	
    	return list;
    }
	
    private FastList<FastMap<String, Object>> getGrouped(String caption, FastList<ProfitLossReportAdapter> adapters, boolean balance)
    {
    	FastList<FastMap<String, Object>> list = new FastList<FastMap<String, Object>>(); 	
        FastMap<String, FastMap<String, Object>> map = new FastMap<String, FastMap<String, Object>>();
        
        FastMap<String, Object> head =  new FastMap<String, Object>();
        head.put("head", caption);
        
        list.add(head);
        
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal prevTotal = BigDecimal.ZERO;
        for(ProfitLossReportAdapter adapter : adapters)
        {
        	if(adapter.getDebet().compareTo(BigDecimal.ZERO) != 0 || adapter.getCredit().compareTo(BigDecimal.ZERO) != 0)
        	{
        		groupping(list, map, adapter.getAccount(), 
        				balance ? adapter.getDebet().negate() : adapter.getDebet(), 
        				balance ? adapter.getCredit().negate() : adapter.getCredit());
        	
        		total = total.add(adapter.getDebet());
        		prevTotal = prevTotal.add(adapter.getCredit());
        	}
        	
        	if(adapter.getAmount().compareTo(BigDecimal.ZERO) != 0)
        	{
        		groupping(list, map, adapter.getAccount(), 
        				balance ? adapter.getAmount().negate() : adapter.getAmount(), 
        						BigDecimal.ZERO);
        		
        		total = total.add(adapter.getAmount());
        	}
        }
        
        list.addAll(map.values());
        
        FastMap<String, Object> foot =  new FastMap<String, Object>();
        foot.put("foot", "Total "+caption);
        foot.put("total", total);
        foot.put("prevTotal", prevTotal);
 
        list.add(foot);
        
        setGrand(getGrand().add(total));
        setPrevGrand(getPrevGrand().add(prevTotal));

        return list;
    }
    
    public void groupping(FastList<FastMap<String, Object>> list, FastMap<String, FastMap<String, Object>> map, GLAccount key, 
			BigDecimal amount, BigDecimal prevAmount)
	{
    	groupping(list, map, key, amount, prevAmount, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, 
    			BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }
    
    public void groupping(FastList<FastMap<String, Object>> list, FastMap<String, FastMap<String, Object>> map, GLAccount key, 
    		BigDecimal january, BigDecimal febuary, BigDecimal march, BigDecimal april,BigDecimal may, BigDecimal june, 
    		BigDecimal july, BigDecimal august, BigDecimal september, BigDecimal october, BigDecimal november, BigDecimal december)
	{
    	groupping(list, map, key, BigDecimal.ZERO, BigDecimal.ZERO, january, febuary, march, april, may, june,
    			july, august, september, october, november, december);
    }
    
    @SuppressWarnings("unchecked")
	public void groupping(FastList<FastMap<String, Object>> list, FastMap<String, FastMap<String, Object>> map, GLAccount key, 
			BigDecimal amount, BigDecimal prevAmount, BigDecimal january, BigDecimal febuary, BigDecimal march, BigDecimal april,
			BigDecimal may, BigDecimal june, BigDecimal july, BigDecimal august, BigDecimal september, BigDecimal october, 
			BigDecimal november, BigDecimal december)
	{ 

    	if(map.containsKey(key.getParent().getParent().getCode()))
	    {
	    	FastMap<String, Object> in = (FastMap<String, Object>)map.get(key.getParent().getParent().getCode());
	    	BigDecimal sum = (BigDecimal)in.get("sum");
	    	sum = sum.add(amount);
	    		
	    	BigDecimal prevSum = (BigDecimal)in.get("prevSum");
	    	prevSum = prevSum.add(prevAmount);
		        	
	    	FastMap<String, FastMap<String, Object>> _map = (FastMap<String, FastMap<String, Object>>)in.get("parents");
			if(_map.containsKey(key.getParent().getCode()))
			{
				FastMap<String, Object> parent = (FastMap<String, Object>)_map.get(key.getParent().getCode());
				FastMap<String, FastMap<String, Object>> _nap = (FastMap<String, FastMap<String, Object>>)parent.get("accounts");
				
				BigDecimal summary = (BigDecimal)parent.get("sum");
				summary = summary.add(amount);
		    		
		    	BigDecimal prevSummary = (BigDecimal)parent.get("prevSum");
		    	prevSummary = prevSummary.add(amount);
				
			    FastMap<String, Object> account = new FastMap<String, Object>();
			    account.put("account", key);
			    trial(account, amount, prevAmount, january, febuary, march, april, may, june, july, august, september, october, november, december);
			        
			    _nap.put(key.getCode(), account);
			
				parent.put("accounts", _nap);
				parent.put("sum", summary);
			    parent.put("prevSum", prevSummary);
				
				_map.put(key.getParent().getCode(), parent);
			}
			else
			{	
			    FastMap<String, Object> account = new FastMap<String, Object>();
			    account.put("account", key);
			    trial(account, amount, prevAmount, january, febuary, march, april, may, june, july, august, september, october, november, december);
			        
			    FastMap<String, FastMap<String, Object>> _nap = new FastMap<String, FastMap<String, Object>>();
		    	_nap.put(key.getCode(), account);

				FastMap<String, Object> parent = new FastMap<String, Object>();
		        parent.put("parent", key.getParent());
		        parent.put("accounts", _nap);
		        parent.put("sum", amount);
			    parent.put("prevSum", prevAmount);
			    
		        _map.put(key.getParent().getCode(), parent);
			}
	            
			in.put("sum", sum);
			in.put("prevSum", prevSum);
			in.put("parents", _map);
	    	
	    	map.put(key.getParent().getParent().getCode(), in);
	    }
	    else
	    {
	    	FastMap<String, Object> account = new FastMap<String, Object>();
	        account.put("account", key);
	        trial(account, amount, prevAmount, january, febuary, march, april, may, june, july, august, september, october, november, december);
	        
	    	FastMap<String, FastMap<String, Object>> _nap = new FastMap<String, FastMap<String, Object>>();
	    	_nap.put(key.getCode(), account);
	    	
	        FastMap<String, Object> parent = new FastMap<String, Object>();
	        parent.put("parent", key.getParent());
	        parent.put("accounts", _nap);
	        parent.put("sum", amount);
	        parent.put("prevSum", prevAmount);

	    	FastMap<String, FastMap<String, Object>> _map = new FastMap<String, FastMap<String, Object>>();
	    	_map.put(key.getParent().getCode(), parent);

	       	FastMap<String, Object> in = new FastMap<String, Object>();
	       	in.put("parental", key.getParent().getParent());
	       	in.put("sum", amount);
	       	in.put("prevSum", prevAmount);
	       	in.put("parents", _map);
	        	
	        map.put(key.getParent().getParent().getCode(), in);
	    }
	}
    
    public void trial(FastMap<String, Object> map, BigDecimal amount, BigDecimal prevAmount, 
    		BigDecimal january, BigDecimal febuary, BigDecimal march, BigDecimal april,BigDecimal may, BigDecimal june, 
    		BigDecimal july, BigDecimal august, BigDecimal september, BigDecimal october, BigDecimal november, BigDecimal december)
    {
    	map.put("amount", amount);
    	map.put("prevAmount", prevAmount);
    	
    	map.put("january", january);
    	map.put("febuary", febuary);
    	map.put("march", march);
    	map.put("april", april);
    	map.put("may", may);
    	map.put("june", june);
    	
    	map.put("july", july);
    	map.put("august", august);
    	map.put("september", september);
    	map.put("october", october);
    	map.put("november", november);
    	map.put("december", december);
    	
    	BigDecimal total1 = BigDecimal.ZERO;
    	
    	total1 = total1.add(january).add(febuary).add(march).add(april).add(may).add(june).add(july).add(august).add(september).add(october).add(november).add(december);
    	
    	map.put("total1", total1);
    }
    
    public 	FastList<Long> getJanPeriods() { return null; }; 
    public  FastList<Long> getFebPeriods() { return null; };
    public  FastList<Long> getMarPeriods() { return null; }; 
    public  FastList<Long> getAprPeriods() { return null; }; 
    public  FastList<Long> getMayPeriods() { return null; }; 
	public  FastList<Long> getJunPeriods() { return null; }; 
    public  FastList<Long> getJulPeriods() { return null; }; 
	public  FastList<Long> getAugPeriods() { return null; };
	public  FastList<Long> getSepPeriods() { return null; }; 
	public  FastList<Long> getOctPeriods() { return null; };
	public  FastList<Long> getNovPeriods() { return null; }; 
	public  FastList<Long> getDecPeriods() { return null; };

}
