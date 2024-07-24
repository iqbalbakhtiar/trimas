/**
 * Dec 19, 2007 2:31:21 PM
 * com.siriuserp.sdk.base
 * Service.java
 */
package com.siriuserp.sdk.base;

import org.springframework.beans.factory.annotation.Autowired;

import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.DisplayConfiguration;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.Profile;
import com.siriuserp.sdk.dm.User;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.exceptions.UserSessionExpired;
import com.siriuserp.sdk.utility.UserHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public abstract class Service
{
	@Autowired
	protected GenericDao genericDao;

	public Party getPerson() throws UserSessionExpired
	{
		return genericDao.load(Party.class, UserHelper.activePerson().getId());
	}

	public Party getPerson(Party current) throws UserSessionExpired
	{
		if (current == null)
			return genericDao.load(Party.class, UserHelper.activePerson().getId());

		return current;
	}

	public Profile getProfile() throws ServiceException
	{
		try
		{
			if (UserHelper.activeUser() != null)
			{
				User user = genericDao.load(User.class, UserHelper.activeUser().getId());
				if (user != null && user.getProfile() != null)
					return user.getProfile();
			}
		} catch (Exception e)
		{
			throw new UserSessionExpired(e);
		}

		return null;
	}

	public Party getActiveOrganization() throws ServiceException
	{
		Party organization = null;

		if (getProfile() != null && getProfile().getOrganization() != null)
			organization = getProfile().getOrganization();
		else
		{
			DisplayConfiguration configuration = genericDao.load(DisplayConfiguration.class, Long.valueOf(1));
			if (configuration != null && configuration.getOrganization() != null)
				organization = configuration.getOrganization();
		}

		return organization;
	}
}
