/**
 * File Name  : FundApplicationItem.java
 * Created On : Jul 12, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountpayable.dm;

import java.math.BigDecimal;

import javax.persistence.Column;
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
import com.siriuserp.sdk.dm.Party;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "fund_application_item")
public class FundApplicationItem extends Model
{
	private static final long serialVersionUID = 4001445612950611846L;

	@Column(name = "amount")
	private BigDecimal amount = BigDecimal.ZERO;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_party_customer")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party supplier;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_fund_application")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private FundApplication fundApplication;

	@Override
	public String getAuditCode()
	{
		return this.getId().toString();
	}
}
