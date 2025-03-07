/**
 * Mar 11, 2010 10:36:42 AM
 * com.siriuserp.accountpayable.query
 * APLedgerDetailQuery.java
 */
package com.siriuserp.accountpayable.query;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;

import com.siriuserp.accountpayable.criteria.APLedgerFilterCriteria;
import com.siriuserp.sdk.db.AbstractStandardReportQuery;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PartyRelationshipType;
import com.siriuserp.sdk.utility.DateHelper;
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
public class APLedgerDetailQuery extends AbstractStandardReportQuery
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

		FastList<Party> supliers = new FastList<Party>();
		if (criteria.getSupplier() != null)
			supliers.add(criteria.getSupplier());
		else
			supliers.addAll(getSupplier(criteria));

		for (Party supplier : supliers)
		{
			BigDecimal opening = getOpening(criteria, supplier).subtract(getPayment(criteria, supplier));

			if (opening.compareTo(BigDecimal.ZERO) > 0)
			{
				FastMap<String, Object> map = new FastMap<String, Object>();
				map.put("supplier", supplier);
				map.put("opening", opening);
				map.put("start", DateHelper.toStartDate(criteria.getDateFrom()));
				map.put("end", DateHelper.toEndDate(criteria.getDateTo()));

				list.add(map);
			}
		}

		return list;
	}

	private BigDecimal getOpening(APLedgerFilterCriteria criteria, Party suplier)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT SUM(ver.amount) FROM Verification ver ");
		builder.append("WHERE ver.date < :from ");
		builder.append("AND ver.organization.id in(:orgs) ");
		builder.append("AND ver.supplier.id =:supplier");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setParameter("from", criteria.getDateFrom());
		query.setParameterList("orgs", criteria.getOrganizations());
		query.setParameter("supplier", suplier.getId());

		return DecimalHelper.safe(query.uniqueResult());
	}

	private BigDecimal getPayment(APLedgerFilterCriteria criteria, Party suplier)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT SUM(app.paid-app.writeOff) FROM PaymentApplication app ");
		builder.append("WHERE app.payment.date < :from ");
		builder.append("AND app.payable.organization.id in(:orgs) ");
		builder.append("AND app.payable.supplier.id =:supplier");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setParameter("from", criteria.getDateFrom());
		query.setParameterList("orgs", criteria.getOrganizations());
		query.setParameter("supplier", suplier.getId());

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
		query.setParameter("from", Long.valueOf(2));
		query.setParameter("to", Long.valueOf(1));
		query.setParameterList("orgs", criteria.getOrganizations());

		return query.list();
	}
}
