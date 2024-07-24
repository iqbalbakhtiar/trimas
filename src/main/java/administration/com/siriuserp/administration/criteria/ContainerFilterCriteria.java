/**
 * May 4, 2009 5:24:23 PM
 * com.siriuserp.administration.criteria
 * ContainerFilterCriteria.java
 */
package com.siriuserp.administration.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public class ContainerFilterCriteria extends AbstractFilterCriteria
{
	private static final long serialVersionUID = 3835529002495532441L;

	private Long grid;
	private Long product;
	private Long facility;

	public ContainerFilterCriteria()
	{
	}

	public Long getGrid()
	{
		return grid;
	}

	public void setGrid(Long grid)
	{
		this.grid = grid;
	}

	public Long getProduct()
	{
		return product;
	}

	public void setProduct(Long product)
	{
		this.product = product;
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
