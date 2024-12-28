/**
 * Feb 2, 2009 10:22:40 AM
 * com.siriuserp.reporting.accounting.adapter
 * ProfitLossFilterCriteria.java
 */
package com.siriuserp.reporting.accounting.criteria;

import java.util.Set;

import com.siriuserp.accounting.criteria.DefaultAccountingReportFilterCriteria;
import com.siriuserp.reporting.accounting.adapter.IndexTypeReportAdapter;

import javolution.util.FastSet;

/**
 * @author Muhammad Rizal
 */
public class ProfitLossFilterCriteria extends DefaultAccountingReportFilterCriteria 
{
	private static final long serialVersionUID = -7839010023647122999L;

	private Set<IndexTypeReportAdapter> indexes = new FastSet<IndexTypeReportAdapter>();
	
	private Long org;
	private Integer year;
	private String aliases;

	public ProfitLossFilterCriteria() {}

	public Long getOrg() 
	{
		return org;
	}

	public void setOrg(Long org)
	{
		this.org = org;
	}

	public Integer getYear() 
	{
		return year;
	}

	public void setYear(Integer year) 
	{
		this.year = year;
	}

	public String getAliases() 
	{
		return aliases;
	}

	public void setAliases(String aliases) 
	{
		this.aliases = aliases;
	}

	public Set<IndexTypeReportAdapter> getIndexes()
	{
		return indexes;
	}

	public void setIndexes(Set<IndexTypeReportAdapter> indexes) 
	{
		this.indexes = indexes;
	}
}
