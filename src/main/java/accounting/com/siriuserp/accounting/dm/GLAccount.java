package com.siriuserp.accounting.dm;

import java.util.Map;
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

import org.codehaus.jackson.annotate.JsonValue;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import com.siriuserp.sdk.dm.JSONSupport;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastMap;
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
@Table(name = "glaccount")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GLAccount extends Model implements JSONSupport
{
	private static final long serialVersionUID = 8637950422769337058L;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "null_warning", length = 1)
	@Type(type = "yes_no")
	private boolean warning;

	@Column(name = "alias")
	private String alias;

	@Column(name = "note")
	private String note;

	@Column(name = "cash_type")
	@Enumerated(EnumType.STRING)
	private GLCashType cashType;

	@Column(name = "closing_type")
	@Enumerated(EnumType.STRING)
	private GLClosingType closingType;

	@Column(name = "posting_type")
	@Enumerated(EnumType.STRING)
	private GLPostingType postingType;

	@Column(name = "gl_level")
	@Enumerated(EnumType.STRING)
	private GLLevel level;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_account_type")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private GLAccountType accountType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_coa")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private ChartOfAccount coa;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_gl_parent")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private GLAccount parent;

	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@OrderBy("code")
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	private Set<GLAccount> childrens = new FastSet<GLAccount>();

	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	private Set<GLAccountBalance> balances = new FastSet<GLAccountBalance>();

	@Override
	public String getAuditCode()
	{
		return this.id + "," + this.code;
	}

	public static final GLAccount newInstance(String id)
	{
		if (SiriusValidator.validateParamWithZeroPosibility(id))
		{
			GLAccount account = new GLAccount();
			account.setId(Long.valueOf(id));

			return account;
		}

		return null;
	}

	@JsonValue
	public Map<String, Object> val()
	{
		Map<String, Object> map = new FastMap<String, Object>();
		map.put("accountId", getId());
		map.put("accountCode", getCode());
		map.put("accountName", getName());

		return map;
	}
}
