/**
 * May 4, 2009 3:09:48 PM
 * com.siriuserp.popup.controller
 * FacilityPopupController.java
 */
package com.siriuserp.administration.controller;

import javax.servlet.http.HttpServletRequest;

import com.siriuserp.administration.query.FacilityGridViewQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.administration.criteria.FacilityFilterCriteria;
import com.siriuserp.administration.query.FacilityPopupGirdViewQuery;
import com.siriuserp.administration.service.FacilityService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
public class FacilityPopupController extends ControllerBase
{
	@Autowired
	private FacilityService service;

	@RequestMapping("/popupfacilityview.htm")
	public ModelAndView view(HttpServletRequest request) throws ServiceException
	{
		FacilityFilterCriteria criteria = (FacilityFilterCriteria) criteriaFactory.createPopup(request, FacilityFilterCriteria.class);

		return new ModelAndView("/administration-popup/facilityPopup", service.view(criteria, FacilityGridViewQuery.class));
	}
	
	@RequestMapping("/popupfacilityjson.htm")
	public ModelAndView view(@RequestParam("id") Long id) throws ServiceException
	{
		JSONResponse response = new JSONResponse();
		
		try
		{
			response.store("facility", service.load(id));
		} 
		catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

        return response;
	}
	
	@RequestMapping("/popupfacilityaliasjson.htm")
	public ModelAndView view(@RequestParam("alias") String alias) throws ServiceException
	{
		JSONResponse response = new JSONResponse();
		
		try
		{
			response.store("facility", service.alias("alias", alias));
		} 
		catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

        return response;
	}
	
	@RequestMapping("/popupfacilityjsonview.htm")
	public ModelAndView json(HttpServletRequest request) throws ServiceException
	{
		JSONResponse view = new JSONResponse();
		view.store("facilitys", service.view(criteriaFactory.createPopup(request, FacilityFilterCriteria.class), FacilityPopupGirdViewQuery.class).get("facilitys"));

		return view;
	}
}
