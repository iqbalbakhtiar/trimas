/**
 * Oct 27, 2009 1:42:57 PM
 * com.siriuserp.sdk.dm
 * Preasset.java
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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import com.siriuserp.inventory.dm.Product;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.ExchangeType;
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
@Table(name = "pre_asset")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Preasset extends Model
{
	private static final long serialVersionUID = 7484837945719712861L;

	@Column(name = "used")
	@Type(type = "yes_no")
	private boolean used;

	@Column(name = "amount")
	private BigDecimal amount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_product")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_currency")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Currency currency;

	@Enumerated(EnumType.STRING)
	@Column(name = "exchange_type")
	private ExchangeType exchangeType;

	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_invoice_verification")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private InvoiceVerification invoiceVerification;*/

	@OneToOne(mappedBy = "preasset", fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private FixedAsset fixedAsset;

	@Override
	public String getAuditCode()
	{
		return null;
	}
}
