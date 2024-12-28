/**
 * Dec 2, 2008 10:46:58 AM
 * com.siriuserp.sdk.adapter
 * AbstractAccountingReportAdapter.java
 */
package com.siriuserp.accounting.adapter;

import java.math.BigDecimal;

import com.siriuserp.accounting.dm.GLAccount;
import com.siriuserp.accounting.dm.JournalEntryType;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Getter
@Setter
public abstract class AbstractAccountingReportAdapter extends AbstractReportAdapter
{
	private static final long serialVersionUID = 1820289741866676151L;

	protected BigDecimal credit = BigDecimal.ZERO;
	protected BigDecimal debet = BigDecimal.ZERO;
	protected BigDecimal adjustmentdebet = BigDecimal.ZERO;
	protected BigDecimal adjustmentcredit = BigDecimal.ZERO;
	protected JournalEntryType journalEntryType;
	protected GLAccount account;
}
