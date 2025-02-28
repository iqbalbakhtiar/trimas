/**
 * File Name  : Memo.java
 * Created On : Feb 26, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountreceivable.dm;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.siriuserp.accounting.dm.JournalEntry;
import com.siriuserp.accounting.dm.JournalEntryStatus;
import com.siriuserp.accounting.dm.Postingable;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Party;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "memo")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Memo extends Model implements Postingable
{
	private static final long serialVersionUID = -6276801747695465951L;

	@Column(name = "code")
	private String code;

	@Column(name = "amount")
	private BigDecimal amount = BigDecimal.ZERO;

	@Column(name = "date")
	private Date date;

	@Column(name = "uri")
	protected String uri;

	@Column(name = "note")
	private String note;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_organization")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party organization;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_customer")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party party;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_journal_entry")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private JournalEntry journalEntry;

	@Override
	public Party getOrganization()
	{
		return organization;
	}

	@Override
	public Date getDate()
	{
		return date;
	}

	@Override
	public JournalEntry getJournalEntry()
	{
		return journalEntry;
	}

	@Override
	public void setJournalEntry(JournalEntry journalEntry)
	{
		this.journalEntry = journalEntry;
	}

	public boolean isRejournalable()
	{
		if (getJournalEntry() == null)
			return true;
		else if (getJournalEntry() != null && getJournalEntry().getEntryStatus().equals(JournalEntryStatus.BATCHED))
			return true;

		return false;
	}

	@Override
	public String getAuditCode()
	{
		return id + ',' + code;
	}
}
