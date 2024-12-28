/**
 * Nov 25, 2008 10:07:26 AM
 * com.siriuserp.sdk.dm
 * RecurringJournal.java
 */
package com.siriuserp.accounting.dm;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.ExchangeType;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Party;

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
@Table(name = "recurring_journal")
public class RecurringJournal extends Model
{
	private static final long serialVersionUID = -7339502668554554015L;

	@Column(name = "recurring_day")
	private Integer day;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "note")
	private String note;

	@Column(name = "enabled")
	@Type(type = "yes_no")
	private boolean enabled;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_currency")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Currency currency;

	@Column(name = "exchange_type")
	@Enumerated(EnumType.STRING)
	private ExchangeType exchangeType;

	@Column(name = "entry_type")
	@Enumerated(EnumType.STRING)
	private JournalEntryType entryType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_organization")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party organization;

	@OneToMany(mappedBy = "recurringJournal", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	private Set<RecurringJournalIndex> indexes = new FastSet<RecurringJournalIndex>();

	@OneToMany(mappedBy = "recurringJournal", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	private Set<RecurringJournalDetail> details = new FastSet<RecurringJournalDetail>();

	@Override
	public String getAuditCode()
	{
		return this.id + "," + this.code;
	}
}
