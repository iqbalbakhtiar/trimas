package com.siriuserp.accountpayable.dm;

import com.siriuserp.accounting.dm.BankAccount;
import com.siriuserp.sdk.dm.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "payment_information")
public class PaymentInformation extends Model
{
	private static final long serialVersionUID = 37092081305902289L;

	@Column(name = "due_date")
	private Date dueDate;

	@Column(name = "amount")
	private BigDecimal amount = BigDecimal.ZERO;

	@Column(name = "bank_charges")
	private BigDecimal bankCharges = BigDecimal.ZERO;

	@Column(name = "other_charges")
	private BigDecimal otherCharges = BigDecimal.ZERO;

	@Column(name = "reference")
	private String reference;

	@Column(name = "reference_to")
	private String referenceTo;

	@Column(name = "note", length = 255)
	private String note;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_method_type")
	private PaymentMethodType paymentMethodType;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_method_type_to")
	private PaymentMethodType paymentMethodTypeTo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_bank_account")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private BankAccount bankAccount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_bank_account_to")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private BankAccount bankAccountTo;

	@Override
	public String getAuditCode()
	{
		return "";
	}
}
