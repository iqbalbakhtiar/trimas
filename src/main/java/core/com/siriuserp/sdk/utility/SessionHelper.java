/**
 * Aug 15, 2007 11:35:58 AM
 * com.siriuserp.sdk.utility
 * SessionHelper.java
 */
package com.siriuserp.sdk.utility;

import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.siriuserp.sdk.dm.Locale;
import com.siriuserp.sdk.dm.Profile;
import com.siriuserp.sdk.dm.SiriusSession;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class SessionHelper extends SiriusSession
{
	public static final synchronized void refreshLocale(HttpSession session, Profile profile)
	{
		if (session != null && profile != null && profile.getLocale() != null)
			refreshLocale(session, profile.getLocale().getCode());
	}

	public static final synchronized void refreshLocale(HttpSession session, Locale locale)
	{
		if (session != null && locale != null)
			refreshLocale(session, locale.getCode());
	}

	public static final synchronized void refreshLocale(HttpSession session, String local)
	{
		if (SiriusValidator.validateParam(local))
			session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, new java.util.Locale(local));
	}
}
