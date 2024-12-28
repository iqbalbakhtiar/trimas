/**
 * Nov 25, 2008 10:08:48 AM
 * com.siriuserp.sdk.dm
 * RecurringJournalIndex.java
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
@Table(name = "recurring_journal_index")
public class RecurringJournalIndex extends Model
{
	private static final long serialVersionUID = -1065285931881563960L;

	@Column(name = "index_name")
	private String index;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_index_type")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private IndexType indexType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_recurring_journal")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private RecurringJournal recurringJournal;

	@Override
	public String getAuditCode()
	{
		return this.id + "";
	}
}
