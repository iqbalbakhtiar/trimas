package com.siriuserp.sdk.dm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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
@Table(name = "core_tax_additional_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CoreTaxAdditionalInfo extends Model
{
	private static final long serialVersionUID = -9145920929008613182L;

	@Enumerated(EnumType.STRING)
    @Column(name = "trx_code")
    private TrxCode trxCode;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "facility_stamp_code")
	private String facilityStampCode;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "facility_stamp_description")
	private String facilityStampDescription;
	
	@Override
	public String getAuditCode() {
		return getId()+"";
	}
}
