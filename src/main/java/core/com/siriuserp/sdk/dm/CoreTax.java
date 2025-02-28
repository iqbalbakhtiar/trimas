package com.siriuserp.sdk.dm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ferdinand
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "core_tax")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CoreTax extends Model
{
	private static final long serialVersionUID = 4549210020181466086L;

	@Column(name="editable")
    @Type(type="yes_no")
    protected boolean editable = false;
	
	@Column(name="type_tin")
    @Type(type="yes_no")
    protected boolean type = false;
	
	@Column(name = "nik")
	private String nik;
	
	@Column(name = "npwp")
	private String npwp;
	
	@Column(name = "nitku")
	private String nitku;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "trx_code")
    private TrxCode trxCode;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_buyer_country")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private BuyerCountry buyerCountry;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_core_tax_additional_info")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private CoreTaxAdditionalInfo additionalInfo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_party")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party party;
	
	@Override
	public String getAuditCode() {
		return getId()+"";
	}
}
