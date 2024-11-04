package com.siriuserp.accounting.dm;

import java.util.Map;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import com.siriuserp.sdk.dm.AccountType;
import com.siriuserp.sdk.dm.JSONSupport;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PartyBankAccount;

import javolution.util.FastMap;
import javolution.util.FastSet;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bank_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BankAccount extends Model implements JSONSupport {
	
	private static final long serialVersionUID = -2244765362354284887L;
	
	@Column(name = "code")
	private String code;

	@Column(name = "bank_name")
	private String bankName;

	@Column(name = "account_name")
	private String accountName;
	
	@Column(name = "account_no")
	private String accountNo;

	@Column(name = "bank_branch")
	private String bankBranch;
	
	@Column(name = "note")
	private String note;
	
	@Column(name="account_type")
    @Enumerated(EnumType.STRING)
    private AccountType accountType = AccountType.BANK;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_party_holder")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Party holder;
	
	@OneToMany(mappedBy = "bankAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	private Set<PartyBankAccount> partys = new FastSet<PartyBankAccount>();

	@Override
	public String getAuditCode() {
		return this.getId() + "," + this.getCode();
	}
	
	@Override
	public Map<String, Object> val()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("bankId", getId());
		map.put("bankCode", getCode());
		map.put("bankName", getBankName());

		map.put("bankAccountNo", getAccountNo());
		map.put("bankAccountName", getAccountName());
		map.put("bankAcountHolder", getHolder());

		return map;
	}
}
