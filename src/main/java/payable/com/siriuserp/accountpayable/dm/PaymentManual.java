/**
 * File Name  : PaymentManual.java
 * Created On : Oct 17, 2023
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountpayable.dm;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Getter
@Setter
@Entity
@Table(name = "payment_manual")
public class PaymentManual extends Payment
{
	private static final long serialVersionUID = 1122919154937698363L;

	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_sales_memoable")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private SalesMemoable salesMemoable;*/

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_payment_manual_type")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private PaymentManualType paymentManualType;

	@Override
	public String getReferenceType()
	{
		//Used in Posting Role
		return "PAYMENT MANUAL";
	}

	@Override
	public String getUri()
	{
		return "paymentmanualpreedit.htm";
	}

	@Override
	public PaymentManualType getPaymentManualType()
	{
		return this.paymentManualType;
	}
}
