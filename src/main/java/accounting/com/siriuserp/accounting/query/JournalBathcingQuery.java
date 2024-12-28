/**
 * Dec 23, 2008 4:02:39 PM
 * com.siriuserp.accounting.query
 * JournalBathcingQuery.java
 */
package com.siriuserp.accounting.query;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.siriuserp.accounting.criteria.JournalEntryFilterCriteria;
import com.siriuserp.accounting.dm.JournalEntry;
import com.siriuserp.accounting.dm.JournalEntryStatus;
import com.siriuserp.accounting.dm.JournalEntryType;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@SuppressWarnings("rawtypes")
public class JournalBathcingQuery extends AbstractGridViewQuery
{
	public Long count()
	{
		return Long.valueOf(((List) execute()).size());
	}

	@Override
	public Object execute()
	{
		Criteria criteria = getSession().createCriteria(JournalEntry.class);
		criteria.addOrder(Order.desc("id"));
		criteria.addOrder(Order.asc("organization.id"));
		criteria.add(Restrictions.eq("entryStatus", JournalEntryStatus.BATCHED));

		JournalEntryFilterCriteria filter = (JournalEntryFilterCriteria) filterCriteria;

		if (SiriusValidator.validateParam(filter.getCode()))
			criteria.add(Restrictions.like("code", filter.getCode(), MatchMode.ANYWHERE));

		if (SiriusValidator.validateParam(filter.getName()))
			criteria.add(Restrictions.like("name", filter.getName(), MatchMode.ANYWHERE));

		if (SiriusValidator.validateParam(filter.getEntryType()))
			criteria.add(Restrictions.eq("entryType", JournalEntryType.valueOf(filter.getEntryType())));

		if (SiriusValidator.validateParamWithZeroPosibility(filter.getOrganization()))
			criteria.createCriteria("organization").add(Restrictions.eq("id", filter.getOrganization()));

		if (filter.getDateFrom() != null)
		{
			if (filter.getDateTo() != null)
				criteria.add(Restrictions.between("entryDate", filter.getDateFrom(), filter.getDateTo()));
			else
				criteria.add(Restrictions.ge("entryDate", filter.getDateFrom()));
		}

		if (filter.getDateTo() != null)
			criteria.add(Restrictions.le("entryDate", filter.getDateTo()));

		criteria.setFirstResult(filter.start());
		criteria.setMaxResults(filter.getMax());

		return criteria.list();
	}
}
