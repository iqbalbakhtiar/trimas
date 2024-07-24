/**
 * 
 */
package com.siriuserp.administration.form;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import com.siriuserp.administration.dm.Geographic;
import com.siriuserp.sdk.dm.ContactMechanism;
import com.siriuserp.sdk.dm.Form;
import com.siriuserp.sdk.dm.PostalAddressType;
import com.siriuserp.sdk.dm.User;

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

	private String salutation;
	private String firstName;
	private String middleName;
	private String lastName;
	private String taxType;
	private String nik;
	private String taxCode;
	private String accountNumber;
	private String address;
	private Long relationshipId;
	private String uri;

	private Date birthDate;
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
}
