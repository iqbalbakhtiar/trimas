/**
 * Oct 10, 2006 11:44:36 AM
 * net.konsep.sirius.accounting.DTO.filter
 * AccountingJournalFilterCriteria.java
 */
package com.siriuserp.accounting.criteria;

import java.util.Date;

import com.siriuserp.accounting.dm.JournalEntryStatus;
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
public class JournalEntryFilterCriteria extends AbstractFilterCriteria
{
	private static final long serialVersionUID = -2400154921019234312L;

	private String code;
	private String name;
	private String condition;
	private String entryType;
	private String entrySourceType;
	private String createdBy;
	private String period;

	private Date dateFrom;
	private Date dateTo;

	private Party org;
	private JournalEntryStatus entryStatus;
}
