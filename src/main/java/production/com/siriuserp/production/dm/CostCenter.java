/**
 * Sep 20, 2006 11:47:20 AM
 * net.konsep.sirius.model
 * Container.java
 */
package com.siriuserp.production.dm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.siriuserp.sdk.dm.JSONSupport;
import com.siriuserp.sdk.dm.Model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Agung Dodi Perdana          
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Entity
@Getter
@Setter
@Table(name = "cost_center")
public class CostCenter extends Model implements JSONSupport
{
	private static final long serialVersionUID = 6859754333552997006L;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name="type")
	private CostCenterType type;

	@Column(name = "note")
	private String note;

	@Override
	public String getAuditCode() {
		return id + ',' + code;
	}

}
