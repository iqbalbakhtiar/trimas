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
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.Role;
import com.siriuserp.sdk.dm.User;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.XLSFile;
import com.siriuserp.sdk.springmvc.view.ViewHelper;
import com.siriuserp.sdk.utility.FormHelper;
import com.siriuserp.tools.criteria.UserFilterCriteria;
import com.siriuserp.tools.form.ToolForm;
import com.siriuserp.tools.query.UserGridViewQuery;
import com.siriuserp.tools.service.UserService;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = "user_form", types = User.class)
@DefaultRedirect(url = "userview.htm")
public class UserController extends ControllerBase
{
	@Autowired
	private UserService userService;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
		binder.registerCustomEditor(Role.class, modelEditor.forClass(Role.class));
	}

	@RequestMapping("/userview.htm")
	public ModelAndView view(HttpServletRequest request) throws ServiceException
	{
		return new ModelAndView("/tools/userList", userService.view(criteriaFactory.create(request, UserFilterCriteria.class), UserGridViewQuery.class));
	}

	@RequestMapping("/userpreadd.htm")
	protected ModelAndView preadd() throws ServiceException
	{
		return new ModelAndView("/tools/userAdd", userService.preadd());
	}

	@RequestMapping("/useradd.htm")
	public ModelAndView add(@ModelAttribute("user_form") ToolForm form, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			userService.add(FormHelper.create(User.class, form));
			status.setComplete();
			response.store("id", form.getUser().getId());
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping("/userpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/tools/userUpdate", userService.preedit(id));
	}

	@RequestMapping("/useredit.htm")
	public ModelAndView edit(@ModelAttribute("user_form") ToolForm form, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			userService.edit(FormHelper.update(form.getUser(), form));
			status.setComplete();
			response.store("id", form.getUser().getId());
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping("/userdelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws ServiceException
	{
		userService.delete(userService.load(id));
		return ViewHelper.redirectTo("userview.htm");
	}

	@RequestMapping("/userpreeditpassword.htm")
	public ModelAndView preeditpassword(@RequestParam(value = "id", required = false) Long id) throws Exception
	{
		return new ModelAndView("/tools/changePassword", userService.preedit(id));
	}

	@RequestMapping("/usereditpassword.htm")
	public ModelAndView editpassword(@ModelAttribute("user_form") ToolForm form, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			userService.reset(FormHelper.update(form.getUser(), form));
			status.setComplete();

			response.store("id", form.getUser().getId());
		} catch (Exception e)
		{
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getMessage());
		}

		return response;
	}

	@RequestMapping("/userexport.xls")
	public ModelAndView export(HttpServletRequest request) throws ServiceException
	{
		return new XLSFile("/tools/userPrint2", userService.view(criteriaFactory.create(request, UserFilterCriteria.class), UserGridViewQuery.class));
	}
}
