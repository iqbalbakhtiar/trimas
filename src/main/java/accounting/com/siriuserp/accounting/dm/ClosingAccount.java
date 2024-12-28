/**
 * Sep 21, 2007 3:14:32 PM
 * net.konsep.sirius.model
 * IncomeSummaryAccount.java
 */
package com.siriuserp.accounting.dm;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
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
@Table(name = "closing_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ClosingAccount extends Model
{
	private static final long serialVersionUID = 8150528683547489034L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_gl_account")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private GLAccount account;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_accounting_schema")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private AccountingSchema accountingSchema;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_closing_account_type")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private ClosingAccountType closingAccountType;

	@Override
	public String getAuditCode()
	{
		return null;
	}
}
