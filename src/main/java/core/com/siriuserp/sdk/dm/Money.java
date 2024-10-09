package com.siriuserp.sdk.dm;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Money implements Comparable<Money> {
	
	@Column(name = "amount")
	private BigDecimal amount = BigDecimal.ZERO;
	
	@Column(name = "rate")
	private BigDecimal rate = BigDecimal.ONE;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "exchange_type")
	private ExchangeType exchangeType = ExchangeType.SPOT;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_currency")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Currency currency;

	@Override
	public int compareTo(Money money) {
		if (!this.getCurrency().getId().equals(money.getCurrency().getId()))
			return -1;

		return 0;
	}

}
