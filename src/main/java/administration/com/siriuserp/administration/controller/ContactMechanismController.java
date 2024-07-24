/**
 * Oct 31, 2008 9:30:11 AM
 * com.siriuserp.administration.controller
 * ContactMechanismController.java
 */
package com.siriuserp.administration.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.administration.service.ContactMechanismService;
import com.siriuserp.sdk.dm.ContactMechanism;
import com.siriuserp.sdk.dm.ContactMechanismType;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.editor.GenericEnumEditor;
import com.siriuserp.sdk.springmvc.view.ViewHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = { "contactMechanism_add", "contactMechanism_edit" }, types = ContactMechanism.class)
public class ContactMechanismController
{
	@Autowired
	private ContactMechanismService contactMechanismService;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(ContactMechanismType.class, new GenericEnumEditor(ContactMechanismType.class));
	}

	@RequestMapping("/contactmechanismpreadd.htm")
	public ModelAndView preadd(@RequestParam("party") String party, @RequestParam(value = "relationshipId", required = false) Long relationshipId, @RequestParam(value = "uri", required = false) String uri)
	{
		ModelAndView view = new ModelAndView("/administration/contactMechanismAdd", contactMechanismService.preadd(party));
		view.addObject("redirectURL", uri);
		view.addObject("relationshipId", relationshipId);

		return view;
	}

	@RequestMapping("/contactmechanismadd.htm")
	public ModelAndView add(@ModelAttribute("contactMechanism_add") ContactMechanism contactMechanism, SessionStatus status)
	{
		JSONResponse response = new JSONResponse();

		try
		{
			contactMechanismService.add(contactMechanism);
			status.setComplete();

			response.store("id", contactMechanism.getParty().getId());
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}

	@RequestMapping("/contactmechanismpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id, @RequestParam(value = "relationshipId", required = false) Long relationshipId, @RequestParam(value = "uri", required = false) String uri)
	{
		ModelAndView view = new ModelAndView("/administration/contactMechanismUpdate", contactMechanismService.preedit(id));
		view.addObject("redirectURL", uri);
		view.addObject("relationshipId", relationshipId);

		return view;
	}

	@RequestMapping("/contactmechanismedit.htm")
	public ModelAndView edit(@ModelAttribute("contactMechanism_edit") ContactMechanism contactMechanism, SessionStatus status)
	{
		JSONResponse response = new JSONResponse();

		try
		{
			contactMechanismService.edit(contactMechanism);
			status.setComplete();

			response.store("id", contactMechanism.getParty().getId());
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}

	@RequestMapping("/contactmechanismdelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id, @RequestParam("party") Long party, @RequestParam(value = "relationshipId", required = false) Long relationshipId, @RequestParam(value = "uri", required = false) String uri) throws Exception
	{
		contactMechanismService.delete(contactMechanismService.load(id));

		if (StringUtils.isNotEmpty(uri))
			return ViewHelper.redirectTo(uri + "?id=" + relationshipId);

		return ViewHelper.redirectTo("partypreedit.htm?id=" + party);
	}
}
