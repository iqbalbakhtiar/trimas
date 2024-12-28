/**
 * Nov 18, 2008 1:45:24 PM
 * com.siriuserp.sdk.dm
 * JournalEntryAppliedIndex.java
 */
package com.siriuserp.accounting.dm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

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
@Table(name = "journal_entry_index")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class JournalEntryIndex extends Model
{
	private static final long serialVersionUID = -1938876017275410142L;

	@Column(name = "index_name")
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_index_type")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private IndexType indexType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_journal_entry")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private JournalEntry journalEntry;

	@Override
	public String getAuditCode()
	{
		return this.id + "";
	}
}
