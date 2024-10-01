package com.siriuserp.sdk.dm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import com.siriuserp.accounting.dm.BankAccount;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "party_bank_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PartyBankAccount extends Model implements JSONSupport {

	private static final long serialVersionUID = -8696331402212105652L;
	
	@Column(name = "enabled")
	@Type(type = "yes_no")
	private boolean enabled = Boolean.TRUE;
	
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_party")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Party party;
	
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_bank_account")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private BankAccount bankAccount;

	@Override
	public String getAuditCode() {
		// TODO Auto-generated method stub
		return null;
	}

}
