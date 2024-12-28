package com.siriuserp.reporting.accounting.adapter;

import java.math.BigDecimal;

import com.siriuserp.sdk.dm.AccessType;

import javolution.util.FastList;
import javolution.util.FastMap;

/**
 * @author Betsu Brahmana Restu
 * Sirius Indonesia, PT
 * betsu@siriuserp.com
 * 
 * Version 1.5
 */
public class ComplexConsecutiveReportAdapter extends ComplexAccountingReportAdapter
{
	private static final long serialVersionUID = -1368841124250749983L;
	
	private FastList<Long> janPeriods = new FastList<Long>();
	    
	private FastList<Long> febPeriods = new FastList<Long>();
	    
	private FastList<Long> marPeriods = new FastList<Long>();
	    
	private FastList<Long> aprPeriods = new FastList<Long>();
	    
	private FastList<Long> mayPeriods = new FastList<Long>();
	    
	private FastList<Long> junPeriods = new FastList<Long>();
	    
	private FastList<Long> julPeriods = new FastList<Long>();
	    
	private FastList<Long> augPeriods = new FastList<Long>();
	    
	private FastList<Long> sepPeriods = new FastList<Long>();
	    
	private FastList<Long> octPeriods = new FastList<Long>();
	    
	private FastList<Long> novPeriods = new FastList<Long>();
	    
	private FastList<Long> decPeriods = new FastList<Long>();
	
	private BigDecimal grandJan = BigDecimal.ZERO;
	private BigDecimal grandFeb = BigDecimal.ZERO;
	private BigDecimal grandMar = BigDecimal.ZERO;
	private BigDecimal grandApr = BigDecimal.ZERO;
	private BigDecimal grandMay = BigDecimal.ZERO;
	private BigDecimal grandJun = BigDecimal.ZERO;
	private BigDecimal grandJul = BigDecimal.ZERO;
	private BigDecimal grandAug = BigDecimal.ZERO;
	private BigDecimal grandSep = BigDecimal.ZERO;
	private BigDecimal grandOct = BigDecimal.ZERO;
	private BigDecimal grandNov = BigDecimal.ZERO;
	private BigDecimal grandDec = BigDecimal.ZERO;
	
	@Override
	public FastList<Long> getJanPeriods() 
	{
		return janPeriods;
	}

	public void setJanPeriods(FastList<Long> janPeriods)
	{
		this.janPeriods = janPeriods;
	}
	
	@Override
	public FastList<Long> getFebPeriods()
	{
		return febPeriods;
	}

	public void setFebPeriods(FastList<Long> febPeriods) 
	{
		this.febPeriods = febPeriods;
	}
	
	@Override
	public FastList<Long> getMarPeriods() 
	{
		return marPeriods;
	}

	public void setMarPeriods(FastList<Long> marPeriods) 
	{
		this.marPeriods = marPeriods;
	}
	
	@Override
	public FastList<Long> getAprPeriods() 
	{
		return aprPeriods;
	}

	public void setAprPeriods(FastList<Long> aprPeriods)
	{
		this.aprPeriods = aprPeriods;
	}
	
	@Override
	public FastList<Long> getMayPeriods() 
	{
		return mayPeriods;
	}

	public void setMayPeriods(FastList<Long> mayPeriods) 
	{
		this.mayPeriods = mayPeriods;
	}
	
	@Override
	public FastList<Long> getJunPeriods() 
	{
		return junPeriods;
	}

	public void setJunPeriods(FastList<Long> junPeriods) 
	{
		this.junPeriods = junPeriods;
	}
	
	@Override
	public FastList<Long> getJulPeriods() 
	{
		return julPeriods;
	}

	public void setJulPeriods(FastList<Long> julPeriods)
	{
		this.julPeriods = julPeriods;
	}
	
	@Override
	public FastList<Long> getAugPeriods()
	{
		return augPeriods;
	}

	public void setAugPeriods(FastList<Long> augPeriods) 
	{
		this.augPeriods = augPeriods;
	}

	@Override
	public FastList<Long> getSepPeriods() 
	{
		return sepPeriods;
	}

	public void setSepPeriods(FastList<Long> sepPeriods) 
	{
		this.sepPeriods = sepPeriods;
	}
	
	@Override
	public FastList<Long> getOctPeriods() 
	{
		return octPeriods;
	}

