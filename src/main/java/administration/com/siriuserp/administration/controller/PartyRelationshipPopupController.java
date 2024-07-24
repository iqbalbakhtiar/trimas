/**
 * Mar 27, 2009 2:10:03 PM
 * com.siriuserp.popup.controller
 * PartyRelationshipPopupController.java
 */
package com.siriuserp.administration.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.administration.criteria.PartyRelationshipFilterCriteria;
import com.siriuserp.administration.query.PartyRelationshipPopupGridViewQuery;
import com.siriuserp.administration.service.PartyRelationshipService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
public class PartyRelationshipPopupController extends ControllerBase
{
	@Autowired
	private PartyRelationshipService service;

	@RequestMapping("/popuppartyrelationshipview.htm")
	public ModelAndView view(HttpServletRequest request, @RequestParam("target") String target) throws ServiceException
	{
		ModelAndView view = new ModelAndView("/party/partyRelationshipPopup");
		view.addAllObjects(service.view(criteriaFactory.createPopup(request, PartyRelationshipFilterCriteria.class), PartyRelationshipPopupGridViewQuery.class));
		view.addObject("target", target);

		return view;
	}
	
	@RequestMapping("/popuppartyrelationshipjson.htm")
	public ModelAndView view(@RequestParam("id") Long id) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			response.store("relation", service.load(id));
		}
		catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

        return response;
	}

	@RequestMapping("/popuppartyrelationshipChargeRoleView.htm")
	public ModelAndView view2(HttpServletRequest request, @RequestParam("target") String target) throws ServiceException
	{
		ModelAndView view = new ModelAndView("partyRelationshipPopup");
		view.addAllObjects(service.view(criteriaFactory.createPopup(request, PartyRelationshipFilterCriteria.class), PartyRelationshipPopupGridViewQuery.class));
		view.addObject("target", target);

		return view;
	}
}