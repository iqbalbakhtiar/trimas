/**
 * Mar 20, 2009 3:43:09 PM
 * com.siriuserp.accounting.query
 * FixedAssetGridViewQuery.java
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

public class FixedAssetGridViewQuery extends AbstractGridViewQuery
{
	private Query getQuery(StringBuilder builder, FixedAssetFilterCriteria filter)
	{
		if (SiriusValidator.validateParamWithZeroPosibility(filter.getOrganization()))
			builder.append(" AND asset.fixedAssetGroup.organization.id = " + filter.getOrganization() + " ");

		if (SiriusValidator.validateParam(filter.getCode()))
			builder.append(" AND asset.code like '%" + filter.getCode() + "%' ");

		if (SiriusValidator.validateParam(filter.getName()))
			builder.append(" AND asset.name like '%" + filter.getName() + "%' ");

		if (SiriusValidator.validateParam(filter.getCategory()))
			builder.append(" AND asset.fixedAssetGroup.name LIKE '%" + filter.getCategory() + "%' ");

		if (SiriusValidator.validateDate(filter.getDateFrom()))
		{
			if (SiriusValidator.validateDate(filter.getDateTo()))
				builder.append("AND asset.acquisitionDate BETWEEN :startDate AND :endDate ");
			else
				builder.append("AND asset.acquisitionDate >= :startDate ");
		} else if (SiriusValidator.validateDate(filter.getDateTo()))
			builder.append("AND asset.acquisitionDate <= :endDate ");

		if (filter.isDispose())
			builder.append(" AND asset.disposed = :disposal ");

		builder.append(" ORDER BY asset.fixedAssetGroup.name, asset.name ");

		Query query = getSession().createQuery(builder.toString());

		query.setParameterList("orgs", getAccessibleOrganizations());

		if (SiriusValidator.validateDate(filter.getDateFrom()))
			query.setParameter("startDate", filter.getDateFrom());

		if (SiriusValidator.validateDate(filter.getDateTo()))
			query.setParameter("endDate", filter.getDateTo());

		if (filter.isDispose())
			query.setParameter("disposal", filter.isDispose());

		return query;
	}

	@Override
	public Long count()
	{
		if (!getAccessibleOrganizations().isEmpty())
		{
			FixedAssetFilterCriteria filter = (FixedAssetFilterCriteria) getFilterCriteria();

			StringBuilder builder = new StringBuilder();
			builder.append("SELECT COUNT(asset) FROM FixedAsset asset WHERE (asset.fixedAssetGroup.organization.id IN(:orgs) OR asset.fixedAssetGroup.organization.id IS NULL)");

			Object object = getQuery(builder, filter).uniqueResult();
			if (object != null)
				return (Long) object;
		}

		return Long.valueOf(0);
	}

	@SuppressWarnings("unchecked")
	public Object execute()
	{
		FastList<FixedAsset> assets = new FastList<FixedAsset>();

		if (!getAccessibleOrganizations().isEmpty())
		{
			FixedAssetFilterCriteria filter = (FixedAssetFilterCriteria) getFilterCriteria();
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT asset FROM FixedAsset asset WHERE (asset.fixedAssetGroup.organization.id IN(:orgs) OR asset.fixedAssetGroup.organization.id IS NULL)");

			Query query = getQuery(builder, filter);
			query.setFirstResult(filter.start());
			query.setMaxResults(filter.getMax());

			assets.addAll(query.list());
		}

		return assets;
	}
}
