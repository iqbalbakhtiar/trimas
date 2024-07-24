/**
 * May 22, 2006
 * CustomerDao.java
 */
package com.siriuserp.sdk.dao;

import java.util.List;

import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.base.Filterable;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PartyRelationship;



/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public interface CustomerDao extends Dao<Party>,Filterable
{
	public List<PartyRelationship> getSalesPerson(Long party);
}
