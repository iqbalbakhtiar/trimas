/**
 * May 4, 2009 5:24:04 PM
 * com.siriuserp.administration.criteria
 * GridFilterCriteria.java
 */
package com.siriuserp.administration.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class GridFilterCriteria extends AbstractFilterCriteria
{
    private static final long serialVersionUID = 4377932190736384259L;

    private Long facility;
    
    private Long product;
    
    private String name;
    
    private String code;
    
    private String source;
    
    private String destination;
    
    public GridFilterCriteria(){}

    public Long getFacility()
    {
        return facility;
    }

    public void setFacility(Long facility)
    {
        this.facility = facility;
    }

    public Long getProduct()
    {
        return product;
    }

    public void setProduct(Long product)
    {
        this.product = product;
    }

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public String getCode() 
	{
		return code;
	}

	public void setCode(String code) 
	{
		this.code = code;
	}

	public String getDestination() 
	{
		return destination;
	}

	public void setDestination(String destination) 
	{
		this.destination = destination;
	}

	public String getSource() 
	{
		return source;
	}

	public void setSource(String source) 
	{
		this.source = source;
	}
}
