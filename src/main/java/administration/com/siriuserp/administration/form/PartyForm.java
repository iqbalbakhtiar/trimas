/**
 * 
 */
package com.siriuserp.administration.form;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import com.siriuserp.administration.dm.Geographic;
import com.siriuserp.sdk.dm.*;

import lombok.Getter;
import lombok.Setter;

/**
 * @author
 * Betsu Brahmana Restu
 * PT. Sirius Indonesia
 * betsu@siriuserp.com
 */

@Getter
@Setter
public class PartyForm extends Form
{
	private static final long serialVersionUID = -3386654104212099282L;
	
	// For Party
	private String salutation;
	private String fullName;
	private String initial;
	private String nik;
	private String taxCode;
	private String permitCode;
	private String picture;

	private Long partyRoleTypeFrom;
	private Long id;
	
	private Date birthDate;
	
	private boolean active;
	private boolean base;
	
	// Other
	private String taxType;
	private String accountNumber;
	private String address;
	private String addressName;
	private String uri;
	private String postalCode;
	
	private Long relationshipId;

	private Date joinDate;
	private Date startDate;

	private Timestamp validFrom;
	private Timestamp validTo;

	private boolean taxStatus;
	private boolean activeStatus = true;
	private boolean enabled;
	private boolean selected;

	private User user;

	private Geographic geographic;
	private Geographic city;
	private ContactMechanism contactMechanism;

	private Set<PostalAddressType> addressTypes;
	private Set<PostalAddress> postalAddresses;
	private Set<ContactMechanism> contactMechanisms;

	private PartyRelationship partyRelationship;
}
