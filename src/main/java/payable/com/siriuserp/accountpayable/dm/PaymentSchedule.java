package com.siriuserp.accountpayable.dm;

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

import com.siriuserp.sdk.dm.JSONSupport;
import com.siriuserp.sdk.dm.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "payment_schedule")
public class PaymentSchedule extends Model implements JSONSupport
{
	private static final long serialVersionUID = 1612983552842273317L;

	@Column(name = "schedule")
	private String schedule;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_payment_method")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private PaymentMethod paymentMethod;

	@Override
	public String getAuditCode()
	{
		return id + "," + schedule;
	}
}
