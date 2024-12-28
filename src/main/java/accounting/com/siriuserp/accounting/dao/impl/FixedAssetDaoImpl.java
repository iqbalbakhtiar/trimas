/**
 * Dec 21, 2007 4:41:05 PM
 * net.konsep.sirius.accounting.dao.hibernate
 * FixedAssetDaoImpl.java
 */
package com.siriuserp.accounting.dao.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.siriuserp.accounting.dao.FixedAssetDao;
import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.accounting.dm.FixedAsset;
import com.siriuserp.accounting.dm.FixedAssetGroup;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.db.GridViewQuery;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component("fixedAssetDao")
@SuppressWarnings("unchecked")
public class FixedAssetDaoImpl extends DaoHelper<FixedAsset> implements FixedAssetDao
{
	public List<FixedAsset> loadFixedAssetBefore(AccountingPeriod period, Long organization)
	{
		Criteria criteria = getSession().createCriteria(FixedAsset.class);
		criteria.add(Restrictions.le("acquisitionDate", period.getEndDate()));
		criteria.add(Restrictions.gt("usefulLife", BigDecimal.ZERO));
		criteria.createCriteria("fixedAssetGroup").createCriteria("organization").add(Restrictions.eq("id", organization));
		criteria.add(Restrictions.eq("disposed", Boolean.FALSE));
		criteria.add(Restrictions.or(Restrictions.eq("lastDeprectiation", period.getId()), Restrictions.isNull("lastDeprectiation")));

		return criteria.list();
	}

	public List<FixedAsset> filter(GridViewQuery query)
	{
		query.setSession(getSession());
		query.init();
		return (List<FixedAsset>) query.execute();
	}

	@Override
	public Long getMax(GridViewQuery query)
	{
		query.setSession(getSession());
		query.init();
		return query.count();
	}

	@Override
	public List<FixedAsset> loadFixedAssetBefore(FixedAssetGroup group, Date date, Long organization)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT fa FROM FixedAsset fa ");
		builder.append("WHERE fa.fixedAssetGroup.id = :group ");
		builder.append("AND fa.fixedAssetGroup.organization.id  = :org ");
		builder.append("ORDER BY fa.id ASC");

		Query query = getSession().createQuery(builder.toString());
		query.setReadOnly(true);

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		query.setParameter("group", group.getId());
		query.setParameter("org", organization);
		//query.setParameter("date", date);

		return query.list();
	}
}
