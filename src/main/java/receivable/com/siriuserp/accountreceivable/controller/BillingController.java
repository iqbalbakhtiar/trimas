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

import com.siriuserp.accounting.form.AccountingForm;
import com.siriuserp.accountreceivable.criteria.BillingFilterCriteria;
import com.siriuserp.accountreceivable.query.Billing4PaymentPopupViewQuery;
import com.siriuserp.accountreceivable.service.BillingService;
import com.siriuserp.sales.query.BillingViewQuery;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.springmvc.JSONResponse;

@Controller
@SessionAttributes(value = "billing_form", types = AccountingForm.class)
@DefaultRedirect(url = "billingview.htm")
public class BillingController extends ControllerBase {

    @Autowired
    private BillingService service;

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request)
    {
        initBinderFactory.initBinder(binder, Party.class, Date.class, Facility.class);
    }

    @RequestMapping("/billingview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/accounting/billingList", service.view(criteriaFactory.create(request, BillingFilterCriteria.class), BillingViewQuery.class));
	}

    @RequestMapping("/billingpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws Exception {
		return new ModelAndView("/accounting/billingUpdate", service.preedit(id));
	}

    @RequestMapping("/billingedit.htm")
	public ModelAndView edit(@ModelAttribute("billing_form") AccountingForm form, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try {
			service.edit(form);
			status.setComplete();

			response.store("id", form.getBilling().getId());
		} catch (Exception e) {
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}

	@RequestMapping("/popupbillingviewjson.htm")
	public ModelAndView getbillings(HttpServletRequest request) throws Exception
	{
		return new JSONResponse(service.viewJson(criteriaFactory.create(request, BillingFilterCriteria.class), Billing4PaymentPopupViewQuery.class));
	}

	@RequestMapping("/billingprint.htm")
	public ModelAndView print(@RequestParam("id") Long id) throws Exception {
		return new ModelAndView("/accounting/billingPrint", service.preedit(id));
	}
}
