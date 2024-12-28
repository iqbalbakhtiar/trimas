/**
 * Feb 2, 2009 3:08:57 PM
 * com.siriuserp.accounting.query
 * JournalEntryGridViewQuery.java
 */
package com.siriuserp.accounting.query;

import org.hibernate.Query;

import com.siriuserp.accounting.criteria.JournalEntryFilterCriteria;
import com.siriuserp.accounting.dm.EntrySourceType;
import com.siriuserp.accounting.dm.JournalEntryType;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastList;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public class JournalEntryComboGridViewQuery extends AbstractGridViewQuery
{
	@Override
	public Long count()
	{
		JournalEntryFilterCriteria criteria = (JournalEntryFilterCriteria) getFilterCriteria();

		Query query = getQuery(criteria, ExecutorType.COUNT);
		Object object = query.uniqueResult();
		if (object != null)
			return (Long) object;

		return Long.valueOf(0);
	}

	@Override
	public Object execute()
	{
		JournalEntryFilterCriteria criteria = (JournalEntryFilterCriteria) getFilterCriteria();

		Query query = getQuery(criteria, ExecutorType.HQL);
		query.setFirstResult(criteria.start());
		query.setMaxResults(criteria.getMax());

		return query.list();
	}

	public Query getQuery(JournalEntryFilterCriteria criteria, ExecutorType type)
	{
		StringBuilder builder = new StringBuilder();

		if (type.compareTo(ExecutorType.COUNT) == 0)
			builder.append("SELECT COUNT(entry.id) ");

		builder.append("FROM JournalEntry entry WHERE entry.id IS NOT NULL ");

		if (SiriusValidator.validateParam(criteria.getCode()))
			builder.append("AND entry.code LIKE '%" + criteria.getCode() + "%' ");

		if (SiriusValidator.validateParam(criteria.getName()))
			builder.append("AND entry.name LIKE '%" + criteria.getName() + "%' ");

		if (SiriusValidator.validateParam(criteria.getEntryType()))
			builder.append("AND entry.entryType =:entryType ");

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
			builder.append("AND entry.organization.id = :filteredorg ");

		if (SiriusValidator.validateParam(criteria.getEntrySourceType()))
		{
			if (criteria.getEntrySourceType().equals(EntrySourceType.STANDARD.toString()))
				builder.append("AND entry.entrySourceType IN(:entrySourceType) ");
			else
				builder.append("AND entry.entrySourceType =:entrySourceType ");
		}

		if (criteria.getEntryStatus() != null)
			builder.append("AND entry.entryStatus =:status ");

		if (criteria.getDateFrom() != null)
		{
			if (criteria.getDateTo() != null)
				builder.append("AND entry.entryDate BETWEEN :startDate AND :endDate ");
			else
				builder.append("AND entry.entryDate >= :startDate ");
		} else if (criteria.getDateTo() != null)
			builder.append("AND entry.entryDate <= :endDate ");

		builder.append("ORDER BY entry.entryDate DESC, entry.code DESC");

		Query query = getSession().createQuery(builder.toString());

		if (SiriusValidator.validateParam(criteria.getEntryType()))
			query.setParameter("entryType", JournalEntryType.valueOf(criteria.getEntryType()));

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
			query.setParameter("filteredorg", criteria.getOrganization());

		if (SiriusValidator.validateParam(criteria.getEntrySourceType()))
		{
			if (criteria.getEntrySourceType().equals(EntrySourceType.STANDARD.toString()))
			{
				FastList<EntrySourceType> sourceType = new FastList<EntrySourceType>();
				sourceType.add(EntrySourceType.AUTOAJUSTMENT);
				//sourceType.add(EntrySourceType.AUTOMATIC);
				sourceType.add(EntrySourceType.CLOSING);
				sourceType.add(EntrySourceType.OPENING);
				sourceType.add(EntrySourceType.STANDARD);

				query.setParameterList("entrySourceType", sourceType);
			} else
				query.setParameter("entrySourceType", EntrySourceType.valueOf(criteria.getEntrySourceType()));
		}

		if (criteria.getEntryStatus() != null)
			query.setParameter("status", criteria.getEntryStatus());

		if (criteria.getDateFrom() != null)
			query.setParameter("startDate", criteria.getDateFrom());

		if (criteria.getDateTo() != null)
			query.setParameter("endDate", criteria.getDateTo());

		return query;
	}
}
