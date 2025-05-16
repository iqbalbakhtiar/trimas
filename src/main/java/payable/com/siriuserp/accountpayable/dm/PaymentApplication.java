package com.siriuserp.accountpayable.dm;

import java.math.BigDecimal;
import java.util.Date;

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

import com.siriuserp.accountreceivable.dm.WriteOffType;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.datawarehouse.APLedgerView;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "payment_application")
public class PaymentApplication extends Model implements APLedgerView
{
	private static final long serialVersionUID = 5521535215726930274L;

	@Column(name = "due_date")
	private Date appliedDate = new Date();

	@Column(name = "paid_amount")
	private BigDecimal paidAmount = BigDecimal.ZERO;

	@Column(name = "write_off")
	private BigDecimal writeOff = BigDecimal.ZERO;

	@Enumerated(EnumType.STRING)
	@Column(name = "write_off_type")
	private WriteOffType writeoffType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_payable")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Payable payable;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_payment")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Payment payment;

	@Override
	public Date getDate()
	{
		return getPayment().getDate();
	}

	@Override
	public Long getReferenceId()
	{
		return getPayment().getId();
	}

	@Override
	public String getCode()
	{
		return getPayment().getCode();
	}

	@Override
	public String getUri()
	{
		return getPayment().getUri();
	}

	@Override
	public BigDecimal getCredit()
	{
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal getDebet()
	{
		return getPayment().getPaymentInformation().getAmount().subtract(getWriteOff());
	}

	@Override
	public int compareTo(APLedgerView o)
	{
		return getPayment().getDate().compareTo(o.getDate());
	}

	@Override
	public String getAuditCode()
	{
		return "";
	}
}
