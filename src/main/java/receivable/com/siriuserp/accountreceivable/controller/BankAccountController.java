package com.siriuserp.accountreceivable.controller;

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

import com.siriuserp.accounting.dm.BankAccount;
import com.siriuserp.accounting.form.AccountingForm;
import com.siriuserp.accountreceivable.criteria.BankAccountFilterCriteria;
import com.siriuserp.accountreceivable.query.BankAccountViewQuery;
import com.siriuserp.accountreceivable.service.BankAccountService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PartyBankAccount;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.view.ViewHelper;
import com.siriuserp.sdk.utility.FormHelper;

import javolution.util.FastMap;

@Controller
@SessionAttributes(value = "bankAccount_form", types = AccountingForm.class)
@DefaultRedirect(url = "bankaccountview.htm")
public class BankAccountController extends ControllerBase
{

	@Autowired
	private BankAccountService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(BankAccount.class, modelEditor.forClass(BankAccount.class));
		binder.registerCustomEditor(PartyBankAccount.class, modelEditor.forClass(PartyBankAccount.class));
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
	}

	@RequestMapping("/bankaccountview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/accounting/bankAccountList", service.view(criteriaFactory.create(request, BankAccountFilterCriteria.class), BankAccountViewQuery.class));
	}

	@RequestMapping("/bankaccountpreadd.htm")
	public ModelAndView preadd()
	{
		return new ModelAndView("/accounting/bankAccountAdd", service.preadd());
	}

	@RequestMapping("/bankaccountadd.htm")
	public ModelAndView add(@ModelAttribute("bankAccount_form") AccountingForm form, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(FormHelper.create(BankAccount.class, form));
			status.setComplete();
		} catch (Exception e)
		{
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getMessage());
		}

		return response;
	}

	@RequestMapping("/bankaccountpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/accounting/bankAccountUpdate", service.preedit(id));
	}

	@RequestMapping("/bankaccountedit.htm")
	public ModelAndView edit(@ModelAttribute("bankAccount_form") AccountingForm form, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(FormHelper.update(form.getBankAccount(), form));
			status.setComplete();
		} catch (Exception e)
		{
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getMessage());
		}

		return response;
	}

	@RequestMapping("/bankaccountdelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws Exception
	{
		service.delete(service.load(id));

		return ViewHelper.redirectTo("bankaccountview.htm");
	}

	@RequestMapping("/popupbankaccountview.htm")
	public ModelAndView show(HttpServletRequest request, @RequestParam("target") String target) throws Exception
	{
		FastMap<String, Object> map = service.view(criteriaFactory.createPopup(request, BankAccountFilterCriteria.class), BankAccountViewQuery.class);
		map.put("target", target);

		return new ModelAndView("/accounting-popup/bankAccountPopup", map);
	}

	@RequestMapping("/popupbankaccountjson.htm")
	public ModelAndView view(@RequestParam("id") Long id) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			response.store("bank", service.load(id));
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}
}
