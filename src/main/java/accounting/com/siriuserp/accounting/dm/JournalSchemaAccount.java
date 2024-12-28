/**
 * Nov 12, 2008 2:27:24 PM
 * com.siriuserp.sdk.dm
 * JournalSchemaAccount.java
 */
package com.siriuserp.accounting.dm;

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
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import com.siriuserp.sdk.dm.JSONSupport;
import com.siriuserp.sdk.dm.Model;

import javolution.util.FastMap;
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
@Table(name = "journal_schema_account")
public class JournalSchemaAccount extends Model implements JSONSupport
{
	private static final long serialVersionUID = 542700747519715568L;

	@Column(name = "mandatory")
	@Type(type = "yes_no")
	private boolean mandatory;

	@Column(name = "posting_type")
	@Enumerated(EnumType.STRING)
	private GLPostingType postingType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_gl_account")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private GLAccount account;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_journal_schema")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private GeneralJournalSchema journalSchema;

	@Override
	public String getAuditCode()
	{
		return null;
	}

	@JsonValue
	public Map<String, Object> val()
	{
		Map<String, Object> map = new FastMap<String, Object>();
		map.put("id", getId());
		map.put("accountId", getAccount().getId());
		map.put("accountCode", getAccount().getCode());
		map.put("accountName", getAccount().getName());

		return map;
	}
}
