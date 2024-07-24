/**
 * Mar 27, 2009 2:11:49 PM
 * com.siriuserp.sdk.dao
 * PartyRelationshipDao.java
 */
package com.siriuserp.sdk.dao;

import java.util.List;

import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.base.Filterable;
import com.siriuserp.sdk.dm.PartyRelationship;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface PartyRelationshipDao extends Dao<PartyRelationship>, Filterable
{
	public PartyRelationship load(Long from, Long to, Long type);
	public List<PartyRelationship> load(Long to, Long type);
}
