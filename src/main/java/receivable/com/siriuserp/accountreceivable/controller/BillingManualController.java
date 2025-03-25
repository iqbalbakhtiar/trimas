package com.siriuserp.accountreceivable.controller;

import com.siriuserp.accounting.form.AccountingForm;
import com.siriuserp.accountreceivable.criteria.BillingFilterCriteria;
import com.siriuserp.accountreceivable.dm.Billing;
import com.siriuserp.accountreceivable.query.BillingManualViewQuery;
import com.siriuserp.accountreceivable.service.BillingManualService;
import com.siriuserp.inventory.dm.Product;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PostalAddress;
import com.siriuserp.sdk.dm.Tax;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;
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

@Controller
@SessionAttributes(value = "billingManual_form", types = AccountingForm.class)
@DefaultRedirect(url = "billingmanualview.htm")
public class BillingManualController extends ControllerBase {
    @Autowired
    private BillingManualService service;

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request)
    {
        binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
        binder.registerCustomEditor(Product.class, modelEditor.forClass(Product.class));
        binder.registerCustomEditor(Tax.class, modelEditor.forClass(Tax.class));
        binder.registerCustomEditor(Currency.class, modelEditor.forClass(Currency.class));
        binder.registerCustomEditor(PostalAddress.class, modelEditor.forClass(PostalAddress.class));
    }

    @RequestMapping("/billingmanualview.htm")
    public ModelAndView view(HttpServletRequest request) throws Exception {
        return new ModelAndView("/accounting/billingManualList", service.view(criteriaFactory.create(request, BillingFilterCriteria.class), BillingManualViewQuery.class));
    }

    @RequestMapping("/billingmanualpreadd.htm")
    public ModelAndView preadd() throws Exception {
        return new ModelAndView("/accounting/billingManualAdd", service.preadd());
    }
    
    @RequestMapping("/billingmanualadd.htm")
	public ModelAndView add(@ModelAttribute("billingManual_form") AccountingForm billingForm, BindingResult result, SessionStatus status) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try {
			service.add(FormHelper.create(Billing.class, billingForm));
			status.setComplete();

			response.store("id", billingForm.getBilling().getId());
		} catch (Exception e) {
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}

    @RequestMapping("/billingmanualpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws Exception {
		return new ModelAndView("/accounting/billingManualUpdate", service.preedit(id));
	}

    @RequestMapping("/billingmanualedit.htm")
	public ModelAndView edit(@ModelAttribute("billingManual_form") AccountingForm form, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try {
			service.edit(FormHelper.update(form.getBilling(), form));
			status.setComplete();

			response.store("id", form.getBilling().getId());
		} catch (Exception e) {
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}
}
