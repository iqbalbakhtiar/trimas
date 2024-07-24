/**
 * Oct 27, 2008 5:13:58 PM
 * com.siriuserp.sdk.dm
 * GeographicType.java
 */
package com.siriuserp.administration.dm;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import com.siriuserp.sdk.dm.Model;

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
@Table(name = "geographic_type")
public class GeographicType extends Model
{
	private static final long serialVersionUID = -6449414483358949930L;

	public static final Long COUNTRY = Long.valueOf(1);
	public static final Long PROVINCE = Long.valueOf(2);
	public static final Long CITY = Long.valueOf(3);
	public static final Long AREA = Long.valueOf(4);
	public static final Long DISTRICT = Long.valueOf(5);

	@Column(name = "name")
	private String name;

	@Column(name = "level")
	private Integer level = 1;

	@Column(name = "enabled", length = 1)
	@Type(type = "yes_no")
	private boolean enabled = true;

	@Column(name = "note")
	private String note;

	@OneToMany(mappedBy = "geographicType", fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	private Set<Geographic> geographics = new FastSet<Geographic>();

	public boolean isDeleteable()
	{
		if (!geographics.isEmpty())
			return false;

		return true;
	}

	@Override
	public String getAuditCode()
	{
		return this.getId().toString();
	}
}
