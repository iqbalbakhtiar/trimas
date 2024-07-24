package com.siriuserp.sdk.dm;

import java.util.HashSet;
import java.util.List;
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
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

import com.siriuserp.sdk.utility.SiriusValidator;

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
@Table(name = "user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User extends Model implements UserDetails, UserToken
{
	private static final long serialVersionUID = 5408426548524466519L;

	@Column(name = "user_name")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "note")
	private String note;

	@Column(name = "level")
	@Enumerated(EnumType.STRING)
	private FlagLevel flagLevel;

	@Column(name = "enabled", length = 1)
	@Type(type = "yes_no")
	private boolean enabled;

	@Column(name = "account_expired", length = 1)
	@Type(type = "yes_no")
	protected boolean accountExpired;

	@Column(name = "account_locked", length = 1)
	@Type(type = "yes_no")
	protected boolean accountLocked;

	@Column(name = "credentials_expired", length = 1)
	@Type(type = "yes_no")
	protected boolean credentialsExpired;

	@Column(name = "super_visor_status", length = 1)
	@Type(type = "yes_no")
	protected boolean superVisorStatus = false;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_person")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party person;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_profile")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Profile profile;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_role")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Role role;

	@Transient
	private Map<String, UrlCache> urls = new FastMap<String, UrlCache>();

	@Transient
	private Map<String, String> menus = new FastMap<String, String>();

	@Transient
	private Map<String, List<Long>> access = new FastMap<String, List<Long>>();

	public GrantedAuthority[] getAuthorities()
	{
		Set<Role> authorities = new HashSet<Role>();
		authorities.add(getRole());

		return authorities.toArray(new GrantedAuthority[0]);
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (!(o instanceof User))
			return false;

		final User user = (User) o;

		if (username != null ? !username.equals(user.getUsername()) : user.getUsername() != null)
			return false;

		return true;
	}

	public int hashCode()
	{
		return (username != null ? username.hashCode() : 0);
	}

	public static final User newInstance(String id)
	{
		if (SiriusValidator.validateParam(id))
		{
			User user = new User();
			user.setId(Long.valueOf(id));
			return user;
		}

		return null;
	}

	@Override
	public String getAuditCode()
	{
		return this.id + "," + this.username;
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return !this.accountExpired;
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return !this.accountLocked;
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return !this.credentialsExpired;
	}

	public Set<String> getRoles()
	{
		Set<String> roles = new HashSet<String>();
		roles.add(getRole().getName());

		return roles;
	}
}
