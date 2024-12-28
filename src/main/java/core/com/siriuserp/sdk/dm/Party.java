/**
 * Aug 17, 2008 11:01:12 AM
 * com.siriuserp.model
 * Party.java
 */
package com.siriuserp.sdk.dm;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonValue;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import com.siriuserp.accounting.dm.BankAccount;

import javolution.util.FastMap;
import javolution.util.FastSet;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Getter
@Setter
@Entity
@Table(name = "party")
@Inheritance(strategy = InheritanceType.JOINED)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Party extends Model implements JSONSupport
{
	private static final long serialVersionUID = 3842499659117487905L;

	@Formula(value = "CONCAT_WS(' ', NULLIF(salutation, ''), NULLIF(full_name, ''))")
	private String printedName;

	@Column(name = "code")
	private String code;

	@Column(name = "salutation")
	private String salutation;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "initial")
	protected String initial;

	@Column(name = "nik", length = 50)
	private String nik;

	@Column(name = "tax_code", length = 50)
	private String taxCode;
	
	@Column(name = "permit_code", length = 50)
	private String permitCode;

	@Column(name = "birth_date")
	private Date birthDate;

	@Column(name = "active")
	@Type(type = "yes_no")
	private boolean active = Boolean.TRUE;
	
	// Untuk membedakan Customer Group atau bukan
	// True = Group & False = Non Group
	@Column(name = "base")
	@Type(type = "yes_no")
	private boolean base = Boolean.FALSE;

	@Column(name = "level")
	@Enumerated(EnumType.STRING)
	protected FlagLevel flagLevel = FlagLevel.USERLEVEL;

	@Column(name = "picture")
	private String picture;

	@Column(name = "note")
	private String note;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_party_group")
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@Fetch(FetchMode.SELECT)
	private Party partyGroup; // Relasi Customer dengan Customer Group
	
	@OneToOne(mappedBy = "party", fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private PaymentMethod paymentMethod;
	
	@OneToMany(mappedBy = "partyGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("id")
	protected Set<Party> partyGroups = new FastSet<Party>();

	@OneToMany(mappedBy = "partyFrom", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("id")
	protected Set<PartyRelationship> relationships = new FastSet<PartyRelationship>();

	@OneToMany(mappedBy = "party", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("id")
	private Set<PostalAddress> postalAddresses = new FastSet<PostalAddress>();

	@OneToMany(mappedBy = "party", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("id")
	private Set<ContactMechanism> contactMechanisms = new FastSet<ContactMechanism>();
	
	@OneToMany(mappedBy = "holder", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("id")
	private Set<BankAccount> bankAccounts = new FastSet<BankAccount>();
	
	@OneToMany(mappedBy = "party", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("id")
	private Set<PartyBankAccount> partyBankAccounts = new FastSet<PartyBankAccount>();

	@Transient
	private Party organization;

	@Override
	public String getAuditCode()
	{
		return this.id + "," + this.code;
	}

	@JsonValue
	public Map<String, Object> val()
	{
		Map<String, Object> map = new FastMap<String, Object>();
		map.put("partyId", getId());
		map.put("salutation", getSalutation());
		map.put("partyCode", getCode());
		map.put("partyName", getFullName());
		map.put("nik", getNik());
		map.put("taxCode", getTaxCode());
		map.put("birthDate", getBirthDate());
		map.put("partyAddresses", getPostalAddresses());
		map.put("contactMechanises", getContactMechanisms());
		map.put("note", getNote());
		map.put("active", isActive());
		map.put("permitCode", getPermitCode());

		return map;
	}
}
