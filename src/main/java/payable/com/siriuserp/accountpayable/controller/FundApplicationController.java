/**
 * File Name  : FundApplicationController.java
 * Created On : Jul 12, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountpayable.controller;

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

import com.siriuserp.accountpayable.criteria.InvoiceVerificationFilterCriteria;
import com.siriuserp.accountpayable.query.FundApplicationViewQuery;
import com.siriuserp.accountpayable.service.FundApplicationService;
import com.siriuserp.accountreceivable.dm.Billing;
import com.siriuserp.accountreceivable.dm.BillingBatch;
import com.siriuserp.accountreceivable.form.ReceivablesForm;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.ResponseStatus;
import com.siriuserp.sdk.utility.FormHelper;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = "fundApplication_form", types = ReceivablesForm.class)
@DefaultRedirect(url = "fundapplicationview.htm")
public class FundApplicationController extends ControllerBase
{
	@Autowired
	private FundApplicationService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		initBinderFactory.initBinder(binder, Billing.class, Date.class, Party.class);
	}

	@RequestMapping("/fundapplicationview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/payable/fundApplicationList", service.view(criteriaFactory.create(request, InvoiceVerificationFilterCriteria.class), FundApplicationViewQuery.class));
	}

	@RequestMapping("/fundapplicationpreadd1.htm")
	public ModelAndView preadd1() throws Exception
	{
		return new ModelAndView("/payable/fundApplicationAdd1", service.preadd1());
	}

	@RequestMapping("/fundapplicationpreadd2.htm")
	public ModelAndView preadd2() throws Exception
	{
		return new ModelAndView("/payable/fundApplicationAdd1", service.preadd1());
	}

	@RequestMapping("/fundapplicationadd.htm")
	public ModelAndView add(@ModelAttribute("fundApplication_form") ReceivablesForm form, BindingResult result, SessionStatus status) throws Exception
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

	@RequestMapping("/fundapplicationpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/receivable/billingBatchUpdate", service.preedit(id));
	}

	@RequestMapping("/fundapplicationedit.htm")
	public ModelAndView edit(@ModelAttribute("fundApplication_form") ReceivablesForm form, SessionStatus status) throws Exception
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

	@RequestMapping("/fundapplicationprint.htm")
	public ModelAndView option(@RequestParam("id") Long id, @RequestParam("invType") String invType) throws Exception
	{
		return new ModelAndView("/receivable/billingBatchPrint", service.preedit(id));
	}
}
