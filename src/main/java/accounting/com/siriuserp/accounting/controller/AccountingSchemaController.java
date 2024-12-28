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

import com.siriuserp.accounting.criteria.AccountingSchemaFilterCriteria;
import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.accounting.dm.AccountingSchema;
import com.siriuserp.accounting.dm.ChartOfAccount;
import com.siriuserp.accounting.dm.GLAccount;
import com.siriuserp.accounting.service.AccountingSchemaService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Level;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.exceptions.DataAdditionException;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.editor.GenericEnumEditor;
import com.siriuserp.sdk.springmvc.view.ViewHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = { "accountingSchema_add", "accountingSchema_edit" }, types = AccountingSchema.class)
@DefaultRedirect(url = "accountingschemaview.htm")
public class AccountingSchemaController extends ControllerBase
{
	@Autowired
	private AccountingSchemaService accountingSchemaService;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(Level.class, new GenericEnumEditor(Level.class));
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
		binder.registerCustomEditor(AccountingPeriod.class, modelEditor.forClass(AccountingPeriod.class));
		binder.registerCustomEditor(ChartOfAccount.class, modelEditor.forClass(ChartOfAccount.class));
		binder.registerCustomEditor(GLAccount.class, modelEditor.forClass(GLAccount.class));
	}

	@RequestMapping("/accountingschemaview.htm")
	public ModelAndView view(HttpServletRequest request) throws ServiceException
	{
		return new ModelAndView("/accounting/accountingSchemaList", accountingSchemaService.view(criteriaFactory.create(request, AccountingSchemaFilterCriteria.class)));
	}

	@RequestMapping("/accountingschemapreadd.htm")
	public ModelAndView preadd()
	{
		return new ModelAndView("/accounting/accountingSchemaAdd", accountingSchemaService.preadd());
	}

	@RequestMapping("/accountingschemaadd.htm")
	public ModelAndView add(@ModelAttribute("accountingSchema_add") AccountingSchema schema, BindingResult result, SessionStatus status) throws ServiceException
	{
		accountingSchemaService.add(schema);
		status.setComplete();

		return ViewHelper.redirectTo("accountingschemaview.htm");
	}

	@RequestMapping("/accountingschemapreedit.htm")
	public ModelAndView preedit(@RequestParam("id") String id) throws DataAdditionException
	{
		return new ModelAndView("/accounting/accountingSchemaUpdate", accountingSchemaService.preedit(id));
	}

	@RequestMapping("/accountingschemaedit.htm")
	public ModelAndView edit(@ModelAttribute("accountingSchema_edit") AccountingSchema schema, BindingResult result, SessionStatus status) throws ServiceException
	{
		accountingSchemaService.edit(schema);
		status.setComplete();

		return ViewHelper.redirectTo("accountingschemaview.htm");
	}

	@RequestMapping("/accountingschemadelete.htm")
	public ModelAndView delete(@RequestParam("id") String id) throws ServiceException
	{
		accountingSchemaService.delete(accountingSchemaService.load(id));
		return ViewHelper.redirectTo("accountingschemaview.htm");
	}
}
