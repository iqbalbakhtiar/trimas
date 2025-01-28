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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.inventory.criteria.ProductFilterCriteria;
import com.siriuserp.production.dm.Machine;
import com.siriuserp.production.query.MachineGridViewQuery;
import com.siriuserp.production.service.MachineService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Container;
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
@SessionAttributes(value = {"machine_add","machine_edit"}, types = Container.class)
@DefaultRedirect(url = "machineview.htm")
public class MachineController extends ControllerBase
{
	@Autowired
	private MachineService service;
	
	@RequestMapping("/machineview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/production/machineList", service.view(criteriaFactory.create(request, ProductFilterCriteria.class), MachineGridViewQuery.class));
	}

	@RequestMapping("/machinepreadd.htm")
	public ModelAndView preadd() throws ServiceException
	{
		return new ModelAndView("/production/machineAdd", service.preadd());
	}

	@RequestMapping("/machineadd.htm")
	public ModelAndView add(@ModelAttribute("machine_add") Machine machine, BindingResult result, SessionStatus status) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(machine);
			status.setComplete();

			response.store("id", machine.getId());
		} catch (Exception e)
		{
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping("machinepreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws ServiceException
	{
		return new ModelAndView("/production/machineUpdate", service.preedit(id));
	}

	@RequestMapping("/machineedit.htm")
	public ModelAndView edit(@ModelAttribute("machine_edit") Machine machine, BindingResult result, SessionStatus status) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(machine);
			status.setComplete();

			response.store("id", machine.getId());
		} catch (Exception e)
		{
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
		
		return response;

	}

	@RequestMapping("/machinedelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws ServiceException
	{
		service.delete(service.load(id));
		return ViewHelper.redirectTo("machineview.htm");
	}
}
