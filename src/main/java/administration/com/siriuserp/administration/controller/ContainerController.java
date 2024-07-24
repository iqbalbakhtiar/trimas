/**
 * Sep 20, 2006 2:45:46 PM
 * net.konsep.sirius.presentation.administration
 * ContainerController.java
 */
package com.siriuserp.administration.controller;

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

import com.siriuserp.administration.service.ContainerService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.ContainerType;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.view.ViewHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = "container", types = Container.class)
@DefaultRedirect(url = "facilityview.htm")
public class ContainerController extends ControllerBase
{
	@Autowired
	private ContainerService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(ContainerType.class, modelEditor.forClass(ContainerType.class));
	}

	@RequestMapping("/containerpreadd.htm")
	public ModelAndView preadd(@RequestParam("id") Long id) throws ServiceException
	{
		return new ModelAndView("/administration/containerAdd", service.preadd(id));
	}

	@RequestMapping("/containerpreaddfromfacility.htm")
	public ModelAndView preaddFromFacility(@RequestParam("id") Long id) throws ServiceException
	{
		return new ModelAndView("/administration/containerAdd", service.preaddFromFacility(id));
	}

	@RequestMapping("/containeradd.htm")
	public ModelAndView add(@ModelAttribute("container") Container container, BindingResult result, SessionStatus status) throws ServiceException
	{
		service.add(container);
		status.setComplete();

		return ViewHelper.redirectTo("gridpreedit.htm?id=" + container.getGrid().getId());
	}

	@RequestMapping("/containerpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws ServiceException
	{
		return new ModelAndView("/administration/containerUpdate", service.preedit(id));
	}

	@RequestMapping("/containeredit.htm")
	public ModelAndView edit(@ModelAttribute("container") Container container, BindingResult result, SessionStatus status) throws ServiceException
	{
		service.edit(container);
		status.setComplete();

		return ViewHelper.redirectTo("facilitypreedit.htm?id=" + container.getGrid().getFacility().getId());
	}

	@RequestMapping("/containerdelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws ServiceException
	{
		Container container = service.load(id);
		service.delete(container);

		return ViewHelper.redirectTo("facilitypreedit.htm?id=" + container.getGrid().getFacility().getId());
	}
}
