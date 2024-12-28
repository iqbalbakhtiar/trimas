/**
 * Aug 20, 2009 4:41:34 PM
 * com.siriuserp.sdk.dm
 * StandardJournalSchema.java
 */
package com.siriuserp.accounting.dm;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Getter
@Setter
@Entity
@Table(name = "standard_journal_schema")
public class StandardJournalSchema extends GeneralJournalSchema
{
	private static final long serialVersionUID = 520799943550735591L;
}
