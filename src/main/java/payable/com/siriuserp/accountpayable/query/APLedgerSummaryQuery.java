/**
 * Mar 11, 2010 10:36:42 AM
 * com.siriuserp.accountpayable.query
 * APLedgerSummaryQuery.java
 */
package com.siriuserp.accountpayable.query;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.hibernate.Query;

import com.siriuserp.accountpayable.criteria.APLedgerFilterCriteria;
import com.siriuserp.sdk.db.AbstractStandardReportQuery;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PartyRelationshipType;
import com.siriuserp.sdk.utility.DecimalHelper;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastList;
import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 * Version 1.5
 */

@SuppressWarnings("unchecked")
public class APLedgerSummaryQuery extends AbstractStandardReportQuery
{
	@Override
	public Object execute()
	{
		FastList<FastMap<String, Object>> list = new FastList<FastMap<String, Object>>();

		APLedgerFilterCriteria criteria = (APLedgerFilterCriteria) getFilterCriteria();

		FastList.recycle(criteria.getOrganizations());

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
			criteria.getOrganizations().add(criteria.getOrganization());
		else
		{
			Query query = getSession().createQuery("SELECT org.id FROM Organization org");
			query.setCacheable(true);

			criteria.getOrganizations().addAll(query.list());
		}
		
		for (Party supplier : getSupplier(criteria))
		{
			BigDecimal opening = getOpening(criteria, supplier).subtract(getPayment(criteria, supplier));
			BigDecimal credit = getCredit(criteria, supplier);
			BigDecimal debet = getDebet(criteria, supplier);

			if (opening.compareTo(BigDecimal.ONE) < 0)
				opening = opening.setScale(0, RoundingMode.HALF_DOWN);
			if (credit.compareTo(BigDecimal.ONE) < 0)
				credit = credit.setScale(0, RoundingMode.HALF_DOWN);
			if (debet.compareTo(BigDecimal.ONE) < 0)
				debet = debet.setScale(0, RoundingMode.HALF_DOWN);

			if (opening.compareTo(BigDecimal.ZERO) > 0 || debet.compareTo(BigDecimal.ZERO) > 0 || credit.compareTo(BigDecimal.ZERO) > 0)
			{
				FastMap<String, Object> map = new FastMap<String, Object>();
				map.put("supplier", supplier);
				map.put("opening", opening);
				map.put("debet", debet);
				map.put("credit", credit);
				map.put("closing", opening.subtract(debet).add(credit));

				list.add(map);
			}
		}

		return list;
	}

	private BigDecimal getOpening(APLedgerFilterCriteria criteria, Party supplier)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT SUM(ver.money.amount) FROM InvoiceVerification ver ");
		builder.append("WHERE ver.date < :from ");
		builder.append("AND ver.organization.id in(:orgs) ");
		builder.append("AND ver.supplier.id =:supplier");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setParameter("from", criteria.getDateFrom());
		query.setParameterList("orgs", criteria.getOrganizations());
		query.setParameter("supplier", supplier.getId());

		return DecimalHelper.safe(query.uniqueResult());
	}

	private BigDecimal getPayment(APLedgerFilterCriteria criteria, Party supplier)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT SUM(app.paidAmount-app.writeOff) FROM PaymentApplication app ");
		builder.append("WHERE app.payment.date < :from ");
		builder.append("AND app.payable.organization.id in(:orgs) ");
		builder.append("AND app.payable.supplier.id =:supplier");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setParameter("from", criteria.getDateFrom());
		query.setParameterList("orgs", criteria.getOrganizations());
		query.setParameter("supplier", supplier.getId());

		return DecimalHelper.safe(query.uniqueResult());
	}

	private BigDecimal getCredit(APLedgerFilterCriteria criteria, Party supplier)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT SUM(ver.money.amount) FROM InvoiceVerification ver ");
		builder.append("WHERE (ver.date BETWEEN :start AND :end) ");
		builder.append("AND ver.organization.id in(:orgs) ");
		builder.append("AND ver.supplier.id =:supplier");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setParameter("start", criteria.getDateFrom());
		query.setParameter("end", criteria.getDateTo());
		query.setParameterList("orgs", criteria.getOrganizations());
		query.setParameter("supplier", supplier.getId());
		
		return DecimalHelper.safe(query.uniqueResult());
	}

	private BigDecimal getDebet(APLedgerFilterCriteria criteria, Party supplier)
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("SELECT SUM(app.paidAmount-app.writeOff) FROM PaymentApplication app ");
		builder.append("WHERE (app.payment.date BETWEEN :start AND :end) ");
		builder.append("AND app.payable.organization.id in(:orgs) ");
		builder.append("AND app.payable.supplier.id =:supplier");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setParameter("start", criteria.getDateFrom());
		query.setParameter("end", criteria.getDateTo());
		query.setParameterList("orgs", criteria.getOrganizations());
		query.setParameter("supplier", supplier.getId());

		return DecimalHelper.safe(query.uniqueResult());
	}

	private List<Party> getSupplier(APLedgerFilterCriteria criteria)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT DISTINCT relation.partyFrom FROM PartyRelationship relation ");
		builder.append("WHERE relation.relationshipType.id =:relationshipType ");
		builder.append("AND relation.partyTo.id in(:orgs) ");
		builder.append("AND relation.partyRoleTypeFrom.id =:from ");
		builder.append("AND relation.partyRoleTypeTo.id =:to");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setParameter("relationshipType", PartyRelationshipType.SUPPLIER_RELATIONSHIP);
		query.setParameter("from", Long.valueOf(5));
		query.setParameter("to", Long.valueOf(4));
		query.setParameterList("orgs", criteria.getOrganizations());

		return query.list();
	}
}
