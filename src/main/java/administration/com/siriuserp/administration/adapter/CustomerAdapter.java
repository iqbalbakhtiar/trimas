package com.siriuserp.administration.adapter;

import com.siriuserp.sdk.adapter.AbstractUIAdapter;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PartyRelationship;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Betsu Brahmana Restu
 * Sirius Indonesia, PT
 * betsu@siriuserp.com
 * 
 * Version 1.5
 */

@Getter
@Setter
public class CustomerAdapter extends AbstractUIAdapter
{
	private static final long serialVersionUID = 1360265210213953772L;

	private Party customer;
	private PartyRelationship relationship;
	
	public CustomerAdapter(Party customer, PartyRelationship relationship) {
		this.customer = customer;
		this.relationship = relationship;
	}

	public CustomerAdapter(Party customer) {
		this.customer = customer;
	}
	
	

}
