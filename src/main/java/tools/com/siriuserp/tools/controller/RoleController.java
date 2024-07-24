package com.siriuserp.tools.controller;

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

import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.AccessType;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.view.ViewHelper;
import com.siriuserp.tools.adapter.RoleUIAdapter;
import com.siriuserp.tools.criteria.RoleFilterCriteria;
import com.siriuserp.tools.service.RoleService;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = "adapter", types = RoleUIAdapter.class)
@DefaultRedirect(url = "roleview.htm")
public class RoleController extends ControllerBase
{
	@Autowired
	private RoleService roleService;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(AccessType.class, modelEditor.forClass(AccessType.class));
	}

	@RequestMapping("/roleview.htm")
	public ModelAndView view(HttpServletRequest request) throws ServiceException
	{
		return new ModelAndView("/tools/roleList", roleService.view(criteriaFactory.create(request, RoleFilterCriteria.class)));
	}

	@RequestMapping("rolepreadd.htm")
	public ModelAndView preadd()
	{
		return new ModelAndView("/tools/roleAdd", roleService.preadd());
	}

	@RequestMapping("roleadd.htm")
	public ModelAndView add(@ModelAttribute("adapter") RoleUIAdapter adapter, BindingResult result, SessionStatus status) throws ServiceException
	{
		roleService.add(adapter.getRole(), adapter);
		status.setComplete();

		return ViewHelper.redirectTo("roleview.htm");
	}

	@RequestMapping("roleprecopy.htm")
	public ModelAndView precopy(@ModelAttribute("adapter") RoleUIAdapter adapter, BindingResult result, SessionStatus status)
	{
		return new ModelAndView("/tools/roleAdd2", roleService.precopy(adapter));
	}

	@RequestMapping("rolecopy.htm")
	public ModelAndView copy(@ModelAttribute("adapter") RoleUIAdapter adapter, BindingResult result, SessionStatus status) throws ServiceException
	{
		roleService.copy(adapter.getRole(), adapter);
		status.setComplete();

		return ViewHelper.redirectTo("rolepreedit.htm?id=" + adapter.getRole().getId());
	}

	@RequestMapping("rolepreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id)
	{
		return new ModelAndView("/tools/roleUpdate", roleService.preedit(id));
	}

	@RequestMapping("roleedit.htm")
	public ModelAndView edit(@ModelAttribute("adapter") RoleUIAdapter adapter, BindingResult result, SessionStatus status) throws ServiceException
	{
		roleService.edit(adapter);
		status.setComplete();

		return ViewHelper.redirectTo("roleview.htm");
	}

	@RequestMapping("roledelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws ServiceException
	{
		roleService.delete(roleService.load(id));

		return ViewHelper.redirectTo("roleview.htm");
	}
}
