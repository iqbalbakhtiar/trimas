/**
 * 
 */
package com.siriuserp.inventory.dm;

/**
 * @author Betsu Brahmana Restu
 * Sirius Indonesia, PT
 * betsu@siriuserp.com
 *
 */

public enum CategoryType
{
	SPAREPART("ALL","","3", 0),
	FINISH_GOODS("ALL", "", "1", 1),
	MATERIAL("ALL", "2", "2", 2),
	WIP("ALL", 3),
	WASTE("ALL", "2", "2", 4),
	SUPPORTING_MATERIAL("ALL", 5),;

	private String type;
	private String url = "";
	private String page = "";
	private int order;
	
	private CategoryType(String type)
	{
		this(type, null);
	}
	
	private CategoryType(String type, String url)
	{
		this(type, url, null);
	}
	
	private CategoryType(String type, String url, String page)
	{
		this(type, url, page, 0);
	}
	
	private CategoryType(String type, int order)
	{
		this(type, null, null, order);
	}
	
	private CategoryType(String type, String url, String page, int order)
	{
		this.type = type;
		this.url = url;
		this.page = page;
		this.order = order;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getUrl() 
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getPage() 
	{
		return page;
	}

	public void setPage(String page)
	{
		this.page = page;
	}

	public int getOrder() 
	{
		return order;
	}

	public void setOrder(int order) 
	{
		this.order = order;
	}

	public String getNormalizedName()
	{
		return this.toString().replace("_", " ");
	}

	public String getMessage()
	{
		return this.toString().replace("_", "").toLowerCase();
	}
}
