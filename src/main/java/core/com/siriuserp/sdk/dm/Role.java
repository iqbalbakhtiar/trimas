package com.siriuserp.sdk.dm;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import org.springframework.security.GrantedAuthority;

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
@Table(name = "access_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role extends Model implements GrantedAuthority
{
	private static final long serialVersionUID = -2709252389757433110L;

	@Column(name = "alias", nullable = true)
	private String alias;

	@Column(name = "role_name", nullable = false, unique = true)
	private String name;

	@Column(name = "role_id", nullable = false, unique = true)
	private String roleId;

	@Column(name = "mandatory")
	@Type(type = "yes_no")
	private boolean mandatory = false;

	@Column(name = "note")
	private String note;

	@Column(name = "level")
	@Enumerated(EnumType.STRING)
	private FlagLevel flagLevel = FlagLevel.USERLEVEL;

	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	private Set<User> users = new FastSet<User>();

	public static final synchronized Role newInstance(String name)
	{
		if (SiriusValidator.validateParam(name))
		{
			Role role = new Role();
			role.setName(name);

			return role;
		}

		return null;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Role))
			return false;

		Role role = (Role) obj;

		if (!SiriusValidator.validateParamWithZeroPosibility(getId()))
			return false;

		if (!SiriusValidator.validateParamWithZeroPosibility(role.getId()))
			return false;

		if (role.getId().equals(this.getId()))
			return true;

		return false;
	}

	@Override
	public int compareTo(Object o)
	{
		return 0;
	}

	@Override
	public String getAuthority()
	{
		return getName();
	}

	@Override
	public String getAuditCode()
	{
		return this.roleId + "," + this.name;
	}
}
