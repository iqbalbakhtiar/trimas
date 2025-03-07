/**
 * Oct 29, 2008 3:11:34 PM
 * com.siriuserp.sdk.dao
 * PartyDao.java
 */
package com.siriuserp.sdk.dao;

import java.util.List;

import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.base.Filterable;
import com.siriuserp.sdk.dm.Party;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface PartyDao extends Dao<Party>, Filterable
{
	public Party load(String firstName, String middleName, String lastName);
	public Long loadByFullName(String firstName, String middleName, String lastName);
	public Party load(String name);
	 public Party load(Long id);
	public List<Party> loadAllNoUser();
}
