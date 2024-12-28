/**
 * Oct 1, 2009 10:23:58 AM
 * com.siriuserp.sdk.dm
 * InterJournalable.java
 */
package com.siriuserp.accounting.dm;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.ExchangeType;
import com.siriuserp.sdk.dm.Model;
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
@Table(name = "inter_journalable")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class InterJournalable extends Model
{
	private static final long serialVersionUID = 6046354014182687820L;

	@Column(name = "date")
	protected Date date;

	@Column(name = "name")
	protected String name;

	@Column(name = "uri")
	protected String uri;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_inter_journal_schema")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	protected InterJournalSchema journalSchema;

	/*@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_payment_information")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	protected PaymentInformation information;*/

	@OneToOne(mappedBy = "interJournalable", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	protected InterJournal journal;

	public abstract Party getFrom();

	public abstract Party getTo();

	public abstract Currency getCurrency();

	public abstract ExchangeType getExchangeType();

	public abstract BigDecimal getRate();

	public abstract Long getRedirectID();
}
