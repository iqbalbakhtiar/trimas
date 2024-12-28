/**
 * Dec 21, 2009 2:30:54 PM
 * com.siriuserp.sdk.dm
 * TaxAccountSchema.java
 */
package com.siriuserp.accounting.dm;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Tax;

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
@Table(name = "tax_account_schema")
public class TaxAccountSchema extends Model
{
	private static final long serialVersionUID = 7547999720927984432L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_tax")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Tax tax;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_accounting_schema")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private AccountingSchema accountingSchema;

	@OneToMany(mappedBy = "taxSchema", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("id asc")
	private Set<TaxPostingAccount> postingAccounts = new FastSet<TaxPostingAccount>();

	@Override
	public String getAuditCode()
	{
		return this.accountingSchema.getCode();
	}
}
