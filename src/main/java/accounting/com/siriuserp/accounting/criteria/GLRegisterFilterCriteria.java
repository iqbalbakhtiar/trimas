/**
 * Jan 30, 2009 11:12:32 AM
 * com.siriuserp.reporting.accounting.adapter
 * GLRegisterFilterCriteria.java
 */
package com.siriuserp.accounting.criteria;

import java.util.Date;

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
public class GLRegisterFilterCriteria extends DefaultAccountingReportFilterCriteria
{
	private static final long serialVersionUID = -7479236401296206444L;

	private Long currency;
	private Long organization;
	private Long customer;
	private Long tax;
	private Long account;

	private String mode;

	private int max = 20;
	private int page = 1;
	private int nextPage;
	private int prevPage;
	private int totalPage;

	private Date dateFrom;
	private Date dateTo;

	private FastList<Long> accounts = new FastList<Long>();

	public int start()
	{
		return (this.page - 1) * this.max;
	}
}
