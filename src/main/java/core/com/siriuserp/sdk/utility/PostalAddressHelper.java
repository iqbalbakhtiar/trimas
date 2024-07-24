/**
 * 
 */
package com.siriuserp.sdk.utility;

import com.siriuserp.sdk.dm.AddressType;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PostalAddress;
import com.siriuserp.sdk.dm.PostalAddressType;

/**
 * @author
 * Betsu Brahmana Restu
 * PT. Sirius Indonesia
 * betsu@siriuserp.com
 */

public class PostalAddressHelper
{
	public static PostalAddress getTaxAddress(Party party)
	{
		for (PostalAddress address : party.getPostalAddresses())
			for (PostalAddressType type : address.getAddressTypes())
				if (type.getType().equals(AddressType.TAX) && type.isEnabled() && address.isSelected())
					return address;

		return null;
	}

	public static PostalAddress getBillingAddress(Party party)
	{
		for (PostalAddress address : party.getPostalAddresses())
			for (PostalAddressType type : address.getAddressTypes())
				if (type.getType().equals(AddressType.OFFICE) && type.isEnabled() && address.isSelected())
					return address;

		return null;
	}
}
