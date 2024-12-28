/**
 * Mar 20, 2009 3:48:56 PM
 * com.siriuserp.accounting.criteria
 * FixedAssetFilterCriteria.java
 */
package com.siriuserp.accounting.criteria;

import java.util.Date;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class FixedAssetFilterCriteria extends AbstractFilterCriteria
{
    private static final long serialVersionUID = 4814121294299582427L;

    private String code;
    private String name;
    private Date dateFrom;
    private Date dateTo;
    private String category;
    private Long person;
    
    private boolean dispose;
    
    public FixedAssetFilterCriteria(){}

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

	public boolean isDispose() {
		return dispose;
	}

	public void setDispose(boolean dispose) {
		this.dispose = dispose;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getPerson() {
		return person;
	}

	public void setPerson(Long person) {
		this.person = person;
	}
   
}
