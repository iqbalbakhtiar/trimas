/**
 * Nov 22, 2007 1:47:02 PM
 * com.siriuserp.tools.dao.impl
 * newsDaoImpl.java
 */
package com.siriuserp.tools.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Component;

import com.siriuserp.sdk.dao.NewsDao;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.dm.News;

/**
 * @author Ersi Agustin
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
public class NewsDaoImpl extends DaoHelper<News> implements NewsDao
{
	public News loadLatesNews()
	{
		Criteria criteria = getSession().createCriteria(News.class);
		criteria.setCacheable(true);
		criteria.setMaxResults(1);
		criteria.addOrder(Order.desc("date"));

		return (News)criteria.uniqueResult();
	}
}
