package com.siriuserp.sdk.dm;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "buyer_country")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BuyerCountry extends Model
{
	private static final long serialVersionUID = -1471548319401219050L;

	@Column(name = "code")
	private String code;
	
	@Column(name = "code_description")
	private String description;

	public String getFullName()
	{
		return getCode() + " - " + getDescription();
	}
	
	@Override
	public String getAuditCode() {
		return getCode();
	}
}
