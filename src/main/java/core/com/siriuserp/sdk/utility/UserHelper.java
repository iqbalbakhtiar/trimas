/**
 * Nov 28, 2007 10:25:59 AM
 * com.siriuserp.sdk.utility
 * UserHelper.java
 */
package com.siriuserp.sdk.utility;

import org.hibernate.Hibernate;
import org.springframework.security.context.SecurityContextHolder;

import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.User;
import com.siriuserp.sdk.exceptions.UserSessionExpired;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public class UserHelper
{
	public static final Party activePerson() throws UserSessionExpired
	{
		Party person = null;

		try
		{
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (principal != null && principal instanceof User)
			{
				person = ((User) principal).getPerson();

				Hibernate.initialize(person);
			}
		} catch (Exception e)
		{
			throw new UserSessionExpired();
		}

		return person;
	}

	public static final User activeUser() throws UserSessionExpired
	{
		User user = null;

		try
		{
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal != null && principal instanceof User)
			{
				user = (User) principal;

				Hibernate.initialize(user);
			}
		} catch (Exception e)
		{
			throw new UserSessionExpired();
		}

		return user;
	}
}
