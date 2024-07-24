/**
 * Oct 27, 2008 5:12:25 PM
 * com.siriuserp.sdk.dm
 * Geographic.java
 */
package com.siriuserp.administration.dm;

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

import com.siriuserp.sdk.dm.JSONSupport;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.PostalAddress;

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
@Table(name = "geographic")
public class Geographic extends Model implements JSONSupport
{
	private static final long serialVersionUID = -7821403226955067886L;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_geographic_parent")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Geographic parent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_geographic_type")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private GeographicType geographicType;

	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	private Set<Geographic> childs = new FastSet<Geographic>();

	@OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	private Set<PostalAddress> postalAddress = new FastSet<PostalAddress>();

	@Override
	public Map<String, Object> val()
	{
		FastMap<String, Object> geo = new FastMap<String, Object>();
		geo.put("id", getId());
		geo.put("name", getName());

		return geo;
	}

	public Geographic getLastParent()
	{
		if (getParent() != null)
			return getParent().getLastParent();

		return this;
	}

	public boolean isDeleteable()
	{

		if (!childs.isEmpty())
			return false;

		if (!postalAddress.isEmpty())
			return false;

		return true;
	}

	@Override
	public String getAuditCode()
	{
		return getId() + "" + getCode();
	}
}
