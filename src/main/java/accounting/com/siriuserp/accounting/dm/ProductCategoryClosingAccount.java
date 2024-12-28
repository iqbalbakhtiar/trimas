/**
 * Mar 5, 2009 4:40:30 PM
 * com.siriuserp.sdk.dm
 * ProductCategoryClosingAccount.java
 */
package com.siriuserp.accounting.dm;

import javax.persistence.CascadeType;
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
@Table(name = "product_category_closing_account")
public class ProductCategoryClosingAccount extends Model
{
	private static final long serialVersionUID = 6295349577942071406L;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_closing_account")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private ClosingAccount closingAccount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_product_category_accounting_schema")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private ProductCategoryAccountingSchema schema;

	@Override
	public String getAuditCode()
	{
		return null;
	}
}
