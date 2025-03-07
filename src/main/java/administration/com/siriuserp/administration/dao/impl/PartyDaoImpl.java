/**
 * Oct 29, 2008 3:12:08 PM
 * com.siriuserp.sdk.dao.impl
 * PartyDaoImpl.java
 */
package com.siriuserp.administration.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.siriuserp.sdk.dao.PartyDao;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
public class PartyDaoImpl extends DaoHelper<Party> implements PartyDao
{
	@Override
	public Party load(String firstName, String middleName, String lastName)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FROM Party party ");
		builder.append("WHERE party.fullName =:fullName ");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setReadOnly(true);
		query.setMaxResults(1);
		query.setParameter("fullName", firstName);

		return (Party) query.uniqueResult();
	}

	@Override
	public Long loadByFullName(String firstName, String middleName, String lastName)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT COUNT(party.id) FROM Party party ");
		builder.append("WHERE party.firstName =:firstName ");

		if (SiriusValidator.validateParam(middleName))
			builder.append("AND party.middleName =:middleName ");

		if (SiriusValidator.validateParam(lastName))
			builder.append("AND party.lastName =:lastName ");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setReadOnly(true);
		query.setMaxResults(1);
		query.setParameter("firstName", firstName);

		if (SiriusValidator.validateParam(middleName))
			query.setParameter("middleName", middleName);

		if (SiriusValidator.validateParam(lastName))
			query.setParameter("lastName", lastName);

		return (Long) query.uniqueResult();
	}

	@Override
	public Party load(String name)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT p FROM Party party ");
		builder.append("WHERE party.fullName LIKE :fullName ");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setReadOnly(true);
		query.setMaxResults(1);
		query.setParameter("fullName", "%" + name + "%");

		return (Party) query.list().get(0);
	}

	@SuppressWarnings("unchecked")
	public List<Party> loadAllNoUser()
	{
		Query query = getSession().createQuery("FROM Party p WHERE p.id not in(SELECT user.person.id FROM User user) ORDER BY p.fullName ASC");
		query.setCacheable(true);
		query.setReadOnly(true);

		return query.list();
	}
	
	public Party load(Long id) 
    {
        return (Party)getSession().load(Party.class,id);
    }
}
