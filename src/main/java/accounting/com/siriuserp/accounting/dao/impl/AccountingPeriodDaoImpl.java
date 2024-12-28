package com.siriuserp.accounting.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.siriuserp.accounting.dao.AccountingPeriodDao;
import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.accounting.dm.JournalEntry;
import com.siriuserp.accounting.dm.PeriodStatus;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.dm.Level;
import com.siriuserp.sdk.dm.Month;
import com.siriuserp.sdk.exceptions.DataEditException;
import com.siriuserp.sdk.utility.DateHelper;

import javolution.util.FastList;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@SuppressWarnings("unchecked")
@Component
public class AccountingPeriodDaoImpl extends DaoHelper<AccountingPeriod> implements AccountingPeriodDao
{
	@Override
	public AccountingPeriod loadToday(Long organization)
	{
		Query query = getSession().createQuery("FROM AccountingPeriod period WHERE :today BETWEEN period.startDate AND period.endDate AND period.organization.id =:org");
		query.setCacheable(true);
		query.setReadOnly(true);
		query.setParameter("today", new Date());
		query.setParameter("org", organization);

		Object object = query.uniqueResult();
		if (object != null)
			return (AccountingPeriod) object;

		return null;
	}

	@Override
	public void changeStatus(AccountingPeriod accountingPeriod, PeriodStatus periodStatus) throws DataEditException
	{
		Query query = getSession().createQuery("UPDATE AccountingPeriod period SET period.status =:status WHERE period.id =:id");
		query.setParameter("status", periodStatus);
		query.setParameter("id", accountingPeriod.getId());

		query.executeUpdate();
	}

	@Override
	public void openStatusNextPeriod(AccountingPeriod accountingPeriod) throws DataEditException
	{
		Query query = getSession().createQuery("UPDATE AccountingPeriod period SET period.status =:status WHERE period.organization.id =:org AND period.sequence =:sequence AND period.status =:currStatus");
		query.setParameter("status", PeriodStatus.OPEN);
		query.setParameter("currStatus", PeriodStatus.FUTURE);
		query.setParameter("org", accountingPeriod.getOrganization().getId());
		query.setParameter("sequence", Long.valueOf(accountingPeriod.getSequence() + 1));

		query.executeUpdate();
	}

	@Override
	public List<AccountingPeriod> loadAllNext(AccountingPeriod accountingPeriod)
	{
		Criteria criteria = getSession().createCriteria(AccountingPeriod.class);
		criteria.setCacheable(true);
		criteria.createCriteria("organization").add(Restrictions.eq("id", accountingPeriod.getOrganization().getId()));
		criteria.add(Restrictions.gt("startDate", accountingPeriod.getEndDate()));
		criteria.add(Restrictions.eq("year", accountingPeriod.getYear()));

		return criteria.list();
	}

	@Override
	public List<Long> loadAllIdByOrgs(List<Long> ids)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT period.id FROM AccountingPeriod period");

		if (!ids.isEmpty())
			builder.append(" WHERE period.organization.id in(:orgs)");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setReadOnly(true);

		if (!ids.isEmpty())
			query.setParameterList("orgs", ids);

