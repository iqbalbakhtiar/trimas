/**
 * 
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
 * @author
 * Betsu Brahmana Restu
 * PT. Sirius Indonesia
 * betsu@siriuserp.com
 */

@Getter
@Setter
@Entity
@Table(name = "product_consumption_master_schema")
public class ProductConsumptionMasterSchema extends Model
{
	private static final long serialVersionUID = -4736034431008850655L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_account")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private GLAccount account;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_accounting_schema")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private AccountingSchema accountingSchema;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_constumption_master")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private ProductConsumptionMaster consumption;

	@Override
	public String getAuditCode()
	{
		return getId() + " ";
	}
}