	public void setOctPeriods(FastList<Long> octPeriods) 
	{
		this.octPeriods = octPeriods;
	}

	@Override
	public FastList<Long> getNovPeriods() 
	{
		return novPeriods;
	}

	public void setNovPeriods(FastList<Long> novPeriods) 
	{
		this.novPeriods = novPeriods;
	}
	
	@Override
	public FastList<Long> getDecPeriods() 
	{
		return decPeriods;
	}

	public void setDecPeriods(FastList<Long> decPeriods)
	{
		this.decPeriods = decPeriods;
	}
	
	public BigDecimal getGrandJan() 
	{
		return grandJan;
	}

	public void setGrandJan(BigDecimal grandJan) 
	{
		this.grandJan = grandJan;
	}

	public BigDecimal getGrandFeb() 
	{
		return grandFeb;
	}

	public void setGrandFeb(BigDecimal grandFeb)
	{
		this.grandFeb = grandFeb;
	}

	public BigDecimal getGrandMar()
	{
		return grandMar;
	}

	public void setGrandMar(BigDecimal grandMar) 
	{
		this.grandMar = grandMar;
	}

	public BigDecimal getGrandApr() 
	{
		return grandApr;
	}

	public void setGrandApr(BigDecimal grandApr) 
	{
		this.grandApr = grandApr;
	}

	public BigDecimal getGrandMay() 
	{
		return grandMay;
	}

	public void setGrandMay(BigDecimal grandMay)
	{
		this.grandMay = grandMay;
	}

	public BigDecimal getGrandJun() 
	{
		return grandJun;
	}

	public void setGrandJun(BigDecimal grandJun) 
	{
		this.grandJun = grandJun;
	}

	public BigDecimal getGrandJul()
	{
		return grandJul;
	}

	public void setGrandJul(BigDecimal grandJul) 
	{
		this.grandJul = grandJul;
	}

	public BigDecimal getGrandAug() 
	{
		return grandAug;
	}

	public void setGrandAug(BigDecimal grandAug) 
	{
		this.grandAug = grandAug;
	}

	public BigDecimal getGrandSep() 
	{
		return grandSep;
	}

	public void setGrandSep(BigDecimal grandSep) 
	{
		this.grandSep = grandSep;
	}

	public BigDecimal getGrandOct() 
	{
		return grandOct;
	}

	public void setGrandOct(BigDecimal grandOct)
	{
		this.grandOct = grandOct;
	}

	public BigDecimal getGrandNov() 
	{
		return grandNov;
	}

	public void setGrandNov(BigDecimal grandNov)
	{
		this.grandNov = grandNov;
	}

	public BigDecimal getGrandDec() 
	{
		return grandDec;
	}

	public void setGrandDec(BigDecimal grandDec) 
	{
		this.grandDec = grandDec;
	}
	
