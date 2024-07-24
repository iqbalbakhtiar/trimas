/**
 * Oct 2, 2009 9:50:40 AM
 * com.siriuserp.tools.query
 * NewsGridViewQuery.java
 */
package com.siriuserp.tools.query;

import org.hibernate.Query;

import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.utility.SiriusValidator;
import com.siriuserp.tools.criteria.NewsFilterCriteria;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public class NewsGridViewQuery extends AbstractGridViewQuery
{
	private Query getQuery(String qry)
	{
		NewsFilterCriteria criteria = (NewsFilterCriteria) getFilterCriteria();
		StringBuilder builder = new StringBuilder(qry);

		if (SiriusValidator.validateParam(criteria.getTitle()))
			builder.append("AND news.title LIKE :title ");

		if (SiriusValidator.validateParam(criteria.getPostBy()))
		{
			builder.append("AND (news.createdBy.firstName LIKE :name ");
			builder.append("OR news.createdBy.middleName LIKE :name ");
			builder.append("OR news.createdBy.lastName LIKE :name) ");
		}

		if (criteria.getDateFrom() != null)
		{
			if (criteria.getDateTo() != null)
				builder.append("AND news.date BETWEEN :dateFrom AND :dateTo ");
			else
				builder.append("AND news.date >= :dateFrom ");
		}

		if (criteria.getDateTo() != null)
			builder.append("AND news.date <= :dateTo ");

		builder.append("ORDER BY news.id DESC");

		Query query = getSession().createQuery(builder.toString());

		if (SiriusValidator.validateParam(criteria.getTitle()))
			query.setParameter("title", "%" + criteria.getTitle() + "%");

		if (SiriusValidator.validateParam(criteria.getPostBy()))
			query.setParameter("name", "%" + criteria.getPostBy() + "%");

		if (criteria.getDateFrom() != null)
		{
			if (criteria.getDateTo() != null)
			{
				query.setParameter("dateFrom", criteria.getDateFrom());
				query.setParameter("dateTo", criteria.getDateTo());
			} else
				query.setParameter("dateFrom", criteria.getDateFrom());
		}

		if (criteria.getDateTo() != null)
			query.setParameter("dateTo", criteria.getDateTo());

		return query;
	}

	@Override
	public Long count()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT COUNT(news.id) FROM News news ");
		builder.append("WHERE news.id IS NOT NULL ");

		Query query = getQuery(builder.toString());
		Object object = query.uniqueResult();
		if (object != null)
			return (Long) object;

		return Long.valueOf(0);
	}

	@Override
	public Object execute()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FROM News news ");
		builder.append("WHERE news.id IS NOT NULL ");

		Query query = getQuery(builder.toString());
		query.setFirstResult(filterCriteria.start());
		query.setMaxResults(filterCriteria.getMax());

		return query.list();
	}
}