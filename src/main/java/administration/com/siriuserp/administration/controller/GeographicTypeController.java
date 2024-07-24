/**
 * Feb 2, 2009 11:05:25 AM
 * com.siriuserp.administration.controller
 * GeographicTypeController.java
 */
package com.siriuserp.administration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.administration.dm.GeographicType;
import com.siriuserp.administration.service.GeographicTypeService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.view.ViewHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = { "geographicType_add", "geographicType_edit" }, types = GeographicType.class)
@DefaultRedirect(url = "geotypeview.htm")
public class GeographicTypeController extends ControllerBase
{
	@Autowired
	private GeographicTypeService geographicTypeService;

	@RequestMapping("/geotypeview.htm")
	public ModelAndView view()
	{
		return new ModelAndView("/administration/geographicTypeList", geographicTypeService.view());
	}

	@RequestMapping("/geotypepreadd.htm")
	public ModelAndView preadd()
	{
		return new ModelAndView("/administration/geographicTypeAdd", geographicTypeService.preadd());
	}

	@RequestMapping("/geotypeadd.htm")
	public ModelAndView add(@ModelAttribute("geographicType_add") GeographicType geographicType, BindingResult result, SessionStatus status) throws ServiceException
	{
		geographicTypeService.add(geographicType);
		return ViewHelper.redirectTo("geotypeview.htm");
	}

	@RequestMapping("/geotypepreedit.htm")
	public ModelAndView preedit(@RequestParam("id") String id)
	{
		return new ModelAndView("/administration/geographicTypeUpdate", geographicTypeService.preedit(id));
	}

	@RequestMapping("/geotypeedit.htm")
	public ModelAndView edit(@ModelAttribute("geographicType_edit") GeographicType geographicType, BindingResult result, SessionStatus status) throws ServiceException
	{
		geographicTypeService.edit(geographicType);
		return ViewHelper.redirectTo("geotypeview.htm");
	}

	@RequestMapping("/geotypedelete.htm")
	public ModelAndView delete(@RequestParam("id") String id) throws ServiceException
	{
		geographicTypeService.delete(geographicTypeService.load(id));
		return ViewHelper.redirectTo("geotypeview.htm");
	}
}
