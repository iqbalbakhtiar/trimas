/**
 * Dec 1, 2008 11:48:38 AM
 * com.siriuserp.sdk.dm
 * IncomeSummaryBalance.java
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
@Table(name = "income_summary_balance")
public class IncomeSummaryBalance extends Model
{
	private static final long serialVersionUID = -8167186581038536508L;

	@Column(name = "amount")
	private BigDecimal amount = BigDecimal.valueOf(0);

	@Column(name = "gl_posting_type")
	@Enumerated(EnumType.STRING)
	private GLPostingType postingType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_accounting_period")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private AccountingPeriod accountingPeriod;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_organization")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party organization;

	@Override
	public String getAuditCode()
	{
		return this.id + "";
	}
}
