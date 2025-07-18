/**
 * File Name  : FundApplicationAddQuery.java
 * Created On : Jul 18, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountpayable.query;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;

import com.siriuserp.accountpayable.criteria.APLedgerFilterCriteria;
import com.siriuserp.sdk.db.AbstractStandardReportQuery;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PartyRelationshipType;
import com.siriuserp.sdk.utility.DecimalHelper;

import javolution.util.FastList;
import javolution.util.FastMap;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@SuppressWarnings("unchecked")
public class FundApplicationAddQuery extends AbstractStandardReportQuery
{
	@Override
	public Object execute()
	{
		APLedgerFilterCriteria criteria = (APLedgerFilterCriteria) getFilterCriteria();
		FastList<FastMap<String, Object>> list = new FastList<FastMap<String, Object>>();

		for (Party supplier : getSupplier(criteria))
		{
			BigDecimal amount = getAmount(criteria, supplier);

			if (amount.compareTo(BigDecimal.ZERO) > 0)
			{
				FastMap<String, Object> map = new FastMap<String, Object>();
				map.put("supplier", supplier);
				map.put("amount", amount);

				list.add(map);
			}
		}

		return list;
	}

	private List<Party> getSupplier(APLedgerFilterCriteria criteria)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT DISTINCT relation.partyFrom FROM PartyRelationship relation ");
		builder.append("WHERE relation.relationshipType.id =:relationshipType ");
		builder.append("AND relation.partyTo.id IN(:orgs) ");
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

	private BigDecimal getAmount(APLedgerFilterCriteria criteria, Party supplier)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT SUM(ver.unpaid) FROM InvoiceVerification ver ");
		builder.append("WHERE ver.status = 'UNPAID' ");
		builder.append("AND ver.organization.id =:org ");
		builder.append("AND ver.supplier.id =:supplier ");
		builder.append("AND ver.date <= :date");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setParameter("org", criteria.getOrganization());
		query.setParameter("supplier", supplier.getId());
		query.setParameter("date", criteria.getDate());

		return DecimalHelper.safe(query.uniqueResult());
	}
}
