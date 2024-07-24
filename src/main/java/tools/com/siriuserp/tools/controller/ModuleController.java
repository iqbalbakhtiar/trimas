/**
 * Nov 16, 2008 5:48:49 PM
 * com.siriuserp.tools.controller
 * ModuleController.java
 */
package com.siriuserp.tools.controller;

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

import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Module;
import com.siriuserp.sdk.dm.ModuleGroup;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.ResponseStatus;
import com.siriuserp.sdk.springmvc.view.ViewHelper;
import com.siriuserp.tools.service.ModuleService;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = "module", types = Module.class)
public class ModuleController extends ControllerBase
{
	@Autowired
	private ModuleService moduleService;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(ModuleGroup.class, modelEditor.forClass(ModuleGroup.class));
	}

	@RequestMapping("/modulepreadd.htm")
	public ModelAndView preadd(@RequestParam("id") Long id)
	{
		return new ModelAndView("moduleAdd", moduleService.preadd(id));
	}

	@RequestMapping("/moduleadd.htm")
	public ModelAndView add(@ModelAttribute("module") Module module, BindingResult result, SessionStatus status) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			moduleService.add(module);
			status.setComplete();
			response.store("value", "page/modulepreedit.htm?id=" + module.getId());
		} catch (Exception e)
		{
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping("/modulepreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id)
	{
		return new ModelAndView("moduleUpdate", moduleService.preedit(id));
	}

	@RequestMapping("/moduleedit.htm")
	public ModelAndView edit(@ModelAttribute("module") Module module, BindingResult result, SessionStatus status) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			moduleService.edit(module);
			status.setComplete();
			response.store("value", "page/modulepreedit.htm?id=" + module.getId());
		} catch (Exception e)
		{
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping("/moduledelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws Exception
	{
		moduleService.delete(moduleService.load(id));

		return ViewHelper.redirectTo("modulegroupview.htm");
	}
}
