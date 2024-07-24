/**
 * Apr 7, 2006
 * UnitOfMeasureController.java
 */
package com.siriuserp.administration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.administration.service.UnitOfMeasureService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.UnitOfMeasure;
import com.siriuserp.sdk.dm.UnitType;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.view.ViewHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = { "unitOfMeasure_add", "unitOfMeasure_edit" }, types = UnitOfMeasure.class)
public class UnitOfMeasureController extends ControllerBase
{
	@Autowired
	private UnitOfMeasureService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(UnitType.class, enumEditor.forClass(UnitType.class));
	}

	@RequestMapping("/uomview.htm")
	public ModelAndView view()
	{
		return new ModelAndView("/general-setting/unitOfMeasureList", "data", service.loadAllUoM());
	}

	@RequestMapping("/uompreadd.htm")
	public ModelAndView preadd()
	{
		return new ModelAndView("/general-setting/unitOfMeasureAdd", service.preadd());
	}

	@RequestMapping("/uomadd.htm")
	public ModelAndView add(@ModelAttribute("unitOfMeasure_add") UnitOfMeasure unitOfMeasure, SessionStatus status) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(unitOfMeasure);
			status.setComplete();
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}

	@RequestMapping("/uompreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id)
	{
		return new ModelAndView("/general-setting/unitOfMeasureUpdate", service.preedit(id));
	}

	@RequestMapping("/uomedithtm")
	public ModelAndView edit(@ModelAttribute("unitOfMeasure_edit") UnitOfMeasure unitOfMeasure, SessionStatus status) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(unitOfMeasure);
			status.setComplete();

			response.store("id", unitOfMeasure.getId());
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}

	@RequestMapping("/uomdelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws ServiceException
	{
		service.delete(service.load(id));

		return ViewHelper.redirectTo("uomview.htm");
	}
}
