/**
 * Dec 21, 2009 2:58:19 PM
 * com.siriuserp.accounting.controller
 * TaxAccountSchemaController.java
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
import com.siriuserp.accounting.dm.TaxAccountSchema;
import com.siriuserp.accounting.service.TaxAccountSchemaService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Tax;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.view.ViewHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = "accountSchema", types = TaxAccountSchema.class)
public class TaxAccountSchemaController extends ControllerBase
{
	@Autowired
	private TaxAccountSchemaService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(Tax.class, modelEditor.forClass(Tax.class));
		binder.registerCustomEditor(GLAccount.class, modelEditor.forClass(GLAccount.class));
	}

	@RequestMapping("/taxaccountschemapreadd.htm")
	public ModelAndView preadd(@RequestParam("id") Long id)
	{
		return new ModelAndView("/accounting/taxAccountSchemaAdd", service.preadd(id));
	}

	@RequestMapping("/taxaccountschemaadd.htm")
	public ModelAndView add(@ModelAttribute("accountSchema") TaxAccountSchema accountSchema, BindingResult result, SessionStatus status) throws ServiceException
	{
		service.add(accountSchema);
		status.setComplete();

		return ViewHelper.redirectTo("accountingschemapreedit.htm?id=" + accountSchema.getAccountingSchema().getId());
	}

	@RequestMapping("/taxaccountschemapreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id)
	{
		return new ModelAndView("/accounting/taxAccountSchemaUpdate", service.preedit(id));
	}

	@RequestMapping("/taxaccountschemaedit.htm")
	public ModelAndView edit(@ModelAttribute("accountSchema") TaxAccountSchema accountSchema, BindingResult result, SessionStatus status) throws ServiceException
	{
		service.edit(accountSchema);
		status.setComplete();

		return ViewHelper.redirectTo("accountingschemapreedit.htm?id=" + accountSchema.getAccountingSchema().getId());
	}

	@RequestMapping("/taxaccountschemadelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws ServiceException
	{
		TaxAccountSchema accountSchema = service.load(id);
		service.delete(accountSchema);

		return ViewHelper.redirectTo("accountingschemapreedit.htm?id=" + accountSchema.getAccountingSchema().getId());
	}
}
