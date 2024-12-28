/**
 * 
 */
package com.siriuserp.accounting.dm;

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
import com.siriuserp.sdk.dm.PaymentMethodType;

import lombok.Getter;
import lombok.Setter;

/**
 * @author
 * Betsu Brahmana Restu
 * PT. Sirius Indonesia
 * betsu@siriuserp.com
 */

@Getter
@Setter
@Entity
@Table(name = "cash_bank_schema")
public class CashBankSchema extends Model
{
	private static final long serialVersionUID = -1807802930510962360L;

	@Enumerated(EnumType.STRING)
	@Column(name = "method_type")
	private PaymentMethodType methodType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_bank_account")
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@Fetch(FetchMode.SELECT)
	private BankAccount bankAccount;

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
		return this.id + ",";
	}
}
