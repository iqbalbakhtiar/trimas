package com.siriuserp.accountreceivable.controller;

import java.util.Date;

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
import com.siriuserp.accountpayable.dm.PaymentMethodType;
import com.siriuserp.accountreceivable.criteria.PrepaymentFilterCriteria;
import com.siriuserp.accountreceivable.dm.Prepayment;
import com.siriuserp.accountreceivable.dm.Receipt;
import com.siriuserp.accountreceivable.query.PrepaymentViewQuery;
import com.siriuserp.accountreceivable.service.PrepaymentService;
import com.siriuserp.accountreceivable.service.ReceiptService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.ResponseStatus;
import com.siriuserp.sdk.utility.FormHelper;

/**
 * @author ferdinand
 */

@Controller
@SessionAttributes(value = "prepayment_form", types = AccountingForm.class)
@DefaultRedirect(url = "prepaymentview.htm")
public class PrepaymentController extends ControllerBase
{
	@Autowired
	private PrepaymentService service;
	
	@Autowired
	private ReceiptService receiptService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		initBinderFactory.initBinder(binder, Party.class, Date.class, Facility.class, Currency.class, BankAccount.class, PaymentMethodType.class);
	}
	
	@RequestMapping("/prepaymentview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/receivable/prepaymentList", service.view(criteriaFactory.create(request, PrepaymentFilterCriteria.class), PrepaymentViewQuery.class));
	}
	
	@RequestMapping("/prepaymentpreadd.htm")
	public ModelAndView preadd() throws Exception
	{
		return new ModelAndView("/receivable/prepaymentAdd", service.preadd());
	}
	
	@RequestMapping("/prepaymentadd.htm")
	public ModelAndView add(@ModelAttribute("prepayment_form") AccountingForm form, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(FormHelper.create(Prepayment.class, form));
			status.setComplete();
			
			response.store("id", form.getPrepayment().getId());
		} 
		catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getMessage());
			
			e.printStackTrace();
		}

		return response;
	}
	
	@RequestMapping("/prepaymentpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/receivable/prepaymentUpdate", service.preedit(id));
	}

	@RequestMapping("/prepaymentedit.htm")
	public ModelAndView edit(@ModelAttribute("prepayment_form") AccountingForm form, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(FormHelper.update(form.getPrepayment(), form));
			status.setComplete();

			response.store("id", form.getPrepayment().getId());
		}
		catch (Exception e)
		{
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}
	
	@RequestMapping("/prepaymentpreapply.htm")
	public ModelAndView preapply(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/receivable/prepaymentApply", service.preapply(id));
	}
	
	@RequestMapping("/prepaymentapply.htm")
	public ModelAndView apply(@ModelAttribute("prepayment_form") AccountingForm form, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			receiptService.add(FormHelper.create(Receipt.class, form));
			status.setComplete();
			
			response.store("id", form.getPrepayment().getId());
		} 
		catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}
}
