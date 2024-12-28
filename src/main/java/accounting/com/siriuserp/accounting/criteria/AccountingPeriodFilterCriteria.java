/**
 * Nov 18, 2008 5:12:56 PM
 * com.siriuserp.accounting.dto.filter
 * AccountingPeriodFilterCriteria.java
 */
package com.siriuserp.accounting.criteria;

import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.filter.AbstractFilterCriteria;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Getter
@Setter
public class AccountingPeriodFilterCriteria extends AbstractFilterCriteria
{
	private static final long serialVersionUID = 8872040580829804129L;

	private boolean relationUsed;
	private boolean openonly;

	private String name;
	private String target;
	private String level = "CHILD";

	private Party parent;
}