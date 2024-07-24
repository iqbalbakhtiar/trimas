/**
 * Mar 27, 2009 2:12:37 PM
 * com.siriuserp.administration.dao.impl
 * PartyRelationshipDaoImpl.java
 */
package com.siriuserp.administration.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.siriuserp.sdk.dao.PartyRelationshipDao;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.dm.PartyRelationship;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@SuppressWarnings("unchecked")
@Component
public class PartyRelationshipDaoImpl extends DaoHelper<PartyRelationship> implements PartyRelationshipDao
{
	@Override
	public PartyRelationship load(Long from, Long to, Long type)
	{
		Criteria criteria = getSession().createCriteria(PartyRelationship.class);
		criteria.createCriteria("partyFrom").add(Restrictions.eq("id", from));
		criteria.createCriteria("partyTo").add(Restrictions.eq("id", to));
		criteria.createCriteria("relationshipType").add(Restrictions.eq("id", type));
		criteria.addOrder(Order.desc("id"));

		List<PartyRelationship> list = criteria.list();
		if (!list.isEmpty())
			return list.get(0);

		return null;
	}

	@Override
	public List<PartyRelationship> load(Long to, Long type)
	{

		Criteria criteria = getSession().createCriteria(PartyRelationship.class);
		criteria.createCriteria("partyTo").add(Restrictions.eq("id", to));
		criteria.createCriteria("relationshipType").add(Restrictions.eq("id", type));
		criteria.addOrder(Order.desc("id"));

		List<PartyRelationship> list = criteria.list();
		return list;
	}
}
