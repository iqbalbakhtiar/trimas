/**
 * May 31, 2006
 * CustomerSequenceDao.java
 */
package com.siriuserp.sdk.dao;

import java.util.Date;

import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.dm.CodeSequence;
import com.siriuserp.sdk.dm.TableType;


/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public interface CodeSequenceDao extends Dao<CodeSequence>
{
	public CodeSequence load(Date date, String company, TableType tableType, Long sequence);
}
