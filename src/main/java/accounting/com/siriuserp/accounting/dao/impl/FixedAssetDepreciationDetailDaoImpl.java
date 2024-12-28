/**
 * Mar 24, 2009 11:27:23 AM
 * com.siriuserp.accounting.dao.impl
 * FixedAssetDepreciationDetailDaoImpl.java
 */
package com.siriuserp.accounting.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.siriuserp.accounting.dao.FixedAssetDepreciationDetailDao;
import com.siriuserp.accounting.dm.FixedAssetDepreciationDetail;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.dm.Month;
import com.siriuserp.sdk.utility.DateHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
public class FixedAssetDepreciationDetailDaoImpl extends DaoHelper<FixedAssetDepreciationDetail> implements FixedAssetDepreciationDetailDao
{
	@SuppressWarnings("unchecked")
	public List<FixedAssetDepreciationDetail> loadByGroupAndMonth(Long fixed, Month month, Date date)
	{
		Query query = getSession().createQuery("FROM FixedAssetDepreciationDetail det WHERE det.fixedAsset.id = :fixed AND det.month =:month AND det.date BETWEEN :startDate AND :endDate ORDER BY det.fixedAsset.id ASC");
		query.setCacheable(true);
		query.setParameter("fixed", fixed);
		query.setParameter("month", month);
		query.setParameter("startDate", DateHelper.toStartDate(date));
		query.setParameter("endDate", DateHelper.toEndDate(date));

		return query.list();
	}
}
