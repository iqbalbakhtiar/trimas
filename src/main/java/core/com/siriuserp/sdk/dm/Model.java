/**
 * Nov 27, 2007 4:01:54 PM
 * net.konsep.sirius.model
 * Model.java
 */
package com.siriuserp.sdk.dm;

import java.util.Map;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

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
@MappedSuperclass
public abstract class Model extends SiriusModel implements JSONSupport
{
	private static final long serialVersionUID = -4565748510918733688L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_person_created_by")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	protected Party createdBy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_person_updated_by")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	protected Party updatedBy;

	@Transient
	private Form form;

	public abstract String getAuditCode();

	@Override
	public String getSelf()
	{
		return getClass().getSimpleName();
	}

	@Override
	public Map<String, Object> val()
	{

		Map<String, Object> map = new FastMap<String, Object>();

		map.put("id", getId());
		map.put("self", this.getClass().getSimpleName());

		return map;
	}
}