	public FastList<FastMap<String, Object>> getConsecutiveGrouped()
    {
    	FastList<FastMap<String, Object>> list = new FastList<FastMap<String,Object>>();
    	
    	if(!getAssets().isEmpty())
    		list.addAll(getConsecutiveGrouped("Assets", getAssets(), false));
    	
    	if(!getLiabilities().isEmpty())
    		list.addAll(getConsecutiveGrouped("Liabilities", getLiabilities(), true));
    	
    	if(!getEquitys().isEmpty())
    		list.addAll(getConsecutiveGrouped("Equities", getEquitys(), true));
    	
    	if(!getRevenues().isEmpty())
    		list.addAll(getConsecutiveGrouped("Revenues", getRevenues(), false));

    	if(!getCogs().isEmpty())
    		list.addAll(getConsecutiveGrouped("COGS", getCogs(), false));
    	
    	if(!getCogs().isEmpty() && !getCogs().isEmpty())
    	{
	    	FastMap<String, Object> gp =  new FastMap<String, Object>();
	    	gp.put("foot", "Gross Profit ");
	    	gp.put("totalJan", getGrandJan());
	    	gp.put("totalFeb", getGrandFeb());
	    	gp.put("totalMar", getGrandMar());
	    	gp.put("totalApr", getGrandApr());
	    	gp.put("totalMay", getGrandMay());
	    	gp.put("totalJun", getGrandJun());
	    	
	    	gp.put("totalJul", getGrandJul());
	    	gp.put("totalAug", getGrandAug());
	    	gp.put("totalSep", getGrandSep());
	    	gp.put("totalOct", getGrandOct());
	    	gp.put("totalNov", getGrandNov());
	    	gp.put("totalDec", getGrandDec());
	    	
	        list.add(gp);
    	}
    	
    	BigDecimal otherGrandJan = getGrandJan();
    	BigDecimal otherGrandFeb = getGrandFeb();
    	BigDecimal otherGrandMar = getGrandMar();
    	BigDecimal otherGrandApr = getGrandApr();
    	BigDecimal otherGrandMay = getGrandMay();
    	BigDecimal otherGrandJun = getGrandJun();
    	
    	BigDecimal otherGrandJul = getGrandJul();
    	BigDecimal otherGrandAug = getGrandAug();
    	BigDecimal otherGrandSep = getGrandSep();
    	BigDecimal otherGrandOct = getGrandOct();
    	BigDecimal otherGrandNov = getGrandNov();
    	BigDecimal otherGrandDec = getGrandDec();
    	
    	if(!getExpenses().isEmpty())
    		list.addAll(getConsecutiveGrouped("Expenses", getExpenses(), false));
    	
    	if(!getOtherrevenues().isEmpty())
    		list.addAll(getConsecutiveGrouped("Other Revenues", getOtherrevenues(), false));
    	
    	if(!getOtherexpenses().isEmpty())
    		list.addAll(getConsecutiveGrouped("Other Expenses", getOtherexpenses(), false));
    	
    	if(!getExpenses().isEmpty() && !getOtherrevenues().isEmpty() && !getOtherexpenses().isEmpty())
    	{
	    	FastMap<String, Object> other =  new FastMap<String, Object>();
	    	other.put("foot", "Total Expenses / Other Revenues");
	    	other.put("totalJan", getGrandJan().subtract(otherGrandJan));
	    	other.put("totalFeb", getGrandFeb().subtract(otherGrandFeb));
	    	other.put("totalMar", getGrandMar().subtract(otherGrandMar));
	    	other.put("totalApr", getGrandApr().subtract(otherGrandApr));
	    	other.put("totalMay", getGrandMay().subtract(otherGrandMay));
	    	other.put("totalJun", getGrandJun().subtract(otherGrandJun));
	    	
	    	other.put("totalJul", getGrandJul().subtract(otherGrandJul));
	    	other.put("totalAug", getGrandAug().subtract(otherGrandAug));
	    	other.put("totalSep", getGrandSep().subtract(otherGrandSep));
	    	other.put("totalOct", getGrandOct().subtract(otherGrandOct));
	    	other.put("totalNov", getGrandNov().subtract(otherGrandNov));
	    	other.put("totalDec", getGrandDec().subtract(otherGrandDec));
	    	
	        list.add(other);
    	}
    	
    	return list;
    }
	
