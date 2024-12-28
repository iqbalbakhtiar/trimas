/**
 * Dec 19, 2007 2:27:25 PM
 * net.konsep.sirius.accounting.dao.hibernate
 * FixedAssetGroupDaoImpl.java
 */
package com.siriuserp.accounting.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.siriuserp.accounting.dao.FixedAssetGroupDao;
import com.siriuserp.accounting.dm.ClosingAccount;
import com.siriuserp.accounting.dm.ClosingAccountType;
import com.siriuserp.accounting.dm.FixedAssetGroup;
import com.siriuserp.sdk.db.DaoHelper;

import javolution.util.FastList;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
@SuppressWarnings("unchecked")
public class FixedAssetGroupDaoImpl extends DaoHelper<FixedAssetGroup> implements FixedAssetGroupDao
{
	@Override
	public List<FixedAssetGroup> loadAllByOrgs(List<Long> orgs)
	{
		FastList<FixedAssetGroup> groups = new FastList<FixedAssetGroup>();

		if (!orgs.isEmpty())
		{
			Criteria criteria = getSession().createCriteria(FixedAssetGroup.class);
			criteria.createCriteria("organization").add(Restrictions.in("id", orgs));

			groups.addAll(criteria.list());
		}

		return groups;
	}

	@Override
	public ClosingAccount loadByOrganizationAndType(Long group, Long org)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT fixInfo.closingAccount FROM FixedAssetClosingInformation fixInfo ");
		builder.append("WHERE fixInfo.fixedAssetGroup.id = :groupId ");
		builder.append("AND fixInfo.fixedAssetGroup.organization.id = :org ");
		builder.append("AND fixInfo.closingAccount.closingAccountType.id = :type");

		Query query = getSession().createQuery(builder.toString());
		query.setParameter("groupId", group);
		query.setParameter("org", org);
		query.setParameter("type", ClosingAccountType.ASSET);

		Object object = query.uniqueResult();
		if (object != null)
			return (ClosingAccount) object;

		return null;
	}
}
