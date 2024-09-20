/**
 * Oct 24, 2008 11:02:03 AM
 * com.siriuserp.sdk.dao.impl
 * UnitOfMeasureFactorDaoImpl.java
 */
package com.siriuserp.administration.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.siriuserp.inventory.dm.UnitofMeasureFactor;
import com.siriuserp.sdk.dao.UnitOfMeasureFactorDao;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
public class UnitOfMeasureFactorDaoImpl extends DaoHelper<UnitofMeasureFactor> implements UnitOfMeasureFactorDao
{
	@Override
	public UnitofMeasureFactor load(Long from, Long to)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FROM UnitofMeasureFactor factor ");
		builder.append("WHERE factor.factor IS NOT NULL ");

		if (SiriusValidator.validateParamWithZeroPosibility(from))
			builder.append("AND factor.from.id =:factorFrom ");

		if (SiriusValidator.validateParamWithZeroPosibility(to))
			builder.append("AND factor.to.id =:factorTo ");

		Query query = getSession().createQuery(builder.toString());
		query.setCacheable(true);
		query.setReadOnly(true);
		query.setMaxResults(1);

		if (SiriusValidator.validateParamWithZeroPosibility(from))
			query.setParameter("factorFrom", from);

		if (SiriusValidator.validateParamWithZeroPosibility(to))
			query.setParameter("factorTo", to);

		Object object = query.uniqueResult();
		if (object != null)
			return (UnitofMeasureFactor) object;

		return null;
	}
}
