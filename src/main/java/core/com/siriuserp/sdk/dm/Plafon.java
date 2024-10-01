package com.siriuserp.sdk.dm;

import java.math.BigDecimal;
import java.util.Date;

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
import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "plafon")
public class Plafon extends Model {
	
	private static final long serialVersionUID = -2293838388616557820L;
	
	@Column(name = "plafon")
	private BigDecimal plafon = BigDecimal.ZERO;
	
	@Column(name = "available")
	private BigDecimal available = BigDecimal.ZERO;
	
	@Column(name = "usage_amount")
	private BigDecimal usageAmount = BigDecimal.ZERO;
	
	@Column(name = "valid_from")
	private Date validFrom;

	@Column(name = "valid_to")
	private Date validTo;
	
	@Column(name = "active")
	@Type(type = "yes_no")
	protected boolean active = true;
	
	@Column(name = "note")
	private String note;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_currency")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Currency currency;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_party_relationship")
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@Fetch(FetchMode.SELECT)
	private PartyRelationship partyRelationship;

	@Override
	public String getAuditCode() {
		return this.id + "," + this.partyRelationship.getPartyFrom().getCode();
	}

}
