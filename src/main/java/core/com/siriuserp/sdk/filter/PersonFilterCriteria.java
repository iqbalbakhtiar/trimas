/**
 * File Name  : PersonFilterCriteria.java
 * Created On : Oct 5, 2018
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sdk.filter;

import java.util.Date;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public class PersonFilterCriteria extends AbstractFilterCriteria
{
	private static final long serialVersionUID = -2516612041613984515L;

	private Long id;
	private String salutation;
	private String code;
	private String name;
	private Date date;
	private Long notPerson;
	private boolean newPerson = true;
	private boolean notSalesPerson = false;
	private boolean notSalesApprover = false;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getSalutation()
	{
		return salutation;
	}

	public void setSalutation(String salutation)
	{
		this.salutation = salutation;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getName()
	{
		return name;
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

	public Long getNotPerson() {
		return notPerson;
	}

	public void setNotPerson(Long notPerson) {
		this.notPerson = notPerson;
	}

	public boolean isNewPerson()
	{
		return newPerson;
	}

	public void setNewPerson(boolean newPerson)
	{
		this.newPerson = newPerson;
	}

	public boolean isNotSalesPerson()
	{
		return notSalesPerson;
	}

	public void setNotSalesPerson(boolean notSalesPerson)
	{
		this.notSalesPerson = notSalesPerson;
	}

	public boolean isNotSalesApprover()
	{
		return notSalesApprover;
	}

	public void setNotSalesApprover(boolean notSalesApprover)
	{
		this.notSalesApprover = notSalesApprover;
	}
}
