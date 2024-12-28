/**
 * Dec 21, 2007 4:47:34 PM
 * com.siriuserp.accounting.service
 * FixedAssetService.java
 */
package com.siriuserp.accounting.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.userdetails.ldap.Person;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accounting.criteria.FixedAssetFilterCriteria;
import com.siriuserp.accounting.dao.FixedAssetDao;
import com.siriuserp.accounting.dm.DecliningInformation;
import com.siriuserp.accounting.dm.DepreciationMethod;
import com.siriuserp.accounting.dm.FixedAsset;
import com.siriuserp.accounting.dm.FixedAssetGroup;
import com.siriuserp.accounting.dm.Preasset;
import com.siriuserp.accounting.dm.StraightLineDepreciationInformation;
import com.siriuserp.accounting.posting.AutomaticPosting;
import com.siriuserp.accounting.posting.FixedAssetAqcuisitionPostingRole;
import com.siriuserp.accounting.posting.FixedAssetDisposePostingRole;
import com.siriuserp.sales.dm.ApprovableType;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.ApprovalDecision;
import com.siriuserp.sdk.dm.ApprovalDecisionStatus;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class FixedAssetService
{
	@Autowired
	private GenericDao genericDao;

	@Autowired
	private CodeSequenceDao codeSequenceDao;

	@Autowired
	private FixedAssetDao fixedAssetDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		FixedAssetFilterCriteria criteria = (FixedAssetFilterCriteria) filterCriteria;

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
			map.put("organization", genericDao.load(Party.class, criteria.getOrganization()));

		if (SiriusValidator.validateParamWithZeroPosibility(criteria.getPerson()))
			map.put("person", genericDao.load(Person.class, criteria.getOrganization()));

		map.put("assets", FilterAndPaging.filter(fixedAssetDao, QueryFactory.create(filterCriteria, queryclass)));
		map.put("filterCriteria", filterCriteria);

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preadd(String group)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();

		FixedAsset fixedAsset = new FixedAsset();
		fixedAsset.setFixedAssetGroup(genericDao.load(FixedAssetGroup.class, Long.valueOf(group)));

		map.put("fixedAsset_add", fixedAsset);
		map.put("currencys", genericDao.loadAll(Currency.class));

		return map;
	}

	@AuditTrails(className = FixedAsset.class, actionType = AuditTrailsActionType.CREATE)
	public void add(FixedAsset fixedAsset) throws ServiceException
	{
		fixedAsset.setCode(GeneratorHelper.instance().generate(TableType.FIXED_ASSET, codeSequenceDao, fixedAsset.getOrganization()));
		fixedAsset.setUsefulLife(fixedAsset.getFixedAssetGroup().getUsefulLife());
		fixedAsset.setMaxlife(fixedAsset.getFixedAssetGroup().getUsefulLife());

		if (fixedAsset.getFixedAssetGroup().getDepreciationMethod().equals(DepreciationMethod.STRAIGHT_LINE))
		{
			StraightLineDepreciationInformation information = new StraightLineDepreciationInformation();
			information.setMonth(fixedAsset.getAcquisitionValue().subtract(fixedAsset.getSalvageValue()).divide(fixedAsset.getFixedAssetGroup().getUsefulLife(), 0, RoundingMode.HALF_UP));
			information.setAccumulated(BigDecimal.ZERO);
			information.setBookValue(fixedAsset.getAcquisitionValue());

			fixedAsset.setStraightLine(information);
		} else if (fixedAsset.getFixedAssetGroup().getDepreciationMethod().equals(DepreciationMethod.DECLINING_BALANCE))
		{
			DecliningInformation information = new DecliningInformation();
			information.setAccumulated(BigDecimal.ZERO);
			information.setBookValue(fixedAsset.getAcquisitionValue());

			fixedAsset.setDecliningInformation(information);
		}

		fixedAsset.setMaxlife(fixedAsset.getUsefulLife());
		fixedAssetDao.add(fixedAsset);

		if (fixedAsset.getPreasset() != null)
		{
			Preasset preasset = (Preasset) genericDao.load(Preasset.class, fixedAsset.getPreasset().getId());
			if (preasset != null)
			{
				preasset.setUsed(true);
				genericDao.update(preasset);
			}
		}
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preedit(Long id)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();

		FixedAsset fixedAsset = load(id);
		Hibernate.initialize(fixedAsset.getDecliningInformation());
		Hibernate.initialize(fixedAsset.getStraightLine());
		Hibernate.initialize(fixedAsset.getBankAccount());
		Hibernate.initialize(fixedAsset.getOrganization());

		map.put("approvalDecision", fixedAsset.getApprovalDecision());
		map.put("fixedAsset_edit", fixedAsset);

		return map;

	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FixedAsset load(Long id)
	{
		return genericDao.load(FixedAsset.class, id);
	}

	@AuditTrails(className = FixedAsset.class, actionType = AuditTrailsActionType.UPDATE)
	@AutomaticPosting(roleClasses = FixedAssetDisposePostingRole.class)
	public void edit(FixedAsset fixedAsset) throws ServiceException
	{
		if (fixedAsset.isDisposed())
		{
			fixedAsset.setUsefulLife(BigDecimal.ZERO);
			if (fixedAsset.getDisposeAmount() == null)
				fixedAsset.setDisposeAmount(BigDecimal.ZERO);
			fixedAsset.setApprovableType(ApprovableType.FIXED_ASSET_DISPOSAL);
			fixedAsset.setUri("fixedassetdisposepreedit.htm");
			fixedAsset.setOrganization(fixedAsset.getFixedAssetGroup().getOrganization());

			ApprovalDecision decision = new ApprovalDecision();
			decision.setApprovalDecisionStatus(ApprovalDecisionStatus.REQUESTED);
			decision.setCreatedDate(DateHelper.now());
			decision.setForwardTo(fixedAsset.getApprover());

			fixedAsset.setApprovalDecision(decision);

		}

		fixedAssetDao.update(fixedAsset);
	}

	@AuditTrails(className = FixedAsset.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(FixedAsset fixedAsset) throws ServiceException
	{
		fixedAssetDao.delete(load(fixedAsset.getId()));
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> predispose(String id)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("fixedAsset_edit", load(Long.valueOf(id)));

		return map;
	}

	@AuditTrails(className = FixedAsset.class, actionType = AuditTrailsActionType.CREATE)
	@AutomaticPosting(roleClasses = FixedAssetAqcuisitionPostingRole.class)
	public void postingfixedasset(FixedAsset fixedAsset) throws ServiceException
	{
	}
}
