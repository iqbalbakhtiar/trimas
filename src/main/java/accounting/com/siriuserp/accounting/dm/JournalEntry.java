package com.siriuserp.accounting.dm;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
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
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.ExchangeType;
import com.siriuserp.sdk.dm.Facility;
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
@Table(name = "journal_entry")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class JournalEntry extends Model
{
	private static final long serialVersionUID = -4104431081450129500L;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "note")
	private String note;

	@Column(name = "journal_schema")
	private String journalSchema;

	@Column(name = "journal_schema_id")
	private Long schemaId;

	@Column(name = "entry_date")
	private Date entryDate = new Date();

	@Column(name = "rate")
	private BigDecimal rate = BigDecimal.ONE;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_currency")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Currency currency;

	@Column(name = "exchange_type")
	@Enumerated(EnumType.STRING)
	private ExchangeType exchangeType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_accounting_period")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private AccountingPeriod accountingPeriod;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_chart_of_account")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private ChartOfAccount chartOfAccount;

	@Enumerated(EnumType.STRING)
	@Column(name = "journal_entry_type")
	private JournalEntryType entryType;

	@Enumerated(EnumType.STRING)
	@Column(name = "journal_entry_status")
	private JournalEntryStatus entryStatus;

	@Enumerated(EnumType.STRING)
	@Column(name = "journal_entry_source_type")
	private EntrySourceType entrySourceType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_organization")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party organization;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_facility")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Facility facility;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_person_posted_by")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party postedBy;

	@OneToMany(mappedBy = "journalEntry", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("id")
	private Set<JournalEntryIndex> indexes = new FastSet<JournalEntryIndex>();

	@OneToMany(mappedBy = "journalEntry", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("postingType DESC")
	private Set<JournalEntryDetail> details = new FastSet<JournalEntryDetail>();

	public String getVoucherCode()
	{
		for (JournalEntryIndex index : getIndexes())
		{
			if (index.getIndexType().getId().equals(IndexType.VOUCHER))
				return index.getContent();
		}

		return "";
	}

	public BigDecimal getAmount()
	{
		BigDecimal decimal = BigDecimal.ZERO;

		for (JournalEntryDetail detail : getDetails())
		{
			if (detail.getPostingType().equals(GLPostingType.DEBET))
				decimal = decimal.add(detail.getAmount());
		}

		return decimal;
	}

	public BigDecimal getDebet()
	{
		BigDecimal decimal = BigDecimal.ZERO;

		for (JournalEntryDetail detail : getDetails())
			if (detail.getPostingType().equals(GLPostingType.DEBET))
				decimal = decimal.add(detail.getAmount());

		return decimal.setScale(0, RoundingMode.HALF_EVEN);
	}

	public BigDecimal getCredit()
	{
		BigDecimal decimal = BigDecimal.ZERO;

		for (JournalEntryDetail detail : getDetails())
			if (detail.getPostingType().equals(GLPostingType.CREDIT))
				decimal = decimal.add(detail.getAmount());

		return decimal.setScale(0, RoundingMode.HALF_EVEN);
	}

	@Override
	public String getAuditCode()
	{
		return this.id + "," + this.code;
	}
}
