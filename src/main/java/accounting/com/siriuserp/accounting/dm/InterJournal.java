/**
 * Nov 26, 2008 9:37:28 AM
 * com.siriuserp.sdk.dm
 * InterJournal.java
 */
package com.siriuserp.accounting.dm;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

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
@Table(name = "inter_journal")
public class InterJournal extends Model
{
	private static final long serialVersionUID = 4129307437818507199L;

	@Column(name = "code")
	private String code;

	@Column(name = "completed")
	@Type(type = "yes_no")
	private boolean completed;

	@Column(name = "amount")
	private BigDecimal amount;

	@Column(name = "posting_type")
	@Enumerated(EnumType.STRING)
	private GLPostingType postingType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_organization_to")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party organizationTo;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "fk_journal_entry_to")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private JournalEntry entryTo;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "fk_journal_entry_from")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private JournalEntry entryFrom;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "fk_interjournalable")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private InterJournalable interJournalable;

	@Override
	public String getAuditCode()
	{
		return this.id + "," + this.code;
	}
}
