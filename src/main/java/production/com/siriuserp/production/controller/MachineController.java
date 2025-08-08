/**
 * File Name  : MachineController.java
 * Created On : Aug 7, 2025
 * Email	  : iqbal@siriuserp.com
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

import com.siriuserp.production.criteria.MachineFilterCriteria;
import com.siriuserp.production.dm.Machine;
import com.siriuserp.production.form.ProductionForm;
import com.siriuserp.production.query.MachineViewQuery;
import com.siriuserp.production.service.MachineService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.ModelReferenceView;
import com.siriuserp.sdk.springmvc.ResponseStatus;
import com.siriuserp.sdk.springmvc.view.ViewHelper;
import com.siriuserp.sdk.utility.FormHelper;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = "machine_form", types = ProductionForm.class)
@DefaultRedirect(url = "machineview.htm")
public class MachineController extends ControllerBase
{
	@Autowired
	private MachineService service;

	@RequestMapping("/machineview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/production/machineList", service.view(criteriaFactory.create(request, MachineFilterCriteria.class), MachineViewQuery.class));
	}

	@RequestMapping("/machinepreadd.htm")
	public ModelAndView preadd() throws ServiceException
	{
		return new ModelAndView("/production/machineAdd", service.preadd());
	}

	@RequestMapping("/machineadd.htm")
	public ModelAndView add(@ModelAttribute("machine_form") ProductionForm form, BindingResult result, SessionStatus status) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(FormHelper.create(Machine.class, form));
			status.setComplete();

			response.store("id", form.getMachine().getId());
		} catch (Exception e)
		{
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping("machinepreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/production/machineUpdate", service.preedit(id));
	}

	@RequestMapping("/machineedit.htm")
	public ModelAndView edit(@ModelAttribute("machine_form") ProductionForm form, BindingResult result, SessionStatus status) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(FormHelper.update(form.getMachine(), form));
			status.setComplete();

			response.store("id", form.getMachine().getId());
		} catch (Exception e)
		{
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;

	}

	@RequestMapping("/machinedelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws Exception
	{
		service.delete(service.load(id));
		return ViewHelper.redirectTo("machineview.htm");
	}

	@RequestMapping("/popupmachineview.htm")
	public ModelAndView popup(HttpServletRequest request) throws Exception
	{
		return new ModelReferenceView("/production-popup/machinePopup", request.getParameter("ref"), service.view(criteriaFactory.createPopup(request, MachineFilterCriteria.class), MachineViewQuery.class));
	}
}
