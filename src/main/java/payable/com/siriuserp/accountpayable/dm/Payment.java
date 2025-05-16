package com.siriuserp.accountpayable.dm;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import com.siriuserp.accounting.dm.BankAccount;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Exchange;
import com.siriuserp.sdk.dm.ExchangeType;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.Siblingable;

import javolution.util.FastSet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "payment")
@Inheritance(strategy = InheritanceType.JOINED)
public class Payment extends Model implements Siblingable, CashBankTransactionReferenceable
{
	private static final long serialVersionUID = -3722845729916796246L;

	@Column(name = "code")
	private String code;

	@Column(name = "date")
	private Date date;

	@Column(name = "real_date")
	private Date realDate;

	@Column(name = "cleared")
	@Type(type = "yes_no")
	private boolean cleared;

	@Column(name = "rate")
	private BigDecimal rate = BigDecimal.ONE;

	@Column(name = "note")
	private String note;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_party_organization")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party organization;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_facility")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Facility facility;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_party_supplier")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party supplier;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_currency")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Currency currency;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_payment_information")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private PaymentInformation paymentInformation;

	@OneToMany(mappedBy = "payment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	private Set<PaymentApplication> applications = new FastSet<PaymentApplication>();

	@Override
	public String getAuditCode()
	{
		return id + "," + code;
	}

	@Override
	public Long getReferenceId()
	{
		return getId();
	}

	@Override
	public Long getExtReferenceId()
	{
		return 0L;
	}

	@Override
	public String getCode()
	{
		return code;
	}

	@Override
	public Date getDate()
	{
		return date;
	}

	@Override
	public BigDecimal getAmount()
	{
		return getPaymentInformation().getAmount();
	}

	@Override
	public ExchangeType getExchangeType()
	{
		return null;
	}

	@Override
	public PaymentMethodType getPaymentMethodType()
	{
		return null;
	}

	@Override
	public String getUri()
	{
		return "paymentpreedit.htm";
	}

	@Override
	public String getNote()
	{
		return note;
	}

	@Override
	public Party getOrganization()
	{
		return organization;
	}

	@Override
	public Party getParty()
	{
		return getSupplier();
	}

	@Override
	public Currency getCurrency()
	{
		return currency;
	}

	@Override
	public Exchange getExchange()
	{
		return null;
	}

	@Override
	public BankAccount getBankAccount()
	{
		return getPaymentInformation().getBankAccount();
	}
}