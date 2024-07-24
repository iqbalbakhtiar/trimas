package com.siriuserp.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.AccessType;
import com.siriuserp.sdk.dm.DisplayConfiguration;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.Profile;
import com.siriuserp.sdk.dm.UrlCache;
import com.siriuserp.sdk.dm.User;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponseView;
import com.siriuserp.sdk.springmvc.view.ViewHelper;
import com.siriuserp.sdk.utility.SessionHelper;
import com.siriuserp.sdk.utility.UserHelper;
import com.siriuserp.tools.service.ProfileService;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class PageAccessLevelInterceptor extends HandlerInterceptorAdapter
{
	@Autowired
	private ProfileService profileService;

	@Autowired
	protected GenericDao genericDao;

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mv) throws Exception
	{
		if (mv.getView() instanceof JSONResponseView)
			return;

		try
		{
			HttpSession session = request.getSession(false);
			User user = UserHelper.activeUser();

			if (session != null && user != null)
			{
				UrlCache cache = null;

				String[] accessed = request.getRequestURI().split(request.getContextPath());
				if (accessed.length > 1)
				{
					String location = accessed[1];

					if (user.getUrls().get(location) != null)
						cache = user.getUrls().get(location);

					session.setAttribute(SessionHelper.URI_KEY, location + "?");

					if (session.getAttribute(SessionHelper.URL_KEY) == null || request.getMethod().compareTo("GET") == 0)
						session.setAttribute(SessionHelper.URL_KEY, location + "?" + request.getQueryString());
				}

				if (cache != null)
				{
					AccessType accessType = cache.getAccessType();

					if (accessType == null)
					{
						accessType = new AccessType();
						accessType.setAdd(false);
						accessType.setDelete(false);
						accessType.setEdit(false);
						accessType.setPrint(false);
						accessType.setRead(false);
					}

					String pageTitle = "";
					String title = "";
					String reportTitle = "";

					for (String moduleName : cache.getName().split(">"))
					{
						pageTitle = cache.getCode() + " - " + moduleName;
						title = moduleName;
						reportTitle = moduleName;
					}

					StringBuilder name = new StringBuilder(cache.getName());

					if (cache.getDetailType() != null)
					{
						StringBuilder post = new StringBuilder(" > ");
						post.append(cache.getDetailType().toString().substring(0, 1));
						post.append(cache.getDetailType().toString().substring(1).toLowerCase());

						name.append(post);
						title = title + post.toString();
						//title.append(post);

						mv.getModel().put("attribute", mv.getModelMap());
					}

					mv.getModel().put("pageTitle", pageTitle);
					mv.getModel().put("title", title.toString());
					mv.getModel().put("reportTitle", reportTitle.toString());
					mv.getModel().put("access", accessType);
					mv.getModel().put("breadcrumb", name.toString());
					mv.getModel().put("now", new Date());
				}

				if (user != null && user.getId() != null)
				{
					Party person = user.getPerson();

					if (person != null)
					{
						Object menu = session.getAttribute(SessionHelper.PERSON_KEY);

						if (menu == null)
						{
							session.setAttribute(SessionHelper.PERSON_KEY, user.getPerson());
							session.setAttribute(SessionHelper.MENU_KEY, user.getMenus().values());
						}
					}
				}

				if (session.getAttribute(SessionHelper.PROFILE_KEY) == null)
					refreshProfile(session);

				mv.getModel().put("profile", session.getAttribute(SessionHelper.PROFILE_KEY));

				if (session.getAttribute(SessionHelper.ACTIVE_ROLE_KEY) == null)
					session.setAttribute(SessionHelper.ACTIVE_ROLE_KEY, cache.getRole());

				mv.getModel().put("url", session.getAttribute(SessionHelper.URL_KEY));
				mv.getModel().put("uri", session.getAttribute(SessionHelper.URI_KEY));
			} else
				mv = ViewHelper.redirectTo("");
		} catch (Exception e)
		{
			mv = ViewHelper.redirectTo("");
		}
	}

	private void refreshProfile(HttpSession session) throws ServiceException
	{
		Profile profile = profileService.loadProfile();
		if (profile != null)
		{
			session.setAttribute(SessionHelper.PROFILE_KEY, profile);
			SessionHelper.refreshLocale(session, profile);
		} else
		{
			DisplayConfiguration displayConfiguration = genericDao.load(DisplayConfiguration.class, Long.valueOf(1));
			if (displayConfiguration != null)
				SessionHelper.refreshLocale(session, displayConfiguration.getLocale());
		}
	}
}
