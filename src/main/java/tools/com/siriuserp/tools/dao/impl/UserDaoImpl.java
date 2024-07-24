package com.siriuserp.tools.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.siriuserp.sdk.dao.UserDao;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.dm.User;

/**
 * @author Ronny Mailindra
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@SuppressWarnings("unchecked")
@Component
public class UserDaoImpl extends DaoHelper<User> implements UserDao
{
	public User loadByName(String username)
	{
		Query query = getSession().createQuery("FROM User u where u.username=?");
		query.setCacheable(true);
		query.setReadOnly(true);
		query.setString(0, username);

		return (User) query.uniqueResult();
	}

	public User getUniqueUser(String userName)
	{
		Criteria criteria = getSession().createCriteria(User.class);
		criteria.setCacheable(true);
		criteria.add(Restrictions.eq("username", userName));

		return (User) criteria.uniqueResult();
	}

	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException
	{
		return loadByName(userName);
	}

	public void refreshUser(User user)
	{
		getSession().refresh(user);
	}

	@Override
	public List<User> loadSupervisor(boolean status)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FROM User u WHERE u.superVisorStatus =:status ");
		builder.append("ORDER BY u.person.firstName ASC");

		Query query = getSession().createQuery(builder.toString());
		query.setParameter("status", status);

		return query.list();
	}
}