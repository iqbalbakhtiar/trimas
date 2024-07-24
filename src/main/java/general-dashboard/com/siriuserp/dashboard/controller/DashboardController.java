package com.siriuserp.dashboard.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dao.NewsDao;
import com.siriuserp.sdk.dm.DisplayConfiguration;
import com.siriuserp.sdk.dm.Profile;
import com.siriuserp.sdk.dm.User;
import com.siriuserp.sdk.utility.SessionHelper;
import com.siriuserp.sdk.utility.UserHelper;
import com.siriuserp.tools.service.ProfileService;

@Controller("dashBoardController")
public class DashboardController extends ControllerBase
{
	@Autowired
	private ProfileService profileService;

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private NewsDao newsDao;

	@RequestMapping("/dashboard.htm")
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String locale = "en";
		HttpSession session = request.getSession();

		if (session != null)
		{
			Profile profile = profileService.loadProfile();
			if (profile != null)
			{
				session.setAttribute(SessionHelper.PROFILE_KEY, profile);
				SessionHelper.refreshLocale(session, profile);

				locale = profile.getLocale().getCode();
			} else
			{
				DisplayConfiguration displayConfiguration = genericDao.load(DisplayConfiguration.class, Long.valueOf(1));
				if (displayConfiguration != null)
					SessionHelper.refreshLocale(session, displayConfiguration.getLocale());
			}
		}

		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 1);

		ModelAndView view = new ModelAndView("login");
		view.addObject("news", newsDao.loadLatesNews());

		User user = UserHelper.activeUser();

		if (user != null)
		{
			profileService.intializeUser(user, locale);

			view.addObject("dashboard", profileService.loadDashBoard(user));
			view.setViewName("dashBoard");
		}

		return view;
	}
}