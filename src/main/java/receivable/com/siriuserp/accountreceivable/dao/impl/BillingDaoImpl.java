/**
 * File Name  : BillingDaoImpl.java
 * Created On : Apr 9, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountreceivable.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.siriuserp.accountreceivable.dao.BillingDao;
import com.siriuserp.accountreceivable.dm.Billing;
import com.siriuserp.accountreceivable.dm.BillingReferenceType;
import com.siriuserp.sdk.db.DaoHelper;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Component
public class BillingDaoImpl extends DaoHelper<Billing> implements BillingDao
{
	@Override
	public Billing getBillingByReference(Long referenceId, BillingReferenceType referenceType)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT DISTINCT(item.billing) ");
		builder.append("FROM BillingItem item ");
		builder.append("WHERE item.billingReferenceItem.referenceId =:referenceId ");
		builder.append("AND item.billingReferenceItem.referenceType =:referenceType ");
		builder.append("ORDER BY item.billing.id");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setMaxResults(1);
		query.setParameter("referenceId", referenceId);
		query.setParameter("referenceType", referenceType);

		Object obj = query.uniqueResult();
		if (obj != null)
			return (Billing) obj;

		return null;
	}
}
