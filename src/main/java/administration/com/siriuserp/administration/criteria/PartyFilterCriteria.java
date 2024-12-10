/**
 * Nov 3, 2008 9:59:36 AM
 * com.siriuserp.administration.dto.filter
 * PartyFilterCriteria.java
 */
package com.siriuserp.administration.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;

import lombok.Setter;

import lombok.Getter;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Getter
@Setter
public class PartyFilterCriteria extends AbstractFilterCriteria
{
	private static final long serialVersionUID = 1904181746608354021L;

	private String organizationName;
	private String code;
	private String name;
	private String created;
	private String status;
	private String partyFor;
	private String source;

	private Long partyRole;
	private Long charge;
	private Long fromRoleType;
	private Long toRoleType;
	private Long toParty;
	private Long relationshipType;
	private Long except;
	private Long responsibility;

	private Boolean base;
}
