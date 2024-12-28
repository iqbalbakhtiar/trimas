/**
 * 
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

import com.siriuserp.accounting.dm.BankAccount;
import com.siriuserp.accounting.dm.CashBankSchema;
import com.siriuserp.accounting.dm.ClosingAccountType;
import com.siriuserp.accounting.service.CashBankSchemaService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;

/**
 * @author
 * Betsu Brahmana Restu
 * PT. Sirius Indonesia
 * betsu@siriuserp.com
 */

@Controller
@SessionAttributes(value = { "cashbank_schema_add", "cashbank_schema_edit" }, types = CashBankSchema.class)
public class CashBankSchemaController extends ControllerBase
{
	@Autowired
	private CashBankSchemaService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(ClosingAccountType.class, modelEditor.forClass(ClosingAccountType.class));
		binder.registerCustomEditor(BankAccount.class, modelEditor.forClass(BankAccount.class));
	}

	@RequestMapping("/cashbankschemapreadd.htm")
	public ModelAndView preadd(@RequestParam("schema") Long id)
	{
		return new ModelAndView("/accounting/cashBankSchemaAdd", service.preadd(id));
	}

	@RequestMapping("/cashbankschemaadd.htm")
	public ModelAndView add(@ModelAttribute("cashbank_schema_add") CashBankSchema schema, BindingResult result, SessionStatus status) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(schema);
			status.setComplete();
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping("/cashbankschemapreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id)
	{
		return new ModelAndView("/accounting/cashBankSchemaUpdate", service.preedit(id));
	}

	@RequestMapping("/cashbankschemaedit.htm")
	public ModelAndView edit(@ModelAttribute("cashbank_schema_edit") CashBankSchema schema, BindingResult result, SessionStatus status) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(schema);
			status.setComplete();
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}
}
