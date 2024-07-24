/**
 * Oct 30, 2008 9:48:32 AM
 * com.siriuserp.administration.controller
 * PartyRelationshipController.java
 */
package com.siriuserp.administration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.administration.service.PartyRelationshipService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PartyRelationship;
import com.siriuserp.sdk.dm.PartyRelationshipType;
import com.siriuserp.sdk.dm.PartyRoleType;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.view.ViewHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = "partyRelationship", types = PartyRelationship.class)
public class PartyRelationshipController extends ControllerBase
{
	@Autowired
	private PartyRelationshipService partyRelationshipService;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
		binder.registerCustomEditor(PartyRoleType.class, modelEditor.forClass(PartyRoleType.class));
		binder.registerCustomEditor(PartyRelationshipType.class, modelEditor.forClass(PartyRelationshipType.class));
	}

	@RequestMapping("/partyrelationshippreadd.htm")
	public ModelAndView preadd(@RequestParam("party") Long party)
	{
		return new ModelAndView("/party/partyRelationshipAdd", partyRelationshipService.preadd(party));
	}

	@RequestMapping("/partyrelationshipadd.htm")
	public ModelAndView add(@ModelAttribute("partyRelationship") PartyRelationship partyRelationship) throws ServiceException
	{
		partyRelationshipService.add(partyRelationship);

		return ViewHelper.redirectTo("partypreedit.htm?id=" + partyRelationship.getPartyFrom().getId());
	}

	@RequestMapping("/partyrelationshipdelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws ServiceException
	{
		PartyRelationship relationship = partyRelationshipService.load(id);
		partyRelationshipService.delete(relationship);

		return ViewHelper.redirectTo("partypreedit.htm?id=" + relationship.getPartyTo().getId());
	}
}
