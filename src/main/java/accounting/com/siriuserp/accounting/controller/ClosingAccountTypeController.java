/**
 * Nov 27, 2008 11:14:39 AM
 * com.siriuserp.accounting.controller
 * ClosingAccountTypeController.java
 */
package com.siriuserp.accounting.controller;

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

import com.siriuserp.accounting.dm.ClosingAccountType;
import com.siriuserp.accounting.dm.GroupType;
import com.siriuserp.accounting.service.ClosingAccountTypeService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.editor.GenericEnumEditor;
import com.siriuserp.sdk.springmvc.view.ViewHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = { "closingAccountType_add", "closingAccountType_edit" }, types = ClosingAccountType.class)
@DefaultRedirect(url = "closingaccounttypeview.htm")
public class ClosingAccountTypeController extends ControllerBase
{
	@Autowired
	private ClosingAccountTypeService closingAccountTypeService;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(GroupType.class, new GenericEnumEditor(GroupType.class));
	}

	@RequestMapping("/closingaccounttypeview.htm")
	public ModelAndView view(HttpServletRequest request)
	{
		return new ModelAndView("/accounting/closingAccountTypeList", closingAccountTypeService.view(null));
	}

	@RequestMapping("/closingaccounttypepreadd.htm")
	public ModelAndView preadd()
	{
		return new ModelAndView("/accounting/closingAccountTypeAdd", closingAccountTypeService.preadd());
	}

	@RequestMapping("/closingaccounttypeadd.htm")
	public ModelAndView add(@ModelAttribute("closingAccountType_add") ClosingAccountType closingAccountType, BindingResult result, SessionStatus status) throws ServiceException
	{
		closingAccountTypeService.add(closingAccountType);
		status.setComplete();

		return ViewHelper.redirectTo("closingaccounttypeview.htm");
	}

	@RequestMapping("/closingaccounttypepreedit.htm")
	public ModelAndView preedit(@RequestParam("id") String id)
	{
		return new ModelAndView("/accounting/closingAccountTypeUpdate", closingAccountTypeService.preedit(id));
	}

	@RequestMapping("/closingaccounttypeedit.htm")
	public ModelAndView edit(@ModelAttribute("closingAccountType_edit") ClosingAccountType closingAccountType, BindingResult result, SessionStatus status) throws ServiceException
	{
		closingAccountTypeService.edit(closingAccountType);
		status.setComplete();

		return ViewHelper.redirectTo("closingaccounttypeview.htm");
	}

	@RequestMapping("/closingaccounttypedelete.htm")
	public ModelAndView delete(@RequestParam("id") String id) throws ServiceException
	{
		closingAccountTypeService.delete(closingAccountTypeService.load(id));

		return ViewHelper.redirectTo("closingaccounttypeview.htm");
	}
}
