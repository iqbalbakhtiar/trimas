/**
 * Oct 27, 2008 4:29:09 PM
 * com.siriuserp.sdk.dm
 * PartyRelationshipType.java
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
@Table(name = "party_relationship_type")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class PartyRelationshipType extends Model
{
	private static final long serialVersionUID = -7818797459311825862L;

	public static final Long ORGANIZATION_STRUCTURE_RELATIONSHIP = Long.valueOf(1);
	public static final Long EMPLOYMENT_RELATIONSHIP = Long.valueOf(2);
	public static final Long CUSTOMER_RELATIONSHIP = Long.valueOf(3);
	public static final Long SUPPLIER_RELATIONSHIP = Long.valueOf(4);
	public static final Long EXPEDITION_RELATIONSHIP = Long.valueOf(5);

	@Column(name = "name", nullable = false, unique = true, length = 150)
	private String name;

	@Column(name = "note", nullable = false, unique = true, length = 150)
	private String note;

	@Override
	public String getAuditCode()
	{
		return this.getId() + " " + this.getName();
	}
}
