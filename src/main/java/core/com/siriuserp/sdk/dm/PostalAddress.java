/**
 * Oct 27, 2008 5:18:21 PM
 * com.siriuserp.sdk.dm
 * PostalAddress.java
 */
package com.siriuserp.sdk.dm;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonValue;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import com.siriuserp.administration.dm.Geographic;

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
@Table(name = "postal_address")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PostalAddress extends Model implements JSONSupport
{
	private static final long serialVersionUID = 1199008070487313832L;

	@Column(name = "address")
	private String address;
	
	@Column(name = "address_name")
	private String addressName;
	
	@Column(name = "postal_code")
	private String postalCode;

	@Column(name = "active")
	@Type(type = "yes_no")
	private boolean enabled;

	@Column(name = "selected")
	@Type(type = "yes_no")
	private boolean selected;

	@Column(name = "note")
	private String note;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_geographic_city")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Geographic city;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_party")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party party;

	@OneToMany(mappedBy = "postalAddress", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("type")
	private Set<PostalAddressType> addressTypes = new FastSet<PostalAddressType>();

	public Geographic getCountry()
	{
		if (getProvince() != null)
			return getProvince().getParent();

		return null;
	}

	public Geographic getProvince()
	{
		if (getCity() != null)
			return getCity().getParent();

		return null;
	}

	@Override
	public String getAuditCode()
	{
		return this.id + ",";
	}

	@JsonValue
	public Map<String, Object> val()
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("postalId", getId());
		map.put("postalAddress", getAddress());
		map.put("postalTypes", getAddressTypes());
		map.put("postalCity", getCity());
		map.put("isDefault", isSelected());

		return map;
	}
}
