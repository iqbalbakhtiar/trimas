/**
 * Apr 20, 2006
 * Grid.java
 */
package com.siriuserp.sdk.dm;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import javolution.util.FastMap;
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
@Table(name = "grid")
public class Grid extends Model implements JSONSupport
{
	private static final long serialVersionUID = 704881348427388427L;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "note")
	private String note;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_facility")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Facility facility;

	@OneToMany(mappedBy = "grid", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	private Set<Container> containers = new HashSet<Container>();

	public boolean isDeletable()
	{
		if (getContainers().isEmpty())
			return true;

		return false;
	}

	@Override
	public Map<String, Object> val()
	{
		Map<String, Object> value = new FastMap<String, Object>();
		value.put("gridId", getId());
		value.put("gridName", getName());
		value.put("facility", getFacility());

		return value;
	}

	@Override
	public String getAuditCode()
	{
		return this.id + "," + this.code;
	}
}
