/**
 * Aug 31, 2006 1:50:29 PM
 * net.konsep.sirius.sales.dto.filter
 * CustomerFilterCriteria.java
 */
package com.siriuserp.sdk.filter;

import java.util.Date;

import com.siriuserp.sdk.utility.DateHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public class CustomerFilterCriteria extends AbstractFilterCriteria
{
	private static final long serialVersionUID = 2929960869111820097L;

	private String code;
	private String salutation;
	private String name;
	private String sort;
	private String clean;
	private Date date;

	private Long facility;

	public CustomerFilterCriteria()
	{
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getSalutation()
	{
		return salutation;
	}

	public void setSalutation(String salutation)
	{
		this.salutation = salutation;
	}

	public String getName()
	{
		return name;
	}

	public String getSort()
	{
		return sort;
	}

	public void setSort(String sort)
	{
		this.sort = sort;
	}

	public String getClean()
	{
		return clean;
	}

	public void setClean(String clean)
	{
		this.clean = clean;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public String getDateString()
	{
		if (this.date != null)
			return DateHelper.format(getDate());

		return null;
	}

	public Long getFacility()
	{
		return facility;
	}

	public void setFacility(Long facility)
	{
		this.facility = facility;
	}
}
