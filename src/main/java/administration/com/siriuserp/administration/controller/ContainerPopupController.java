/**
 * May 4, 2009 5:20:23 PM
 * com.siriuserp.popup.controller
 * ContainerPopupController.java
 */
package com.siriuserp.administration.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.administration.criteria.ContainerFilterCriteria;
import com.siriuserp.administration.query.ContainerPopupGridViewQuery;
import com.siriuserp.administration.service.ContainerService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.ModelReferenceView;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
public class ContainerPopupController extends ControllerBase
{
	@Autowired
	private ContainerService service;

	@RequestMapping("/popupcontainerview.htm")
	public ModelAndView bygrid(HttpServletRequest request) throws ServiceException
	{
		return new ModelReferenceView("/inventory-popup/containerPopup", request.getParameter("ref"), service.view(criteriaFactory.createPopup(request, ContainerFilterCriteria.class), ContainerPopupGridViewQuery.class));
	}

	@RequestMapping("/popupcontainerjson.htm")
	public ModelAndView view(@RequestParam("id") Long id) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			response.store("container", service.load(id));
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}
	
	@RequestMapping("/popupcontainerdefaultjson.htm")
	public ModelAndView viewDefault(@RequestParam("facility") Long facility) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			response.store("container", service.loadDefault(facility));
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}
}
