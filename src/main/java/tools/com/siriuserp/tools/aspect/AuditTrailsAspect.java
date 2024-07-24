/**
 * Nov 1, 2007 11:20:02 AM
 * com.siriuserp.sdk.aspect
 * AuditTrailsAspect.java
 */
package com.siriuserp.tools.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.ActivityHistory;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.UserHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Aspect
@Component
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
public class AuditTrailsAspect
{
	@Autowired
	private GenericDao genericDao;

	@AfterReturning("@annotation(auditTrails) && args(model,..)")
	public void audit(AuditTrails auditTrails, Model model) throws ServiceException, InterruptedException
	{
		if (auditTrails.actionType().equals(AuditTrailsActionType.CREATE))
		{
			if (model.getCreatedBy() == null)
				model.setCreatedBy(genericDao.load(Party.class, UserHelper.activePerson().getId()));

			model.setCreatedDate(DateHelper.now());
		} else if (auditTrails.actionType().equals(AuditTrailsActionType.UPDATE))
		{
			model.setUpdatedBy(genericDao.load(Party.class, UserHelper.activePerson().getId()));
			model.setUpdatedDate(DateHelper.now());
		}

		ActivityHistory activityHistory = new ActivityHistory();
		activityHistory.setAccessedModule(auditTrails.className().getSimpleName());
		activityHistory.setActionDate(DateHelper.now());
		activityHistory.setAccessedModuleId(model.getAuditCode());
		activityHistory.setActionType(auditTrails.actionType());
		activityHistory.setActivePerson(UserHelper.activePerson());

		genericDao.add(activityHistory);
	}
}
