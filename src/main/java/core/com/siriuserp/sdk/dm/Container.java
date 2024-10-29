/**
 * Sep 20, 2006 11:47:20 AM
 * net.konsep.sirius.model
 * Container.java
 */
package com.siriuserp.sdk.dm;

import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import com.siriuserp.inventory.dm.InventoryItem;
import com.siriuserp.sdk.utility.SiriusValidator;

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
@Table(name = "container")
public class Container extends Model implements JSONSupport
{
	private static final long serialVersionUID = 6859754333552997006L;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "note")
	private String note;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_grid")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Grid grid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_container_type")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private ContainerType containerType;
	
	@OneToMany(mappedBy = "container", fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	private Set<InventoryItem> items = new FastSet<InventoryItem>();

	@Transient
	private Long facilityId;

	public static final synchronized Container newInstance(String id)
	{
		if (SiriusValidator.validateParamWithZeroPosibility(id))
		{
			Container container = new Container();
			container.setId(Long.valueOf(id));
			return container;
		}

		return null;
	}

	@Override
	public String getAuditCode()
	{
		return this.id + "," + this.code;
	}

	@Override
	public Map<String, Object> val()
	{
		Map<String, Object> value = new FastMap<String, Object>();
		value.put("containerId", getId());
		value.put("containerName", getName());
		value.put("grid", getGrid());
		value.put("facility", getGrid().getFacility());

		return value;
	}
}
