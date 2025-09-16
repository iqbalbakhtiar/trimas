/**
 * 
 */
package com.siriuserp.inventory.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.inventory.criteria.OnHandQuantityFilterCriteria;
import com.siriuserp.inventory.query.OnHandQuantityGridViewQuery;
import com.siriuserp.inventory.query.OnHandQuantityGroupViewQuery;
import com.siriuserp.inventory.service.OnHandQuantityService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;

/**
 * @author ferdinand
 */

@Controller
@DefaultRedirect(url = "onhandquantityview.htm")
public class OnHandQuantityController extends ControllerBase
{
	@Autowired
	private OnHandQuantityService service;

	// Main List View
	@RequestMapping("/onhandquantitygroupview.htm")
	public ModelAndView viewGroup(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/inventory/warehouse-management/onHandQuantityGroupList", service.view(criteriaFactory.create(request, OnHandQuantityFilterCriteria.class), OnHandQuantityGroupViewQuery.class));
	}

	// Second List View
	@RequestMapping("/onhandquantityview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/inventory/warehouse-management/onHandQuantityList", service.view(criteriaFactory.create(request, OnHandQuantityFilterCriteria.class), OnHandQuantityGridViewQuery.class));
	}

	@RequestMapping("/onhandquantityeditview.htm")
	public ModelAndView preedit(HttpServletRequest request) throws ServiceException
	{
		OnHandQuantityFilterCriteria criteria = (OnHandQuantityFilterCriteria) criteriaFactory.create(request, OnHandQuantityFilterCriteria.class);

		return new ModelAndView("/inventory/warehouse-management/onHandQuantityUpdate", service.preedit(criteria));
	}

	@RequestMapping("/onhandquantityviewonhandjson.htm")
	public ModelAndView viewOnHand(HttpServletRequest request) throws ServiceException
	{
		return new JSONResponse(service.viewOnHand((OnHandQuantityFilterCriteria) criteriaFactory.create(request, OnHandQuantityFilterCriteria.class)));
	}
}