		return query.list();
	}

	@Override
	public List<AccountingPeriod> loadAll(List<Long> organizations)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FROM AccountingPeriod period");

		if (!organizations.isEmpty())
			builder.append(" WHERE period.organization.id in(:orgs)");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setReadOnly(true);

		if (!organizations.isEmpty())
			query.setParameterList("orgs", organizations);

		return query.list();
	}

	@Override
	public AccountingPeriod loadNext(AccountingPeriod from)
	{
		Date date = DateHelper.plusOneDay(from.getEndDate());

		StringBuilder builder = new StringBuilder("FROM AccountingPeriod periode WHERE periode.organization.id =:org ");
		builder.append("AND periode.sequence =:sequence AND periode.startDate =:date AND periode.level =:level");

		Query query = getSession().createQuery(builder.toString());
		query.setParameter("org", from.getOrganization().getId());
		query.setParameter("sequence", Long.valueOf(DateHelper.toMonth(date)));
		query.setParameter("date", date);
		query.setParameter("level", Level.CHILD);

		Object object = query.uniqueResult();
		if (object != null)
			return (AccountingPeriod) object;

		return null;
	}

	@Override
	public AccountingPeriod load(Long organization, Date date, PeriodStatus status)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FROM AccountingPeriod period ");
		builder.append("WHERE period.level = 'CHILD' ");
		builder.append("AND period.organization.id =:org ");

		if (status != null)
			builder.append("AND period.status =:status ");

		builder.append("AND :_now BETWEEN period.startDate AND period.endDate ");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setReadOnly(true);

		if (status != null)
			query.setParameter("status", status);

		query.setParameter("_now", date);
		query.setParameter("org", organization);
		query.setMaxResults(1);

		Object object = query.uniqueResult();

		if (object != null)
			return (AccountingPeriod) object;

		return null;
	}

	@Override
	public AccountingPeriod loadPrev(AccountingPeriod from)
	{
		Date date = DateHelper.minusOneDay(from.getStartDate());

		StringBuilder builder = new StringBuilder("FROM AccountingPeriod periode WHERE periode.organization.id =:org ");
		builder.append("AND periode.sequence =:sequence AND periode.endDate =:date AND periode.level =:level");

		Query query = getSession().createQuery(builder.toString());
		query.setParameter("org", from.getOrganization().getId());
		query.setParameter("sequence", Long.valueOf(DateHelper.toMonth(date)));
		query.setParameter("date", date);
		query.setParameter("level", Level.CHILD);

		Object object = query.uniqueResult();
		if (object != null)
			return (AccountingPeriod) object;

		return null;
	}

	@Override
	public List<AccountingPeriod> loadAllPrevInYear(AccountingPeriod from)
	{
		Criteria criteria = getSession().createCriteria(AccountingPeriod.class);
		criteria.setCacheable(true);
		criteria.createCriteria("organization").add(Restrictions.eq("id", from.getOrganization().getId()));
		criteria.add(Restrictions.le("sequence", from.getSequence()));
		criteria.add(Restrictions.eq("level", Level.CHILD));

		return criteria.list();
	}

	@Override
	public List<Long> loadAllIDPrevInYear(AccountingPeriod from)
	{
		Query query = getSession().createQuery("SELECT period.id FROM AccountingPeriod period WHERE period.organization.id =:org AND period.year =:year AND period.sequence < :sequence AND period.level =:level");
		query.setCacheable(true);
		query.setReadOnly(false);
		query.setParameter("org", from.getOrganization().getId());
		query.setParameter("sequence", from.getSequence());
		query.setParameter("level", Level.CHILD);
		query.setParameter("year", from.getYear());

		return query.list();
	}

	@Override
	public Date loadMin(List<Long> periods)
	{
		Query query = getSession().createQuery("SELECT MIN(period.startDate)FROM AccountingPeriod period WHERE period.id in (:periods)");
		query.setCacheable(true);
		query.setReadOnly(false);
		query.setParameterList("periods", periods);

		return (Date) query.uniqueResult();
	}

	@Override
	public List<Integer> loadAllAsYear()
	{
		Query query = getSession().createQuery("SELECT DISTINCT period.year FROM AccountingPeriod period WHERE period.level =:level");
		query.setCacheable(true);
		query.setReadOnly(true);
		query.setParameter("level", Level.GROUP);

		return query.list();
	}

	@Override
	public List<Long> getAllIdByYearAndMonth(Integer year, Month month, Long org)
	{
		FastList<Long> periods = new FastList<Long>();
		//adding default
		periods.add(Long.valueOf(0));

		StringBuilder builder = new StringBuilder();
		builder.append("SELECT DISTINCT period.id FROM AccountingPeriod period,PartyRelationship relation");
		builder.append(" WHERE period.year =:period AND period.level =:level");

		if (month != null)
			builder.append(" AND period.month =:month");

		builder.append(" AND relation.relationshipType.id = 5");
		builder.append(" AND ");
		builder.append("(relation.fromRole.party.id = period.organization.id OR relation.toRole.party.id = period.organization.id)");
		builder.append(" AND ");
		builder.append("(relation.fromRole.party.id =:org OR relation.toRole.party.id =:org)");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setReadOnly(true);
		query.setParameter("period", year);

		if (month != null)
			query.setParameter("month", month);

		query.setParameter("org", org);
		query.setParameter("level", Level.CHILD);

		periods.addAll(query.list());

		return periods;
	}

	@Override
	public List<JournalEntry> loadJournals(Long periodId)
	{
		Query qry = getSession().createQuery("SELECT j FROM JournalEntry j WHERE j.chartOfAccount.id=:period GROUP BY j.id");
		qry.setParameter("period", periodId);
		return qry.list();
	}
}
