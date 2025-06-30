package com.siriuserp.accountreceivable.controller;

import com.siriuserp.accounting.form.AccountingForm;
import com.siriuserp.accountreceivable.criteria.BillingFilterCriteria;
import com.siriuserp.accountreceivable.dm.Billing;
import com.siriuserp.accountreceivable.dm.BillingBatch;
import com.siriuserp.inventory.query.BillingBatchViewQuery;
import com.siriuserp.inventory.service.BillingBatchService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.ResponseStatus;
import com.siriuserp.sdk.utility.FormHelper;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@SessionAttributes(value = "billing_batch_form", types = AccountingForm.class)
@DefaultRedirect(url = "billingbatchview.htm")
public class BillingBatchController extends ControllerBase
{
	@Autowired
	private BillingBatchService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		initBinderFactory.initBinder(binder, Billing.class, Date.class, Party.class);
	}

	@RequestMapping("/billingbatchview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/accounting/billingBatchList", service.view(criteriaFactory.create(request, BillingFilterCriteria.class), BillingBatchViewQuery.class));
	}

	@RequestMapping("/billingbatchpreadd.htm")
	public ModelAndView preadd() throws ServiceException
	{
		return new ModelAndView("/accounting/billingBatchAdd", service.preadd());
	}

	@RequestMapping("/billingbatchadd.htm")
	public ModelAndView add(@ModelAttribute("billing_batch_form") AccountingForm form, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(FormHelper.create(BillingBatch.class, form));
			status.setComplete();

			response.store("id", form.getBillingBatch().getId());
		} catch (Exception e)
		{
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}

	@RequestMapping("/billingbatchpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/accounting/billingBatchUpdate", service.preedit(id));
	}

	@RequestMapping("/billingbatchedit.htm")
	public ModelAndView edit(@ModelAttribute("billing_batch_form") AccountingForm form, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(FormHelper.update(form.getBillingBatch(), form));
			status.setComplete();

			response.store("id", form.getBillingBatch().getId());
		} catch (Exception e)
		{
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getMessage());
		}

		return response;
	}

	@RequestMapping("/billingbatchprint.htm")
	public ModelAndView option(@RequestParam("id") Long id, @RequestParam("invType") String invType) throws Exception
	{
		if (invType.equals("1"))
			return new ModelAndView("/accounting/billingBatchPrint", service.preedit(id));
		else
			return new ModelAndView("/accounting/billingBatchPrintReceipt", service.preedit(id));
	}
}
