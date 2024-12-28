package com.siriuserp.accounting.dm;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonValue;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.siriuserp.sdk.dm.JSONSupport;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.utility.SiriusValidator;

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
@Table(name = "journal_entry_detail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class JournalEntryDetail extends Model implements JSONSupport
{
	private static final long serialVersionUID = -2825180556155487924L;

	@Column(name = "note")
	private String note;

	@Column(name = "transaction_date")
	private Date transactionDate;

	@Column(name = "amount")
	private BigDecimal amount;

	@Column(name = "closing_account_type")
	private Long closingAccountType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_gl_account")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private GLAccount account;

	@Column(name = "fk_posting_type")
	@Enumerated(EnumType.STRING)
	private GLPostingType postingType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_journal_entry")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private JournalEntry journalEntry;

	public static final JournalEntryDetail newInstance(String id)
	{
		if (SiriusValidator.validateParamWithZeroPosibility(id))
		{
			JournalEntryDetail accountingTransaction = new JournalEntryDetail();
			accountingTransaction.setId(Long.valueOf(id));

			return accountingTransaction;
		}

		return null;
	}

	@JsonValue
	public Map<String, Object> val()
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", getJournalEntry().getId());
		map.put("date", new SimpleDateFormat("dd-MM").format(getJournalEntry().getEntryDate()));
		map.put("dateReport", new SimpleDateFormat("dd-MMM-yyyy").format(getJournalEntry().getEntryDate()));
		map.put("code", getJournalEntry().getCode());
		map.put("name", getJournalEntry().getName());
		map.put("note", getNote() != null ? getNote() : getJournalEntry().getName());
		map.put("parentNote", getJournalEntry().getNote());
		map.put("debet", getPostingType().compareTo(GLPostingType.DEBET) == 0 ? getAmount() : BigDecimal.ZERO);
		map.put("credit", getPostingType().compareTo(GLPostingType.CREDIT) == 0 ? getAmount() : BigDecimal.ZERO);
		map.put("debetRp", getPostingType().compareTo(GLPostingType.DEBET) == 0 ? getAmount().multiply(getJournalEntry().getRate()) : BigDecimal.ZERO);
		map.put("creditRp", getPostingType().compareTo(GLPostingType.CREDIT) == 0 ? getAmount().multiply(getJournalEntry().getRate()) : BigDecimal.ZERO);
		map.put("balance", getAmount().compareTo(BigDecimal.ZERO) < 0 && getPostingType().compareTo(GLPostingType.DEBET) == 0 ? getAmount().negate() : getAmount());
		map.put("amount", getAmount());
		map.put("account", getAccount());
		map.put("postingType", getPostingType());
		map.put("rate", getJournalEntry().getRate());

		return map;
	}

	@Override
	public String getAuditCode()
	{
		return null;
	}
}
