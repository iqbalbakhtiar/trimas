/**
 * Apr 24, 2009 4:16:46 PM
 * com.siriuserp.administration.controller
 * FacilityController.java
 */
package com.siriuserp.administration.controller;

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

import com.siriuserp.administration.criteria.FacilityFilterCriteria;
import com.siriuserp.administration.dm.Geographic;
import com.siriuserp.administration.form.AdministrationForm;
import com.siriuserp.administration.query.FacilityGridViewQuery;
import com.siriuserp.administration.service.FacilityService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.FacilityImplementation;
import com.siriuserp.sdk.dm.FacilityType;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PostalAddress;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.view.ViewHelper;
import com.siriuserp.sdk.utility.FormHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = "facility_form", types = AdministrationForm.class)
public class FacilityController extends ControllerBase
{
	@Autowired
	private FacilityService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
		binder.registerCustomEditor(Geographic.class, modelEditor.forClass(Geographic.class));
		binder.registerCustomEditor(FacilityType.class, modelEditor.forClass(FacilityType.class));
		binder.registerCustomEditor(PostalAddress.class, modelEditor.forClass(PostalAddress.class));
		binder.registerCustomEditor(FacilityImplementation.class, enumEditor.forClass(FacilityImplementation.class));
	}

	@RequestMapping("/facilityview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/administration/facilityList", service.view(criteriaFactory.create(request, FacilityFilterCriteria.class), FacilityGridViewQuery.class));
	}

	@RequestMapping("/facilitypreadd.htm")
	public ModelAndView preadd() throws Exception
	{
		return new ModelAndView("/administration/facilityAdd", service.preadd());
	}

	@RequestMapping("/facilityadd.htm")
	public ModelAndView add(@ModelAttribute("facility_form") AdministrationForm form, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(FormHelper.create(Facility.class, form));
			status.setComplete();

			response.store("id", form.getFacility().getId());
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping("/facilitypreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/administration/facilityUpdate", service.preedit(id));
	}

	@RequestMapping("/facilityedit.htm")
	public ModelAndView edit(@ModelAttribute("facility_form") AdministrationForm form, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(FormHelper.update(form.getFacility(), form));
			status.setComplete();

			response.store("id", form.getFacility().getId());
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping("/facilitydelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws Exception
	{
		service.delete(service.load(id));
		return ViewHelper.redirectTo("facilityview.htm");
	}
}
