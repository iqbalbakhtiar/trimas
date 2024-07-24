/**
 * Nov 12, 2008 12:46:35 PM
 * com.siriuserp.tools.service
 * ConfigurationAssistanceService.java
 */
package com.siriuserp.tools.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.ConfigurationAssistance;
import com.siriuserp.sdk.dm.DisplayConfiguration;
import com.siriuserp.sdk.dm.Locale;
import com.siriuserp.sdk.dm.RestApiConfiguration;
import com.siriuserp.sdk.exceptions.ServiceException;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class ConfigurationAssistanceService
{
	@Autowired
	private GenericDao genericDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("configurationAssistance", genericDao.load(ConfigurationAssistance.class, Long.valueOf(1)));

		return map;
	}

	public void edit(ConfigurationAssistance configurationAssistance) throws ServiceException
	{
		genericDao.update(configurationAssistance);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> display()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("displayConfiguration", genericDao.load(DisplayConfiguration.class, Long.valueOf(1)));
		map.put("locales", genericDao.loadAll(Locale.class));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> restapi()
	{
		Map<String, Object> map = new FastMap<String, Object>();
		map.put("restApiConfiguration", genericDao.load(RestApiConfiguration.class, 1L));

		return map;
	}

	@AuditTrails(className = DisplayConfiguration.class, actionType = AuditTrailsActionType.UPDATE)
	public void displayedit(DisplayConfiguration displayConfiguration) throws ServiceException
	{
		genericDao.update(displayConfiguration);
	}

	@AuditTrails(className = RestApiConfiguration.class, actionType = AuditTrailsActionType.UPDATE)
	public void restapiedit(RestApiConfiguration restApiConfiguration) throws ServiceException
	{
		genericDao.update(restApiConfiguration);
	}
}
