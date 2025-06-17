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
import com.siriuserp.inventory.dm.UnitOfMeasure;
import com.siriuserp.production.dm.CostCenter;
import com.siriuserp.production.dm.CostCenterGroup;
import com.siriuserp.production.form.ProductionForm;
import com.siriuserp.production.query.CostCenterGroupGridViewQuery;
import com.siriuserp.production.service.CostCenterGroupService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.ResponseStatus;
import com.siriuserp.sdk.springmvc.view.ViewHelper;
import com.siriuserp.sdk.utility.FormHelper;

/**
 * @author Muhammad Rizal
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = {"costgroup_add", "costgroup_edit"}, types = {ProductionForm.class, CostCenterGroup.class})
@DefaultRedirect(url = "costcentergroupview.htm")
public class CostCenterGroupController extends ControllerBase
{
	@Autowired
	private CostCenterGroupService service;
			
	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(CostCenter.class, modelEditor.forClass(CostCenter.class));
		binder.registerCustomEditor(UnitOfMeasure.class, modelEditor.forClass(UnitOfMeasure.class));
		binder.registerCustomEditor(CostCenterGroup.class, modelEditor.forClass(CostCenterGroup.class));
	}
	
	@RequestMapping("/costcentergroupview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/production/costCenterGroupList", service.view(criteriaFactory.create(request, MasterDataFilterCriteria.class), CostCenterGroupGridViewQuery.class));
	}

	@RequestMapping("/costcentergrouppreadd.htm")
	public ModelAndView preadd() throws ServiceException
	{
		return new ModelAndView("/production/costCenterGroupAdd", service.preadd());
	}

	@RequestMapping("/costcentergroupadd.htm")
	public ModelAndView add(@ModelAttribute("costgroup_add") ProductionForm form, BindingResult result, SessionStatus status) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(FormHelper.create(CostCenterGroup.class, form));
			status.setComplete();

			response.store("id", form.getCostCenterGroup().getId());
		} 
		catch (Exception e)
		{
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping("costcentergrouppreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws Exception 
	{
		return new ModelAndView("/production/costCenterGroupUpdate", service.preedit(id));
	}

	@RequestMapping("/costcentergroupedit.htm")
	public ModelAndView edit(@ModelAttribute("costgroup_edit") CostCenterGroup costCenterGroup, BindingResult result, SessionStatus status) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(costCenterGroup);
			status.setComplete();

			response.store("id", costCenterGroup.getId());
		} 
		catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getMessage());
		}
		
		return response;

	}

	@RequestMapping("/costcentergroupdelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws ServiceException
	{
		service.delete(service.load(id));
		return ViewHelper.redirectTo("costcentergroupview.htm");
	}
}
