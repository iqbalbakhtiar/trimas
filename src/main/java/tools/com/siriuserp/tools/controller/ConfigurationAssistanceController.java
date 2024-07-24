/**
 * Nov 12, 2008 12:46:20 PM
 * com.siriuserp.tools.controller
 * ConfigurationAssistanceController.java
 */
package com.siriuserp.tools.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.DisplayConfiguration;
import com.siriuserp.sdk.dm.Locale;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.RestApiConfiguration;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.view.ViewHelper;
import com.siriuserp.sdk.utility.SessionHelper;
import com.siriuserp.tools.service.ConfigurationAssistanceService;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value =
{ "displayConfiguration", "restApiConfiguration" }, types =
{ DisplayConfiguration.class, RestApiConfiguration.class })
public class ConfigurationAssistanceController extends ControllerBase
{
	@Autowired
	private ConfigurationAssistanceService assistanceService;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(Locale.class, modelEditor.forClass(Locale.class));
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
	}

	@RequestMapping("/configurationassistancepreedit.htm")
	public ModelAndView preedit()
	{
		return new ModelAndView("configurationAssistanceUpdate", assistanceService.preedit());
	}

	@RequestMapping("/configurationassistancedisplayconfig.htm")
	public ModelAndView display()
	{
		return new ModelAndView("displayConfigurationUpdate", assistanceService.display());
	}

	@RequestMapping("/configurationassistancedisplayconfigedit.htm")
	public ModelAndView displayedit(HttpServletRequest request, @ModelAttribute("displayConfiguration") DisplayConfiguration configuration, SessionStatus status) throws ServiceException
	{
		assistanceService.displayedit(configuration);

		HttpSession httpSession = request.getSession(false);
		if (httpSession != null)
		{
			if (httpSession.getAttribute(SessionHelper.PROFILE_KEY) == null)
				SessionHelper.refreshLocale(httpSession, configuration.getLocale().getCode());
		}

		status.setComplete();

		return ViewHelper.redirectTo("configurationassistancepreedit.htm");
	}

	@RequestMapping("/configurationassistancerestapiconfig.htm")
	public ModelAndView restapi()
	{
		return new ModelAndView("restApiConfigurationUpdate", assistanceService.restapi());
	}

	@RequestMapping("/configurationassistancerestapiedit.htm")
	public ModelAndView restapiedit(@ModelAttribute("restApiConfiguration") RestApiConfiguration configuration) throws ServiceException
	{
		assistanceService.restapiedit(configuration);
		return ViewHelper.redirectTo("configurationassistancepreedit.htm");
	}
}
