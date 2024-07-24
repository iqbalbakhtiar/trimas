/**
 * Feb 13, 2009 3:21:07 PM
 * com.siriuserp.sdk.dm
 * PostalAddressType.java
 */
package com.siriuserp.sdk.dm;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonValue;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

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
@Table(name = "postal_address_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PostalAddressType extends Model implements JSONSupport
{
	private static final long serialVersionUID = 2794498168509994116L;

	@Column(name = "enabled")
	@Type(type = "yes_no")
	private boolean enabled = true;

	@Column(name = "address_type")
	@Enumerated(EnumType.STRING)
	private AddressType type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_postal_address")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private PostalAddress postalAddress;

	@Override
	public String getAuditCode()
	{
		return this.getId().toString();
	}

	@JsonValue
	public Map<String, Object> val()
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("enabled", this.enabled);
		map.put("type", this.type);

		return map;
	}
}
