/**
 * Nov 15, 2008 4:59:04 PM
 * com.siriuserp.tools.controller
 * ModuleGroupController.java
 */
package com.siriuserp.tools.controller;

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

import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.ModuleGroup;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.ResponseStatus;
import com.siriuserp.sdk.springmvc.view.ViewHelper;
import com.siriuserp.tools.criteria.RoleFilterCriteria;
import com.siriuserp.tools.query.ModuleGroupGridViewQuery;
import com.siriuserp.tools.service.ModuleGroupService;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Controller
@SessionAttributes(value = "moduleGroup", types = ModuleGroup.class)
@DefaultRedirect(url = "modulegroupview.htm")
public class ModuleGroupController extends ControllerBase
{
	@Autowired
	private ModuleGroupService moduleGroupService;

	@RequestMapping("/modulegroupview.htm")
	public ModelAndView view(HttpServletRequest request) throws ServiceException
	{
		return new ModelAndView("moduleGroupList", moduleGroupService.preview());
	}

	@RequestMapping("/modulegrouppopup.htm")
	public ModelAndView popup(HttpServletRequest request, @RequestParam("target") String target) throws ServiceException
	{
		ModelAndView view = new ModelAndView("/moduleGroupPopup");
		view.addObject("target", target);
		view.addAllObjects(moduleGroupService.view(criteriaFactory.createPopup(request, RoleFilterCriteria.class), ModuleGroupGridViewQuery.class));

		return view;
	}

	@RequestMapping("/modulegrouppreadd.htm")
	public ModelAndView preadd(@RequestParam(value = "parent", required = false) String parent)
	{
		return new ModelAndView("moduleGroupAdd", moduleGroupService.preadd(parent));
	}

	@RequestMapping("/modulegroupadd.htm")
	public ModelAndView add(@ModelAttribute("moduleGroup") ModuleGroup moduleGroup, BindingResult result, SessionStatus status) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			moduleGroupService.add(moduleGroup);
			status.setComplete();
			response.store("value", "page/modulegrouppreedit.htm?id=" + moduleGroup.getId());
		} catch (Exception e)
		{
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping("/modulegrouppreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id)
	{
		return new ModelAndView("moduleGroupUpdate", moduleGroupService.preedit(id));
	}

	@RequestMapping("/modulegroupedit.htm")
	public ModelAndView edit(@ModelAttribute("moduleGroup") ModuleGroup moduleGroup, BindingResult result, SessionStatus status) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			moduleGroupService.edit(moduleGroup);
			status.setComplete();
			response.store("value", "page/modulegrouppreedit.htm?id=" + moduleGroup.getId());
		} catch (Exception e)
		{
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping("/modulegroupdelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws ServiceException
	{
		moduleGroupService.delete(moduleGroupService.load(id));
		return ViewHelper.redirectTo("modulegroupview.htm");
	}
}