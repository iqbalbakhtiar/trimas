/**
 * Jan 19, 2009 4:59:19 PM
 * com.siriuserp.administration.dto.filter
 * GeographicFilterCriteria.java
 */
package com.siriuserp.administration.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class GeographicFilterCriteria extends AbstractFilterCriteria
{
	private static final long serialVersionUID = -477806143352401631L;

	private String code;
	private String name;

	private Long parentId;
	private Long type;
	private String view;

	private String geoParent;
	private String geoType;

	private Integer level;

	public GeographicFilterCriteria()
	{
	}

	public Long getParentId()
	{
		return parentId;
	}

	public void setParentId(Long parentId)
	{
		this.parentId = parentId;
	}

	public Long getType()
	{
		return type;
	}

	public void setType(Long type)
	{
		this.type = type;
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

	public void setView(String view)
	{
		this.view = view;
	}

	public String getView()
	{
		return view;
	}

	public String getGeoParent()
	{
		return geoParent;
	}

	public void setGeoParent(String geoParent)
	{
		this.geoParent = geoParent;
	}

	public String getGeoType()
	{
		return geoType;
	}

	public void setGeoType(String geoType)
	{
		this.geoType = geoType;
	}

	public Integer getLevel()
	{
		return level;
	}

	public void setLevel(Integer level)
	{
		this.level = level;
	}

}
