/**
 * Nov 12, 2008 3:58:33 PM
 * com.siriuserp.popup
 * OrganizationStructurePopup.java
 */
package com.siriuserp.administration.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.administration.criteria.PartyFilterCriteria;
import com.siriuserp.administration.query.CompanyStructureStandardGridViewQuery;
import com.siriuserp.administration.service.CompanyStructureService;
import com.siriuserp.administration.service.PartyService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.ModelReferenceView;
import com.siriuserp.tools.query.RoleBasedOrganizationQuery;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
public class CompanyStructurePopupController extends ControllerBase
{
	@Autowired
	private CompanyStructureService service;

	@Autowired
	private PartyService partyService;

	@RequestMapping("/popupcompanystructureview.htm")
	public ModelAndView show(HttpServletRequest request) throws ServiceException
	{
		return new ModelAndView("/administration-popup/companyStructurePopup", service.view(criteriaFactory.createPopup(request, PartyFilterCriteria.class), CompanyStructureStandardGridViewQuery.class));
	}

	@RequestMapping("/popupcompanystructurerolebasedview.htm")
	public ModelAndView showrolebased(HttpServletRequest request) throws ServiceException
	{
		return new ModelReferenceView("/administration-popup/roleBasedCompanyStructurePopup", request.getParameter("ref"), service.view(criteriaFactory.createPopup(request, PartyFilterCriteria.class), RoleBasedOrganizationQuery.class));
	}

	@RequestMapping("/popupcompanystructurerolebasedjson.htm")
	public ModelAndView view(@RequestParam("id") Long id) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			response.store("party", partyService.load(id));
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}
}
