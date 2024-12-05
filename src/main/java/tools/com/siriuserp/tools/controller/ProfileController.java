/**
 * Dec 22, 2008 3:50:22 PM
 * com.siriuserp.tools.controller
 * UserProfileController.java
 */
package com.siriuserp.tools.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Locale;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.Profile;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.view.ViewHelper;
import com.siriuserp.sdk.utility.SessionHelper;
import com.siriuserp.tools.service.ProfileService;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = "profile", types = Profile.class)
public class ProfileController extends ControllerBase
{
	@Autowired
	private ProfileService profileService;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
		binder.registerCustomEditor(Locale.class, modelEditor.forClass(Locale.class));
		binder.registerCustomEditor(Container.class, modelEditor.forClass(Container.class));
		binder.registerCustomEditor(Facility.class, modelEditor.forClass(Facility.class));
	}

	@RequestMapping("/userprofilepreedit.htm")
	public ModelAndView profilepreedit() throws ServiceException
	{
		return new ModelAndView("/administration/profileUpdate", profileService.preedit());
	}

	@RequestMapping("/userprofileedit.htm")
	public ModelAndView profileedit(HttpServletRequest request, @ModelAttribute("profile") Profile profile, BindingResult result, SessionStatus status) throws ServiceException
	{
		profileService.edit(profile, request.getSession());

		HttpSession session = request.getSession(false);
		if (session != null)
		{
			SessionHelper.refreshLocale(session, profile);
			session.setAttribute(SessionHelper.PROFILE_KEY, profile);
		}

		status.setComplete();

		return ViewHelper.redirectTo("dashboard.htm");
	}
}
