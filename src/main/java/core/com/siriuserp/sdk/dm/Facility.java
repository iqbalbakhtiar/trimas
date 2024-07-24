/**
 * Oct 28, 2008 6:46:59 AM
 * com.siriuserp.sdk.dm
 * Facility.java
 */
package com.siriuserp.sdk.dm;

import java.util.HashMap;
import java.util.Map;
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
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonValue;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import com.siriuserp.sdk.utility.SiriusValidator;

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
@Table(name = "facility")
public class Facility extends Model implements JSONSupport
{
	private static final long serialVersionUID = 5553065939797193148L;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "legend")
	private String legend;

	@Column(name = "alias")
	private String alias;

	@Column(name = "note")
	private String note;

	@Enumerated(EnumType.STRING)
	@Column(name = "facility_implementation")
	private FacilityImplementation implementation = FacilityImplementation.REAL;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_facility_type")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private FacilityType facilityType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_party_owner")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party owner;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_postal_address")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private PostalAddress postalAddress;

	@OneToMany(mappedBy = "facility", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	private Set<Grid> grids = new FastSet<Grid>();

	@Override
	public String getAuditCode()
	{
		return getId() + "," + getCode();
	}

	@JsonValue
	public Map<String, Object> val()
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("facilityId", getId());
		map.put("facilityName", getName());
		map.put("facilityAlias", getAlias());
		map.put("facilityCode", getCode());
		map.put("facilityAddress", getPostalAddress() != null ? getPostalAddress().getAddress() : "");

		return map;
	}

	public static final synchronized Facility newInstance(String id)
	{
		if (SiriusValidator.validateParam(id))
		{
			Facility facility = new Facility();
			facility.setId(Long.valueOf(id));

			return facility;
		}

		return null;
	}

	public boolean isDeletable()
	{
		boolean delete = true;

		for (Grid grid : getGrids())
			if (!grid.isDeletable())
				delete = grid.isDeletable();

		return delete;
	}
}
