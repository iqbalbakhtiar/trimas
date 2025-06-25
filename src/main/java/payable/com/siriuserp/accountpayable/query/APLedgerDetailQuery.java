/**
 * Mar 11, 2010 10:36:42 AM
 * com.siriuserp.accountpayable.query
 * APLedgerDetailQuery.java
 */
package com.siriuserp.accountpayable.query;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.hibernate.Query;

import com.siriuserp.accountpayable.criteria.APLedgerFilterCriteria;
import com.siriuserp.accountpayable.dm.InvoiceVerification;
import com.siriuserp.accountpayable.dm.PaymentApplication;
import com.siriuserp.sdk.db.AbstractStandardReportQuery;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PartyRelationshipType;
import com.siriuserp.sdk.dm.datawarehouse.APLedgerView;
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
		supliers.addAll(getSupplier(criteria));

		for (Party supplier : supliers)
		{
			BigDecimal opening = getOpening(criteria, supplier).subtract(getPayment(criteria, supplier));

			FastList<APLedgerView> view = new FastList<APLedgerView>();
			view.addAll(getCredit(criteria, supplier));
			view.addAll(getDebet(criteria, supplier));

			if (opening.compareTo(BigDecimal.ZERO) > 0 || !view.isEmpty())
			{
				FastMap<String, Object> map = new FastMap<String, Object>();
				map.put("supplier", supplier);
				map.put("opening", opening);
				map.put("start", DateHelper.toStartDate(criteria.getDateFrom()));
				map.put("end", DateHelper.toEndDate(criteria.getDateTo()));

				Collections.sort(view);

				map.put("views", view);

				list.add(map);
			}
		}

		return list;
	}

	private BigDecimal getOpening(APLedgerFilterCriteria criteria, Party suplier)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT SUM(((ver.money.amount-ver.discount) * ver.quantity) + ((ver.money.amount-ver.discount)*invoiceVerification.tax.taxRate/100)) FROM InvoiceVerificationItem ver ");
		builder.append("WHERE ver.invoiceVerification.date < :from ");
		builder.append("AND ver.invoiceVerification.organization.id IN(:orgs) ");
		builder.append("AND ver.invoiceVerification.supplier.id =:supplier");

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
		builder.append("SELECT SUM(app.paidAmount-app.writeOff) FROM PaymentApplication app ");
		builder.append("WHERE app.payment.date < :from ");
		builder.append("AND app.payable.organization.id IN(:orgs) ");
		builder.append("AND app.payable.supplier.id =:supplier");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setParameter("from", criteria.getDateFrom());
		query.setParameterList("orgs", criteria.getOrganizations());
		query.setParameter("supplier", suplier.getId());

		return DecimalHelper.safe(query.uniqueResult());
	}

	private List<InvoiceVerification> getCredit(APLedgerFilterCriteria criteria, Party suplier)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FROM InvoiceVerification ver ");
		builder.append("WHERE (ver.date BETWEEN :start AND :end) ");
		builder.append("AND ver.organization.id IN(:orgs) ");
		builder.append("AND ver.supplier.id =:supplier");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setParameter("start", criteria.getDateFrom());
		query.setParameter("end", criteria.getDateTo());
		query.setParameterList("orgs", criteria.getOrganizations());
		query.setParameter("supplier", suplier.getId());

		return query.list();
	}

	private List<PaymentApplication> getDebet(APLedgerFilterCriteria criteria, Party supplier)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FROM PaymentApplication app ");
		builder.append("WHERE (app.payment.date BETWEEN :start AND :end) ");
		builder.append("AND app.payment.organization.id IN(:orgs) ");
		builder.append("AND app.payment.supplier.id =:supplier");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setParameter("start", criteria.getDateFrom());
		query.setParameter("end", criteria.getDateTo());
		query.setParameterList("orgs", criteria.getOrganizations());
		query.setParameter("supplier", supplier.getId());

		return query.list();
	}

	private List<Party> getSupplier(APLedgerFilterCriteria criteria)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT DISTINCT relation.partyFrom FROM PartyRelationship relation ");
		builder.append("WHERE relation.relationshipType.id =:relationshipType ");
		builder.append("AND relation.partyTo.id IN(:orgs) ");
		builder.append("AND relation.partyRoleTypeFrom.id =:from ");
		builder.append("AND relation.partyRoleTypeTo.id =:to ");

		if (SiriusValidator.validateLongParam(criteria.getSupplierId()))
			builder.append("AND relation.partyFrom.id =:supplierId ");

		builder.append("ORDER BY relation.partyFrom.fullName ASC");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setParameter("relationshipType", PartyRelationshipType.SUPPLIER_RELATIONSHIP);
		query.setParameter("from", Long.valueOf(5));
		query.setParameter("to", Long.valueOf(4));
		query.setParameterList("orgs", criteria.getOrganizations());

		if (SiriusValidator.validateLongParam(criteria.getSupplierId()))
			query.setParameter("supplierId", criteria.getSupplierId());

		return query.list();
	}
}
