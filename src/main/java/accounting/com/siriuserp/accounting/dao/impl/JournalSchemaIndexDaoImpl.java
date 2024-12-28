/**
 * Nov 12, 2008 4:34:45 PM
 * com.siriuserp.sdk.dao.impl
 * JournalSchemaIndexDaoImpl.java
 */
package com.siriuserp.accounting.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.siriuserp.accounting.dao.JournalSchemaIndexDao;
import com.siriuserp.accounting.dm.JournalSchemaIndex;
import com.siriuserp.sdk.db.DaoHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
public class JournalSchemaIndexDaoImpl extends DaoHelper<JournalSchemaIndex> implements JournalSchemaIndexDao
{
	@Override
	public JournalSchemaIndex loadByParentAndType(Long parent, Long type)
	{
		Criteria criteria = getSession().createCriteria(JournalSchemaIndex.class);
		criteria.createCriteria("journalSchema").add(Restrictions.eq("id", parent));
		criteria.createCriteria("index").add(Restrictions.eq("id", type));

		Object object = criteria.uniqueResult();
		if (object != null)
			return (JournalSchemaIndex) object;

		return null;
	}
}
