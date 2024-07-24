/**
 * Apr 17, 2006
 * FacilityType.java
 */
package com.siriuserp.sdk.dm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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
@Table(name = "facility_type")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class FacilityType extends Model
{
	private static final long serialVersionUID = 5360952770260039795L;

	@Column(name = "name")
	private String name;

	@Column(name = "note")
	private String note;

	@Override
	public String getAuditCode()
	{
		return getId() + "," + getName();
	}
}
