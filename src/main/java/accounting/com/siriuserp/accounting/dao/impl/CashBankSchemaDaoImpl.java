/**
 * 
 */
package com.siriuserp.accounting.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.siriuserp.accounting.dao.CashBankSchemaDao;
import com.siriuserp.accounting.dm.CashBankSchema;
import com.siriuserp.sdk.db.DaoHelper;

/**
 * @author
 * Betsu Brahmana Restu
 * PT. Sirius Indonesia
 * betsu@siriuserp.com
 */

@Component
public class CashBankSchemaDaoImpl extends DaoHelper<CashBankSchema> implements CashBankSchemaDao
{
	@Override
	public CashBankSchema getCashBankByTypeAndOrganization(Long type, Long organization)
	{
		StringBuilder builder = new StringBuilder("FROM CashBankSchema schema WHERE schema.accountingSchema.organization.id =:org");
		builder.append(" AND schema.closingAccountType.id =:type");

		Query query = getSession().createQuery(builder.toString());
		query.setParameter("org", organization);
		query.setParameter("type", type);

		Object object = query.uniqueResult();

		if (object != null)
			return (CashBankSchema) object;

		return null;
	}

}
