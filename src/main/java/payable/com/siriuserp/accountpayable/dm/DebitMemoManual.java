/**
 * File Name  : DebitMemoManual.java
 * Created On : Nov 09, 2021
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountpayable.dm;

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

import com.siriuserp.accountreceivable.dm.Memo;

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
@Table(name = "debit_memo_manual")
public class DebitMemoManual extends Memo
{
	private static final long serialVersionUID = -4770080319575725139L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_payable")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Payable payable;

	@Enumerated(EnumType.STRING)
	@Column(name = "reference_type")
	private DebitMemoReferenceType referenceType;

	@Override
	public String getAuditCode()
	{
		return this.id + " " + getCode();
	}
}
