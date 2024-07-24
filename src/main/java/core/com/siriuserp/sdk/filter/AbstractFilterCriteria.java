/**
 * Aug 30, 2007 11:05:40 AM
 * com.siriuserp.sdk.filter
 * AbstractFilterCriteria.java
 */
package com.siriuserp.sdk.filter;

import java.util.Date;

import com.siriuserp.sdk.dm.Role;
import com.siriuserp.sdk.dm.User;

import javolution.util.FastList;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Getter
@Setter
public abstract class AbstractFilterCriteria implements GridViewFilterCriteria
{
	private static final long serialVersionUID = -2510650205758698019L;
	
	public static final String MODE_DEFAULT="DEFAULT";
	public static final String MODE_LOAD_ALL="ALL";

	protected int max = 20;
	protected int page = 1;
	protected int nextPage;
	protected int prevPage;
	protected int totalPage;
	
	protected String mode=MODE_DEFAULT;

	protected Role role;
	protected User user;
	
	protected Date date;

	protected Long activePerson;
	protected Long organization;
	
	protected FastList<Long> organizations = new FastList<Long>();
	
	@Override
	public int start()
	{
		return (getPage() - 1) * getMax();
	}
}
