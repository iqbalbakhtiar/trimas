/**
 * File Name  : ReceiptManual.java
 * Created On : Dec 5, 2023
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountreceivable.dm;

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
@Table(name = "receipt_manual")
public class ReceiptManual extends Receipt
{
	private static final long serialVersionUID = 6385407106965596419L;

	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_purchase_memoable")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private PurchaseMemoable purchaseMemoable;*/

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_receipt_manual_type")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private ReceiptManualType receiptManualType;

	@Override
	public String getReferenceType()
	{
		//Used in Posting Role
		return "RECEIPT MANUAL";
	}
}
