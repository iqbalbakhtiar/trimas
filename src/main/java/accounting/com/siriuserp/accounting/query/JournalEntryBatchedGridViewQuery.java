/**
 * Oct 8, 2009 11:33:18 AM
 * com.siriuserp.accounting.query
 * JournalEntryBatchedGridViewQuery.java
 */
package com.siriuserp.accounting.query;

import org.hibernate.Query;

import com.siriuserp.accounting.criteria.JournalEntryFilterCriteria;
import com.siriuserp.accounting.dm.EntrySourceType;
import com.siriuserp.accounting.dm.JournalEntry;
import com.siriuserp.accounting.dm.JournalEntryStatus;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastList;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public class JournalEntryBatchedGridViewQuery extends AbstractGridViewQuery
{
	@Override
	public Long count()
	{
		if (!getAccessibleOrganizations().isEmpty())
		{
			JournalEntryFilterCriteria criteria = (JournalEntryFilterCriteria) getFilterCriteria();

			StringBuilder builder = new StringBuilder();
			builder.append("SELECT COUNT(DISTINCT entry) FROM JournalEntry entry WHERE entry.organization.id in(:orgs)");
			builder.append(" AND entry.entryStatus =:status ");

			if (SiriusValidator.validateParam(criteria.getCode()))
				builder.append(" AND entry.code like :code ");

			if (SiriusValidator.validateParam(criteria.getName()))
				builder.append(" AND entry.name like :name ");

			if (SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
				builder.append(" AND entry.organization.id =:org ");

			if (SiriusValidator.validateParam(criteria.getCreatedBy()))
			{
				builder.append("AND (entry.createdBy.firstName like :createdBy ");
				builder.append("OR entry.createdBy.middleName like :createdBy ");
				builder.append("OR entry.createdBy.lastName like :createdBy) ");
			}

			if (SiriusValidator.validateParam(criteria.getPeriod()))
			{
				builder.append("AND (entry.accountingPeriod.code like :period ");
				builder.append("OR entry.accountingPeriod.name like :period) ");
			}

			if (criteria.getDateFrom() != null)
			{
				if (criteria.getDateTo() != null)
					builder.append(" AND entry.entryDate BETWEEN :startDate AND :endDate");
				else
					builder.append(" AND entry.entryDate >= :startDate");
			}

			if (criteria.getDateTo() != null)
				builder.append(" AND entry.entryDate <= :endDate");

			if (SiriusValidator.validateParam(criteria.getEntryType()))
			{
				if (criteria.getEntryType().equalsIgnoreCase("standard"))
					builder.append(" AND (entry.entrySourceType = :type OR entry.entrySourceType = 'SCHEMATIC') ");
				else
					builder.append(" AND entry.entrySourceType = :type ");
			}

			Query query = getSession().createQuery(builder.toString());
			query.setCacheable(true);
			query.setReadOnly(true);
			query.setParameterList("orgs", getAccessibleOrganizations());
			query.setParameter("status", JournalEntryStatus.BATCHED);

			if (SiriusValidator.validateParam(criteria.getCode()))
				query.setParameter("code", "%" + criteria.getCode() + "%");

			if (SiriusValidator.validateParam(criteria.getName()))
				query.setParameter("name", "%" + criteria.getName() + "%");

			if (SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
				query.setParameter("org", criteria.getOrganization());

			if (SiriusValidator.validateParam(criteria.getCreatedBy()))
				query.setParameter("createdBy", "%" + criteria.getCreatedBy() + "%");

			if (SiriusValidator.validateParam(criteria.getPeriod()))
				query.setParameter("period", "%" + criteria.getPeriod() + "%");

			if (criteria.getDateFrom() != null)
				query.setParameter("startDate", criteria.getDateFrom());

			if (criteria.getDateTo() != null)
				query.setParameter("endDate", criteria.getDateTo());

			if (SiriusValidator.validateParam(criteria.getEntryType()))
				query.setParameter("type", EntrySourceType.valueOf(criteria.getEntryType()));

			Object object = query.uniqueResult();
			if (object != null)
				return (Long) object;
		}

		return Long.valueOf(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object execute()
	{
		FastList<JournalEntry> list = new FastList<JournalEntry>();

		if (!getAccessibleOrganizations().isEmpty())
		{
			JournalEntryFilterCriteria criteria = (JournalEntryFilterCriteria) getFilterCriteria();

			StringBuilder builder = new StringBuilder();
			builder.append("SELECT DISTINCT entry FROM JournalEntry entry WHERE entry.organization.id in(:orgs)");
			builder.append(" AND entry.entryStatus =:status ");

			if (SiriusValidator.validateParam(criteria.getCode()))
				builder.append(" AND entry.code like :code ");

			if (SiriusValidator.validateParam(criteria.getName()))
				builder.append(" AND entry.name like :name ");

			if (SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
				builder.append(" AND entry.organization.id =:org ");

			if (SiriusValidator.validateParam(criteria.getCreatedBy()))
			{
				builder.append("AND (entry.createdBy.firstName like :createdBy ");
				builder.append("OR entry.createdBy.middleName like :createdBy ");
				builder.append("OR entry.createdBy.lastName like :createdBy) ");
			}

			if (SiriusValidator.validateParam(criteria.getPeriod()))
			{
				builder.append("AND (entry.accountingPeriod.code like :period ");
				builder.append("OR entry.accountingPeriod.name like :period) ");
			}

			if (criteria.getDateFrom() != null)
			{
				if (criteria.getDateTo() != null)
					builder.append(" AND entry.entryDate BETWEEN :startDate AND :endDate ");
				else
					builder.append(" AND entry.entryDate >= :startDate");
			} else if (criteria.getDateTo() != null)
				builder.append(" AND entry.entryDate <= :endDate");

			if (SiriusValidator.validateParam(criteria.getEntryType()))
			{
				if (criteria.getEntryType().equalsIgnoreCase("standard"))
					builder.append(" AND (entry.entrySourceType = :type OR entry.entrySourceType = 'SCHEMATIC') ");
				else
					builder.append(" AND entry.entrySourceType = :type ");
			}

			builder.append("ORDER BY entry.entryDate DESC");

			Query query = getSession().createQuery(builder.toString());
			query.setCacheable(true);
			query.setReadOnly(true);
			query.setParameterList("orgs", getAccessibleOrganizations());
			query.setParameter("status", JournalEntryStatus.BATCHED);
			query.setFirstResult(criteria.start());
			query.setMaxResults(criteria.getMax());

			if (SiriusValidator.validateParam(criteria.getCode()))
				query.setParameter("code", "%" + criteria.getCode() + "%");

			if (SiriusValidator.validateParam(criteria.getName()))
				query.setParameter("name", "%" + criteria.getName() + "%");

			if (SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
				query.setParameter("org", criteria.getOrganization());

			if (SiriusValidator.validateParam(criteria.getCreatedBy()))
				query.setParameter("createdBy", "%" + criteria.getCreatedBy() + "%");

			if (SiriusValidator.validateParam(criteria.getPeriod()))
				query.setParameter("period", "%" + criteria.getPeriod() + "%");

			if (criteria.getDateFrom() != null)
				query.setParameter("startDate", criteria.getDateFrom());

			if (criteria.getDateTo() != null)
				query.setParameter("endDate", criteria.getDateTo());

			if (SiriusValidator.validateParam(criteria.getEntryType()))
				query.setParameter("type", EntrySourceType.valueOf(criteria.getEntryType()));

			list.addAll(query.list());
		}

		return list;
	}
}
