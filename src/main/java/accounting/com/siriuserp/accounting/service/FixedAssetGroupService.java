/**
 * Dec 19, 2007 2:25:14 PM
 * com.siriuserp.accounting.service
 * FixedAssetGroupService.java
 */
package com.siriuserp.accounting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accounting.dao.ClosingAccountTypeDao;
import com.siriuserp.accounting.dm.ClosingAccount;
import com.siriuserp.accounting.dm.ClosingAccountType;
import com.siriuserp.accounting.dm.FixedAssetClosingInformation;
import com.siriuserp.accounting.dm.FixedAssetGroup;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class FixedAssetGroupService extends Service
{
	@Autowired
	private GenericDao genericDao;

	@Autowired
	private ClosingAccountTypeDao closingAccountTypeDao;

	@Autowired
	private CodeSequenceDao codeSequenceDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends AbstractGridViewQuery> queryclass) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();

		map.put("groups", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));
		map.put("filterCriteria", filterCriteria);

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	@InjectParty(keyName = "fixedAssetGroup")
	public FastMap<String, Object> preadd()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		FixedAssetGroup fixedAssetGroup = new FixedAssetGroup();
		fixedAssetGroup.setDepreciateAssetImmediately(true);

		for (ClosingAccountType closingAccountType : closingAccountTypeDao.loadAllAsset())
		{
			ClosingAccount closingAccount = new ClosingAccount();
			closingAccount.setClosingAccountType(closingAccountType);

			FixedAssetClosingInformation closingInformation = new FixedAssetClosingInformation();
			closingInformation.setClosingAccount(closingAccount);
			closingInformation.setFixedAssetGroup(fixedAssetGroup);

			fixedAssetGroup.getClosingInformations().add(closingInformation);
		}
		map.put("fixedAssetGroup", fixedAssetGroup);

		return map;
	}

	@AuditTrails(className = FixedAssetGroup.class, actionType = AuditTrailsActionType.CREATE)
	public void add(FixedAssetGroup fixedAssetGroup) throws ServiceException
	{
		fixedAssetGroup.setCode(GeneratorHelper.instance().generate(TableType.FIXED_ASSET_CATEGORY, codeSequenceDao, fixedAssetGroup.getOrganization()));
		genericDao.add(fixedAssetGroup);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(String id)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("fixedAssetGroup", load(Long.valueOf(id)));

		return map;
	}

	@AuditTrails(className = FixedAssetGroup.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(FixedAssetGroup fixedAssetGroup) throws ServiceException
	{
		genericDao.update(fixedAssetGroup);
	}

	@AuditTrails(className = FixedAssetGroup.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(FixedAssetGroup fixedAssetGroup) throws ServiceException
	{
		genericDao.delete(load(fixedAssetGroup.getId()));
	}

	@Transactional(readOnly = true)
	public FixedAssetGroup load(Long id)
	{
		return genericDao.load(FixedAssetGroup.class, id);
	}
}
