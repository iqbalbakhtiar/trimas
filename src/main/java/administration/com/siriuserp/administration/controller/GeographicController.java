/**
 * Apr 7, 2006
 * CountryController.java
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

import com.siriuserp.administration.criteria.GeographicFilterCriteria;
import com.siriuserp.administration.dm.Geographic;
import com.siriuserp.administration.dm.GeographicType;
import com.siriuserp.administration.query.GeographicGridViewQuery;
import com.siriuserp.administration.service.GeographicService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.view.ViewHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * Version 1.0
 */

@Controller("geographicController")
@SessionAttributes(value = { "geographic_add", "geographic_edit" }, types = Geographic.class)
public class GeographicController extends ControllerBase
{
	@Autowired
	private GeographicService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(GeographicType.class, modelEditor.forClass(GeographicType.class));
		binder.registerCustomEditor(Geographic.class, modelEditor.forClass(Geographic.class));
	}

	@RequestMapping("/geographicview.htm")
	public ModelAndView view(HttpServletRequest request) throws ServiceException
	{
		if (request.getHeader("Accept").contains("json"))
			return new JSONResponse(service.viewJson(criteriaFactory.create(request, GeographicFilterCriteria.class), GeographicGridViewQuery.class));
		else
			return new ModelAndView("/administration/geographicList", service.view(criteriaFactory.create(request, GeographicFilterCriteria.class), GeographicGridViewQuery.class));
	}

	@RequestMapping("/geographicpreadd.htm")
	public ModelAndView preadd()
	{
		return new ModelAndView("/administration/geographicAdd", service.preadd());
	}

	@RequestMapping("/geographicadd.htm")
	public ModelAndView add(@ModelAttribute("geographic_add") Geographic geographic, BindingResult result, SessionStatus status) throws ServiceException
	{
		service.add(geographic);
		status.setComplete();

		return ViewHelper.redirectTo("geographicview.htm");
	}

	@RequestMapping("/geographicpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") String id)
	{
		return new ModelAndView("/administration/geographicUpdate", service.preedit(id));
	}

	@RequestMapping("/geographicedit.htm")
	public ModelAndView edit(@ModelAttribute("geographic_edit") Geographic geographic, BindingResult result, SessionStatus status) throws ServiceException
	{
		service.edit(geographic);
		status.setComplete();

		return ViewHelper.redirectTo("geographicview.htm");
	}

	@RequestMapping("/geographicdelete.htm")
	public ModelAndView delete(@RequestParam("id") String id) throws ServiceException
	{
		service.delete(service.load(id));

		return ViewHelper.redirectTo("geographicview.htm");
	}
}
