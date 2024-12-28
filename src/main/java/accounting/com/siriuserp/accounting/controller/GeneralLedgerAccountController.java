/**
 * Nov 11, 2008 9:52:24 AM
 * com.siriuserp.accounting.controller
 * GeneralLedgerAccountController.java
 */
package com.siriuserp.accounting.controller;

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

import com.siriuserp.accounting.dm.GLAccount;
import com.siriuserp.accounting.dm.GLAccountType;
import com.siriuserp.accounting.dm.GLCashType;
import com.siriuserp.accounting.dm.GLClosingType;
import com.siriuserp.accounting.dm.GLLevel;
import com.siriuserp.accounting.dm.GLPostingType;
import com.siriuserp.accounting.service.GeneralLedgerAccountService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.editor.GenericEnumEditor;
import com.siriuserp.sdk.springmvc.view.ViewHelper;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = "account", types = GLAccount.class)
@DefaultRedirect(url = "chartofaccountview.htm")
public class GeneralLedgerAccountController extends ControllerBase
{
	@Autowired
	private GeneralLedgerAccountService accountService;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(GLCashType.class, new GenericEnumEditor(GLCashType.class));
		binder.registerCustomEditor(GLClosingType.class, new GenericEnumEditor(GLClosingType.class));
		binder.registerCustomEditor(GLPostingType.class, new GenericEnumEditor(GLPostingType.class));
		binder.registerCustomEditor(GLLevel.class, new GenericEnumEditor(GLLevel.class));
		binder.registerCustomEditor(GLAccountType.class, modelEditor.forClass(GLAccountType.class));
		binder.registerCustomEditor(GLAccount.class, modelEditor.forClass(GLAccount.class));
	}

	@RequestMapping("glaccountpreadd.htm")
	public ModelAndView preadd(@RequestParam("coa") String coa, @RequestParam(value = "parent", required = false) String parent)
	{
		return new ModelAndView("/accounting/glAccountAdd", accountService.preadd(coa, parent));
	}

	@RequestMapping("glaccountadd.htm")
	public ModelAndView add(@ModelAttribute("account") GLAccount account, BindingResult result, SessionStatus status) throws ServiceException
	{
		accountService.add(account);
		status.setComplete();

		return ViewHelper.redirectTo("chartofaccountview.htm");
	}

	@RequestMapping("glaccountpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") String id, @RequestParam("coa") String coa)
	{
		FastMap<String, Object> map = accountService.preedit(id);
		map.put("chartOfAccount", coa);

		return new ModelAndView("/accounting/glAccountUpdate", map);
	}

	@RequestMapping("glaccountedit.htm")
	public ModelAndView edit(@ModelAttribute("account") GLAccount account, BindingResult result, SessionStatus status) throws ServiceException
	{
		accountService.edit(account);
		status.setComplete();

		return ViewHelper.redirectTo("chartofaccountview.htm");
	}

	@RequestMapping("glaccountdelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws ServiceException
	{
		accountService.delete(id);

		return ViewHelper.redirectTo("chartofaccountview.htm");
	}
}
