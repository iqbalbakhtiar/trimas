/**
 * Oct 27, 2008 4:23:30 PM
 * com.siriuserp.sdk.dm
 * PartyRelationship.java
 */
package com.siriuserp.sdk.dm;

import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
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
@Table(name = "party_relationship")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PartyRelationship extends Model implements JSONSupport
{
	private static final long serialVersionUID = 1951759300167064331L;

	@Column(name = "from_date")
	private Date fromDate;

	@Column(name = "to_date")
	private Date toDate;

	@Column(name = "active")
	@Type(type = "yes_no")
	private boolean active = Boolean.TRUE;

	@Column(name = "note")
	private String note;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_party_from")
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@Fetch(FetchMode.SELECT)
	private Party partyFrom;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_party_role_type_from")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private PartyRoleType partyRoleTypeFrom;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_party_to")
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@Fetch(FetchMode.SELECT)
	private Party partyTo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_party_role_type_to")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private PartyRoleType partyRoleTypeTo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_party_relationship_type")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private PartyRelationshipType relationshipType;

	@Override
	public String getAuditCode()
	{
		return "";
	}

	@Override
	public Map<String, Object> val()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("relaltionId", getId());
		map.put("partyFrom", getPartyFrom());
		map.put("partyTo", getPartyTo());

		return map;
	}
}
