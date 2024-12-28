/**
 * Dec 21, 2009 2:40:48 PM
 * com.siriuserp.sdk.dm
 * TaxPostingAccount.java
 */
package com.siriuserp.accounting.dm;

import javax.persistence.Entity;
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
@Table(name = "tax_posting_account")
public class TaxPostingAccount extends Model
{
	private static final long serialVersionUID = -355637103272433723L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_gl_account")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private GLAccount account;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_closing_account_type")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private ClosingAccountType accountType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_tax_account_schema")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private TaxAccountSchema taxSchema;

	@Override
	public String getAuditCode()
	{
		return null;
	}
}