	@SuppressWarnings("unchecked")
	private FastList<FastMap<String, Object>> getConsecutiveGrouped(String caption, FastList<? extends ProfitLossReportAdapter> adapters, boolean balance)
    {
    	FastList<FastMap<String, Object>> list = new FastList<FastMap<String, Object>>(); 	
        FastMap<String, FastMap<String, Object>> map = new FastMap<String, FastMap<String, Object>>();
        
        FastMap<String, Object> head =  new FastMap<String, Object>();
        head.put("head", caption);
        
        list.add(head);
        
        BigDecimal totalJan = BigDecimal.ZERO;
    	BigDecimal totalFeb = BigDecimal.ZERO;
    	BigDecimal totalMar = BigDecimal.ZERO;
    	BigDecimal totalMay = BigDecimal.ZERO;
    	BigDecimal totalApr = BigDecimal.ZERO;
    	BigDecimal totalJun = BigDecimal.ZERO;
    	
    	BigDecimal totalJul = BigDecimal.ZERO;
    	BigDecimal totalAug = BigDecimal.ZERO;
    	BigDecimal totalSep = BigDecimal.ZERO;
    	BigDecimal totalOct = BigDecimal.ZERO;
    	BigDecimal totalNov = BigDecimal.ZERO;
    	BigDecimal totalDec = BigDecimal.ZERO;
    	
        for(GLAccountProfitLossAdapter adapter : (FastList<GLAccountProfitLossAdapter>)adapters)
        {
        	if( adapter.getJanuary().compareTo(BigDecimal.ZERO) != 0
        			|| adapter.getFebuary().compareTo(BigDecimal.ZERO) != 0
        			|| adapter.getMarch().compareTo(BigDecimal.ZERO) != 0
        			|| adapter.getApril().compareTo(BigDecimal.ZERO) != 0
        			|| adapter.getMay().compareTo(BigDecimal.ZERO) != 0
        			|| adapter.getJune().compareTo(BigDecimal.ZERO) != 0
        			|| adapter.getJuly().compareTo(BigDecimal.ZERO) != 0
        			|| adapter.getAugust().compareTo(BigDecimal.ZERO) != 0
        			|| adapter.getSeptember().compareTo(BigDecimal.ZERO) != 0 
        			|| adapter.getOctober().compareTo(BigDecimal.ZERO) != 0 
        			|| adapter.getNovember().compareTo(BigDecimal.ZERO) != 0 
        			|| adapter.getDecember().compareTo(BigDecimal.ZERO) != 0 )
        	{
        		groupping(list, map, adapter.getAccount(), 
        				balance ? adapter.getJanuary().negate() : adapter.getJanuary(), 
        				balance ? adapter.getFebuary().negate() : adapter.getFebuary(), 
        				balance ? adapter.getMarch().negate() : adapter.getMarch(), 
        				balance ? adapter.getApril().negate() : adapter.getApril(), 
        				balance ? adapter.getMay().negate() : adapter.getMay(), 
        				balance ? adapter.getJune().negate() : adapter.getJune(),
        				balance ? adapter.getJuly().negate() : adapter.getJuly(), 
        				balance ? adapter.getAugust().negate() : adapter.getAugust(), 
        				balance ? adapter.getSeptember().negate() : adapter.getSeptember(), 
        				balance ? adapter.getOctober().negate() : adapter.getOctober(), 
        				balance ? adapter.getNovember().negate() : adapter.getNovember(), 
        				balance ? adapter.getDecember().negate() : adapter.getDecember());
        		
        		totalJan = totalJan.add(adapter.getJanuary());
        		totalFeb = totalFeb.add(adapter.getFebuary());
        		totalMar = totalMar.add(adapter.getMarch());
        		totalApr = totalApr.add(adapter.getApril());
        		totalMay = totalMay.add(adapter.getMay());
        		totalJun = totalJun.add(adapter.getJune());
        		totalJul = totalJul.add(adapter.getJuly());
        		totalAug = totalAug.add(adapter.getAugust());
        		totalSep = totalSep.add(adapter.getSeptember());
        		totalOct = totalOct.add(adapter.getOctober());
        		totalNov = totalNov.add(adapter.getNovember());
        		totalDec = totalDec.add(adapter.getDecember());
        	}
        	
        	
        }
        
        list.addAll(map.values());
        
        FastMap<String, Object> foot =  new FastMap<String, Object>();
        foot.put("foot", "Total "+caption);
        foot.put("totalJan", totalJan);
        foot.put("totalFeb", totalFeb);
        foot.put("totalMar", totalMar);
        foot.put("totalApr", totalApr);
        foot.put("totalMay", totalMay);
        foot.put("totalJun", totalJun);
        
        foot.put("totalJul", totalJul);
        foot.put("totalAug", totalAug);
        foot.put("totalSep", totalSep);
        foot.put("totalOct", totalOct);
        foot.put("totalNov", totalNov);
        foot.put("totalDec", totalDec);
         
        list.add(foot);
        
        setGrandJan(getGrandJan().add(totalJan));
        setGrandFeb(getGrandFeb().add(totalFeb));
        setGrandMar(getGrandMar().add(totalMar));
        setGrandApr(getGrandApr().add(totalApr));
        setGrandMay(getGrandMay().add(totalMay));
        setGrandJun(getGrandJun().add(totalJun));
        
        setGrandJul(getGrandJul().add(totalJul));
        setGrandAug(getGrandAug().add(totalAug));
        setGrandSep(getGrandSep().add(totalSep));
        setGrandOct(getGrandOct().add(totalOct));
        setGrandNov(getGrandNov().add(totalNov));
        setGrandDec(getGrandDec().add(totalDec));

        return list;
    }
	
	@Override
	public void setAccessType(AccessType accessType) 
	{
	}

	@Override
	public AccessType getAccessType() 
	{
		return null;
	}

}
