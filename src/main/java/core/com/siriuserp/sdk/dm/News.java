/**
 * Nov 22, 2007 9:57:31 AM
 * net.konsep.sirius.model
 * NewsCMS.java
 */
package com.siriuserp.sdk.dm;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Ersi Agustin
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Entity
@Table(name = "news")
public class News extends Model
{
	private static final long serialVersionUID = 1L;

	@Column(name = "date", nullable = false)
	private Date date;

	@Column(name = "title", nullable = true, length = 50)
	private String title;

	@Column(name = "content", nullable = false, length = 255)
	private String content;

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	@Override
	public String getAuditCode()
	{
		return this.title;
	}
}
