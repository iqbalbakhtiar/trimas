/**
 * Mar 20, 2009 5:07:22 PM
 * com.siriuserp.popup.query
 * FixedAssetPopupGridViewQuery.java
 */
package com.siriuserp.accounting.query;

import org.hibernate.Query;

import com.siriuserp.accounting.criteria.FixedAssetFilterCriteria;
import com.siriuserp.accounting.dm.FixedAsset;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastList;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@SuppressWarnings("unchecked")
public class FixedAssetPopupGridViewQuery extends AbstractGridViewQuery
{
	@Override
	public Long count()
	{
		if (!getAccessibleOrganizations().isEmpty())
		{
			FixedAssetFilterCriteria filter = (FixedAssetFilterCriteria) getFilterCriteria();

			StringBuilder builder = new StringBuilder();
			builder.append("SELECT COUNT(DISTINCT asset) FROM FixedAsset asset ");

			if (SiriusValidator.validateParamWithZeroPosibility(filter.getOrganization()))
				builder.append(" WHERE asset.fixedAssetGroup.organization.id = " + filter.getOrganization());

			if (SiriusValidator.validateParam(filter.getCode()))
				builder.append(" AND asset.code like '%" + filter.getCode() + "%'");

			if (SiriusValidator.validateParam(filter.getName()))
				builder.append(" AND asset.name like '%" + filter.getName() + "%'");

			Query query = getSession().createQuery(builder.toString());

			Object object = query.uniqueResult();
			if (object != null)
				return (Long) object;
		}

		return Long.valueOf(0);
	}

	public Object execute()
	{
		FastList<FixedAsset> assets = new FastList<FixedAsset>();

		if (!getAccessibleOrganizations().isEmpty())
		{
			FixedAssetFilterCriteria filter = (FixedAssetFilterCriteria) getFilterCriteria();

			StringBuilder builder = new StringBuilder();
			builder.append("SELECT DISTINCT(asset) FROM FixedAsset asset ");

			if (SiriusValidator.validateParamWithZeroPosibility(filter.getOrganization()))
				builder.append(" WHERE asset.fixedAssetGroup.organization.id = " + filter.getOrganization());

			if (SiriusValidator.validateParam(filter.getCode()))
				builder.append(" AND asset.code like '%" + filter.getCode() + "%'");

			if (SiriusValidator.validateParam(filter.getName()))
				builder.append(" AND asset.name like '%" + filter.getName() + "%'");

			Query query = getSession().createQuery(builder.toString());
			query.setFirstResult(filter.start());
			query.setMaxResults(filter.getMax());

			assets.addAll(query.list());
		}

		return assets;
	}

}
