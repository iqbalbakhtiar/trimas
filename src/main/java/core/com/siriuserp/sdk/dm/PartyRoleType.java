/**
 * Oct 27, 2008 1:55:36 PM
 * com.siriuserp.sdk.dm
 * PartyRoleType.java
 */
package com.siriuserp.sdk.dm;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonValue;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

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
@Table(name = "party_role_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PartyRoleType extends Model implements JSONSupport
{
	private static final long serialVersionUID = -1924897387200100071L;

	public static final Long HOLDING = Long.valueOf(1);
	public static final Long COMPANY = Long.valueOf(2);
	public static final Long FACILITY = Long.valueOf(3);
	public static final Long EMPLOYEE = Long.valueOf(4);
	public static final Long CUSTOMER = Long.valueOf(5);
	public static final Long SUPPLIER = Long.valueOf(6);
	public static final Long DRIVER = Long.valueOf(7);
	public static final Long SALES_PERSON = Long.valueOf(8);

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "active", length = 1)
	@Type(type = "yes_no")
	private boolean active = true;

	@Enumerated(EnumType.STRING)
	@Column(name = "role_type_group")
	private PartyRoleTypeGroup roleTypeGroup;

	@Column(name = "note", nullable = false)
	private String note;

	@Override
	public String getAuditCode()
	{
		return this.id + " " + this.getName();
	}

	@JsonValue
	public Map<String, Object> val()
	{
		Map<String, Object> map = new FastMap<String, Object>();
		map.put("id", getId());
		map.put("name", getName());

		return map;
	}
}
