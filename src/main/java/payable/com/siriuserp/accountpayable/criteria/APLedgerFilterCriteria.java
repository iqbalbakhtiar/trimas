/**
 * Mar 11, 2010 10:36:04 AM
 * com.siriuserp.accountpayable.criteria
 * APLedgerFilterCriteria.java
 */
package com.siriuserp.accountpayable.criteria;

import java.math.BigDecimal;
import java.util.Date;

import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.filter.AbstractReportFilterCriteria;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 * Version 1.5
 */

@Getter
@Setter
public class APLedgerFilterCriteria extends AbstractReportFilterCriteria
{
	private static final long serialVersionUID = 4021524969907924976L;

	private Party supplier;

	private Long supplierId;
	private Long currencyId;

	private BigDecimal rate;

	private Date dateFrom;
	private Date dateTo;
}
