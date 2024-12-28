/**
 * Dec 1, 2008 10:00:25 AM
 * com.siriuserp.sdk.dao.impl
 * FixedAssetClosingInformationDaoImpl.java
 */
package com.siriuserp.accounting.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.siriuserp.accounting.dao.FixedAssetClosingInformationDao;
import com.siriuserp.accounting.dm.FixedAssetClosingInformation;
import com.siriuserp.sdk.db.DaoHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
public class FixedAssetClosingInformationDaoImpl extends DaoHelper<FixedAssetClosingInformation> implements FixedAssetClosingInformationDao
{
	@Override
	public FixedAssetClosingInformation loadByGroupAndType(Long group, Long type)
	{
		Criteria criteria = getSession().createCriteria(FixedAssetClosingInformation.class);
		criteria.createCriteria("fixedAssetGroup").add(Restrictions.eq("id", group));
		criteria.createCriteria("closingAccount").createCriteria("closingAccountType").add(Restrictions.eq("id", type));

		Object object = criteria.uniqueResult();
		if (object != null)
			return (FixedAssetClosingInformation) object;

		return null;
	}
}
