package com.siriuserp.sdk.dm;

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
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastMap;
import javolution.util.FastSet;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Entity
@Table(name = "module")
public class Module extends Model
{
	private static final long serialVersionUID = 5897860431864259050L;

	@Column(name = "code", nullable = false)
	private String code;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "alias", nullable = false)
	private String alias;

	@Column(name = "menu_index")
	private Integer menuIndex;

	@Column(name = "default_uri", nullable = false, length = 150)
	private String defaultUri;

	@Column(name = "enabled")
	@Type(type = "yes_no")
	private boolean enabled;

	@Column(name = "mandatory")
	@Type(type = "yes_no")
	private boolean mandatory;

	@Column(name = "display_group")
	@Enumerated(EnumType.STRING)
	private DisplayGroup displayGroup;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_module_group")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private ModuleGroup moduleGroup;

	@OneToMany(mappedBy = "module", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<ModuleDetail> details = new FastSet<ModuleDetail>();
	
	@OneToMany(mappedBy = "module", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<AccessibleModule> access = new FastSet<AccessibleModule>();

	@Transient
	private String oldKey;
	
	@Transient
	private String newKey;
	
	public Module()
	{
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	public boolean isMandatory()
	{
		return mandatory;
	}

	public void setMandatory(boolean mandatory)
	{
		this.mandatory = mandatory;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getAlias()
	{
		return alias;
	}

	public void setAlias(String alias)
	{
		this.alias = alias;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getDefaultUri()
	{
		return defaultUri;
	}

	public void setDefaultUri(String defaultUri)
	{
		this.defaultUri = defaultUri;
	}

	public DisplayGroup getDisplayGroup()
	{
		return displayGroup;
	}

	public void setDisplayGroup(DisplayGroup displayGroup)
	{
		this.displayGroup = displayGroup;
	}

	public ModuleGroup getModuleGroup()
	{
		return moduleGroup;
	}

	public void setModuleGroup(ModuleGroup moduleGroup)
	{
		this.moduleGroup = moduleGroup;
	}

	public Set<ModuleDetail> getDetails()
	{
		return details;
	}

	public void setDetails(Set<ModuleDetail> details)
	{
		this.details = details;
	}

	public Integer getMenuIndex()
	{
		return menuIndex;
	}

	public void setMenuIndex(Integer menuIndex)
	{
		this.menuIndex = menuIndex;
	}
	
	public Set<AccessibleModule> getAccess()
	{
		return access;
	}

	public void setAccess(Set<AccessibleModule> access) 
	{
		this.access = access;
	}
	
	public String getOldKey() 
	{
		return oldKey;
	}

	public void setOldKey(String oldKey)
	{
		this.oldKey = oldKey;
	}

	public String getNewKey()
	{
		return newKey;
	}

	public void setNewKey(String newKey) 
	{
		this.newKey = newKey;
	}

	public static final Module newInstance(String id)
	{
		if (SiriusValidator.validateParamWithZeroPosibility(id))
		{
			Module module = new Module();
			module.setId(Long.valueOf(id));

			return module;
		}

		return null;
	}
	
	public FastMap<String, String> getMenus()
	{
		FastMap<String, String> map = new FastMap<String, String>();
		map.put("en", getName());
		map.put("id", getAlias());
		
		return map;
	}
	
	@Override
	public String getAuditCode()
	{
		return this.id + "," + this.name;
	}
}
