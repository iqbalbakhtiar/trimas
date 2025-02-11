/**
 * Sep 20, 2006 2:45:46 PM
 * net.konsep.sirius.presentation.administration
 * ContainerController.java
 */
package com.siriuserp.production.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.inventory.criteria.MasterDataFilterCriteria;
import com.siriuserp.production.dm.CostCenter;
import com.siriuserp.production.dm.CostCenterType;
import com.siriuserp.production.query.CostCenterGridViewQuery;
import com.siriuserp.production.service.CostCenterService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.ResponseStatus;
import com.siriuserp.sdk.springmvc.view.ViewHelper;

/**
 * @author Muhammad Rizal
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = {"costcenter_add","costcenter_edit"}, types = CostCenter.class)
@DefaultRedirect(url = "costcenterview.htm")
public class CostCenterController extends ControllerBase
{
	@Autowired
	private CostCenterService service;
			
	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(CostCenterType.class, enumEditor.forClass(CostCenterType.class));
	}
	
	@RequestMapping("/costcenterview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/production/costCenterList", service.view(criteriaFactory.create(request, MasterDataFilterCriteria.class), CostCenterGridViewQuery.class));
	}

	@RequestMapping("/costcenterpreadd.htm")
	public ModelAndView preadd() throws ServiceException
	{
		return new ModelAndView("/production/costCenterAdd", service.preadd());
	}

	@RequestMapping("/costcenteradd.htm")
	public ModelAndView add(@ModelAttribute("costcenter_add") CostCenter costCenter, BindingResult result, SessionStatus status) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(costCenter);
			status.setComplete();

			response.store("id", costCenter.getId());
		} catch (Exception e)
		{
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping("costcenterpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws ServiceException
	{
		return new ModelAndView("/production/costCenterUpdate", service.preedit(id));
	}

	@RequestMapping("/costcenteredit.htm")
	public ModelAndView edit(@ModelAttribute("costcenter_edit") CostCenter costCenter, BindingResult result, SessionStatus status) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(costCenter);
			status.setComplete();

			response.store("id", costCenter.getId());
		} catch (Exception e)
		{
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
		
		return response;

	}

	@RequestMapping("/costcenterdelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws ServiceException
	{
		service.delete(service.load(id));
		return ViewHelper.redirectTo("costcenterview.htm");
	}
}
