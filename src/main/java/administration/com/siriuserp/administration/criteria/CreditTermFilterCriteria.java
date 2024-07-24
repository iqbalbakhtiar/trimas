package com.siriuserp.administration.criteria;

import java.util.Date;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;

/**
 * @author Betsu Brahmana Restu
 * Sirius Indonesia, PT
 * betsu@siriuserp.com
 * 
 * Version 1.5
 */
public class CreditTermFilterCriteria extends AbstractFilterCriteria 
{
	private static final long serialVersionUID = 8375263367804935533L;
	
	private Long Customer;
	
	private Date dateFrom;
	
	private Date dateTo;
	
	private Date validFrom;
	
	private Date validTo;
	
	private Long last;
	
	private boolean customize = false;
	
	public CreditTermFilterCriteria(){}

	public Long getCustomer() 
	{
		return Customer;
	}

	public void setCustomer(Long customer) 
	{
		Customer = customer;
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

	public boolean isCustomize() 
	{
		return customize;
	}

	public void setCustomize(boolean customize) 
	{
		this.customize = customize;
	}

	public Long getLast()
	{
		return last;
	}

	public void setLast(Long last) 
	{
		this.last = last;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}
	
}
