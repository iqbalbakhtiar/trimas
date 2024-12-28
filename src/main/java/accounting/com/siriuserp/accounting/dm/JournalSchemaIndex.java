/**
 * Nov 12, 2008 2:34:29 PM
 * com.siriuserp.sdk.dm
 * JournalSchemaIndex.java
 */
package com.siriuserp.accounting.dm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import com.siriuserp.sdk.dm.Model;

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
@Table(name = "journal_schema_index")
public class JournalSchemaIndex extends Model
{
	private static final long serialVersionUID = 6486968794193525434L;

	@Column(name = "index_on")
	@Type(type = "yes_no")
	private boolean on;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_journal_entry_index")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private IndexType index;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_journal_schema")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private JournalSchema journalSchema;

	@Override
	public String getAuditCode()
	{
		return null;
	}
}
