package com.siriuserp.sdk.dm;

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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import javolution.util.FastSet;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Entity
@Table(name = "module_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ModuleGroup extends Model
{
	private static final long serialVersionUID = 4776241530939743420L;

	@Column(name = "code", nullable = false, length = 5)
	private String code;
	
	@Column(name = "name", nullable = false, length = 50)
	private String name;

	@Column(name = "menu_index")
	private int menuIndex = 0;

	@Column(name = "note")
	private String note;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_module_group_parent")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private ModuleGroup parent;

	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	private Set<ModuleGroup> members = new FastSet<ModuleGroup>();

	@OneToMany(mappedBy = "moduleGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@OrderBy("code")
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	private Set<Module> modules = new FastSet<Module>();

	public ModuleGroup() {}

	public String getCode() 
	{
		return code;
	}

	public void setCode(String code) 
	{
		this.code = code;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getMenuIndex()
	{
		return menuIndex;
	}

	public void setMenuIndex(int menuIndex)
	{
		this.menuIndex = menuIndex;
	}

	public String getNote()
	{
		return note;
	}

	public void setNote(String note)
	{
		this.note = note;
	}

	public Set<Module> getModules()
	{
		return modules;
	}

	public void setModules(Set<Module> modules)
	{
		this.modules = modules;
	}

	public ModuleGroup getParent()
	{
		return parent;
	}

	public void setParent(ModuleGroup parent)
	{
		this.parent = parent;
	}

	public Set<ModuleGroup> getMembers()
	{
		return members;
	}

	public void setMembers(Set<ModuleGroup> members)
	{
		this.members = members;
	}

	public String getFullName()
	{
		StringBuilder builder = new StringBuilder();

		if (getParent() != null)
			builder.append("G " + getParent().getName());
		else
			builder.append("G Module Group");

		builder.append(" > ");
		builder.append("G " + getName());

		return builder.toString();
	}

	@Override
	public String getAuditCode()
	{
		return this.id + "," + this.name;
	}
}