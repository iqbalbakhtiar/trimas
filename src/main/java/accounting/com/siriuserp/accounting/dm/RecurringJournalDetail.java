/**
 * Nov 25, 2008 10:10:04 AM
 * com.siriuserp.sdk.dm
 * RecurringJournalDetail.java
 */
package com.siriuserp.accounting.dm;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "recurring_journal_detail")
public class RecurringJournalDetail extends Model
{
	private static final long serialVersionUID = 7591669673601298383L;

	@Column(name = "note")
	private String note;

	@Column(name = "amount")
	private BigDecimal amount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_gl_account")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private GLAccount account;

	@Column(name = "fk_posting_type")
	@Enumerated(EnumType.STRING)
	private GLPostingType postingType;

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
