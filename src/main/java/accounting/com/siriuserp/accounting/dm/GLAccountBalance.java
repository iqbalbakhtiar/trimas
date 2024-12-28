/**
 * Nov 10, 2008 12:02:08 PM
 * com.siriuserp.sdk.dm
 * GLAccountBalance.java
 */
package com.siriuserp.accounting.dm;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.SystemTransaction;
import com.siriuserp.sdk.dm.UserTransaction;

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
@Table(name = "gl_account_balance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GLAccountBalance extends Model
{
	private static final long serialVersionUID = 6194333640503847876L;

	@Column(name = "code")
	private String code;

	@Embedded
	private UserTransaction userTransaction = new UserTransaction();

	@Embedded
	private SystemTransaction systemTransaction = new SystemTransaction();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_currency")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Currency currency;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_gl_account")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private GLAccount account;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_organization")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party organization;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_accounting_period")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private AccountingPeriod accountingPeriod;

	@Transient
	private BigDecimal trxDebit = BigDecimal.ZERO;

	@Transient
	private BigDecimal trxCredit = BigDecimal.ZERO;

	public String getColor()
	{
		if (userTransaction.getDebet().compareTo(trxDebit) != 0 || userTransaction.getCredit().compareTo(trxCredit) != 0)
			return "#F8A076";

		return "";
	}

	@Override
	public String getAuditCode()
	{
		return this.id + "," + this.code;
	}
}
