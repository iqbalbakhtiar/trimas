/**
 * Sep 21, 2007 3:20:49 PM
 * net.konsep.sirius.model
 * ClosingAccountType.java
 */
package com.siriuserp.accounting.dm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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
@Table(name = "closing_account_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ClosingAccountType extends Model
{
	private static final long serialVersionUID = -469045010429916810L;

	//Latest ID : 27 (Please always update this ID after Add or Remove Account)
	public static final Long INCOME_SUMMARY = Long.valueOf(1);
	public static final Long CAPITAL_ACCOUNT = Long.valueOf(2);
	public static final Long CLOSING_ACCOUNT = Long.valueOf(3);
	public static final Long ASSET = Long.valueOf(4);
	public static final Long ASSET_DEPRECIATION = Long.valueOf(5);
	public static final Long ACCUMULATIVE_DEPRECIATION = Long.valueOf(6);
	public static final Long GAIN_LOSS_AND_DISPOSAL = Long.valueOf(7);
	public static final Long CURRENCY_GAIN = Long.valueOf(8);
	public static final Long CURRENCY_LOSS = Long.valueOf(9);
	public static final Long INVENTORY = Long.valueOf(10);

	//Account Receivables
	public static final Long AR_PREPAYMENT = Long.valueOf(11);
	public static final Long AR_RECEIVABLES = Long.valueOf(12);
	public static final Long AR_RECEIVABLES_SERVICE = Long.valueOf(13);
	public static final Long AR_WRITEOFF_ADJUSTMENT = Long.valueOf(14);
	public static final Long AR_WRITEOFF_STAMP = Long.valueOf(15);
	public static final Long AR_DISCOUNT_UNDERTABLE = Long.valueOf(16);
	public static final Long AR_UNINVOICED = Long.valueOf(17);
	public static final Long AR_UNINVOICED_SERVICE = Long.valueOf(18);

	//Account Payables
	public static final Long AP_PREPAYMENT = Long.valueOf(19);
	public static final Long AP_PAYABLES = Long.valueOf(20);
	public static final Long AP_WRITEOFF_ADJUSTMENT = Long.valueOf(21);
	public static final Long AP_WRITEOFF_STAMP = Long.valueOf(22);
	public static final Long AP_DISCOUNT_UNDERTABLE = Long.valueOf(23);

	//Sales
	public static final Long SALES_DISCOUNT = Long.valueOf(24);
	public static final Long SALES_DISCOUNT_SERVICE = Long.valueOf(25);
	public static final Long SALES_ADJUSTMENT = Long.valueOf(26);
	public static final Long SALES_RETURN = Long.valueOf(27);

	@Column(name = "name", nullable = false, unique = true, length = 50)
	private String name;

	@Column(name = "group_type")
	@Enumerated(EnumType.STRING)
	private GroupType groupType = GroupType.NONASSET;

	public static final synchronized ClosingAccountType newInstance(String id)
	{
		if (id == null || id.equals(""))
			return null;

		ClosingAccountType accountType = new ClosingAccountType();
		accountType.setId(Long.valueOf(id));

		return accountType;
	}

	@Override
	public String getAuditCode()
	{
		return this.id + "," + this.name;
	}
}