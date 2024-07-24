/**
 * Jun 8, 2009 11:18:28 AM
 * com.siriuserp.sdk.aspect
 * PartyInjectionAspect.java
 */
package com.siriuserp.administration.aspect;

import java.lang.reflect.Method;
import java.util.Map;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.Profile;
import com.siriuserp.sdk.dm.User;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.utility.UserHelper;

/**
 * @author Agung Dodi Perdana
 * @author Betsu Brahmana Restu
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
@Aspect
public class PartyInjectionAspect
{
	@Autowired
	private GenericDao genericDao;

	@SuppressWarnings("unchecked")
	@AfterReturning(pointcut = "@annotation(party)", returning = "retVal")
	public void inject(InjectParty party, Object retVal) throws ServiceException
	{
		try
		{
			if (retVal instanceof Map)
			{
				if (UserHelper.activeUser() != null)
				{
					User user = genericDao.load(User.class, UserHelper.activeUser().getId());
					Profile profile = genericDao.load(Profile.class, user.getProfile().getId());

					((Map<String, Object>) retVal).put("organization", profile.getOrganization());

					Object target = ((Map<?, ?>) retVal).get(party.keyName());
					Method method = target.getClass().getMethod(party.targetMethod(), Party.class);

					if (method != null)
					{
						if (user != null && profile != null && profile.getOrganization() != null)
						{
							Method another = (Method) target.getClass().getMethod(party.sourceMethod(), new Class<?>[0]);
							Object object = another.invoke(target);

							if (object == null && method.getDefaultValue() == null)
								method.invoke(target, profile.getOrganization());
						}
					}
				}

			}
		} catch (Exception e)
		{
		}
	}
}
