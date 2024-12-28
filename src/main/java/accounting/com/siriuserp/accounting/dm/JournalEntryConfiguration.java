/**
 * Nov 12, 2008 11:45:40 AM
 * com.siriuserp.sdk.dm
 * JournalEntryConfiguration.java
 */
package com.siriuserp.accounting.dm;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import com.siriuserp.sdk.dm.ExchangeType;
import com.siriuserp.sdk.dm.Model;

import javolution.util.FastSet;
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
@Table(name = "journal_entry_configuration")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class JournalEntryConfiguration extends Model
{
	private static final long serialVersionUID = 8625940338494858682L;

	@Column(name = "journal_entry_index")
	@Type(type = "yes_no")
	private boolean journalEntryIndex;

	@Column(name = "journal_distribution")
	@Type(type = "yes_no")
	private boolean journalDistribution;

	@Column(name = "journal_entry_transaction")
	@Type(type = "yes_no")
	private boolean journalEntryTransaction;

	@Column(name = "journal_batching_type")
	@Enumerated(EnumType.STRING)
	private BatchingType batchingType = BatchingType.NONE;

	@Column(name = "closing_exchange_type")
	@Enumerated(EnumType.STRING)
	private ExchangeType exchangeType = ExchangeType.MIDDLE;

	@OneToMany(mappedBy = "configuration", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	private Set<BatchedItem> batchedItems = new FastSet<BatchedItem>();

	@OneToMany(mappedBy = "journalEntryConfiguration", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	private Set<JournalDistributionSource> distributionSources = new FastSet<JournalDistributionSource>();

	@Override
	public String getAuditCode()
	{
		return this.id + "";
	}
}