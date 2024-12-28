/**
 * Aug 20, 2009 4:42:20 PM
 * com.siriuserp.sdk.dm
 * InterJournalSchema.java
 */
package com.siriuserp.accounting.dm;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.siriuserp.sdk.dm.Party;

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
@Table(name = "inter_journal_schema")
public class InterJournalSchema extends GeneralJournalSchema
{
	private static final long serialVersionUID = 8982026669731372995L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_organization_from")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party from;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_organization_to")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party to;
}
