package com.siriuserp.accountreceivable.dm;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.siriuserp.accounting.dm.BankAccount;
import com.siriuserp.accountpayable.dm.PaymentMethodType;
import com.siriuserp.sdk.utility.DecimalHelper;

import javolution.util.FastSet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ferdinand
 */

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "prepayment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Prepayment extends Receipt
{
	private static final long serialVersionUID = 8735203478318534094L;

	@Column(name = "reference")
	private String reference;

	@Enumerated(EnumType.STRING)
	@Column(name = "prepayment_method_type")
	private PaymentMethodType prepaymentMethodType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_prepayment_bank_account")
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@Fetch(FetchMode.SELECT)
	private BankAccount prepaymentBankAccount;

	@OneToMany(mappedBy = "prepayment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@OrderBy("id DESC")
	private Set<Receipt> receipts = new FastSet<Receipt>();
	
	public BigDecimal getTotalApplied()
	{
		return getReceipts().stream().map(receipt -> receipt.getReceiptInformation().getAmount()).collect(DecimalHelper.sum());
	}
	
	public BigDecimal getUnapplied()
	{
		if (getReceiptInformation().getPaymentMethodType().equals(PaymentMethodType.CLEARING))
			return getReceiptInformation().getAmount().subtract(getReceiptInformation().getBankCharges()).subtract(getTotalApplied());
		else
			return getReceiptInformation().getAmount().subtract(getTotalApplied());
	}
	
	// Returns false if totalApplied less than Receipt Amount
	public boolean isApplied()
	{
		return getTotalApplied().compareTo(getReceiptInformation().getAmount().subtract(getReceiptInformation().getBankCharges())) < 0;
	}

	@Override
	public String getUri()
	{
		return "prepaymentpreedit.htm";
	}

	@Override
	public String getReferenceType()
	{
		return "PREPAYMENT";
	}
}