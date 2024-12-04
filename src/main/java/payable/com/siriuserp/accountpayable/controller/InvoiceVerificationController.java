package com.siriuserp.accountpayable.controller;

import com.siriuserp.accountpayable.criteria.InvoiceVerificationFilterCriteria;
import com.siriuserp.accountpayable.form.PaymentForm;
import com.siriuserp.accountpayable.query.InvoiceVerificationGridViewQuery;
import com.siriuserp.accountpayable.service.InvoiceVerificationService;
import com.siriuserp.inventory.dm.GoodsReceiptItem;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.*;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.ResponseStatus;
import com.siriuserp.sdk.utility.FormHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes(value = { "verification_add", "verification_edit"}, types = {PaymentForm.class, InvoiceVerification.class})
@DefaultRedirect(url = "invoiceverificationview.htm")
public class InvoiceVerificationController extends ControllerBase {
    @Autowired
    private InvoiceVerificationService service;

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request){
        binder.registerCustomEditor(Tax.class, modelEditor.forClass(Tax.class));
        binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
        binder.registerCustomEditor(Currency.class, modelEditor.forClass(Currency.class));
        binder.registerCustomEditor(Exchange.class, modelEditor.forClass(Exchange.class));
        binder.registerCustomEditor(GoodsReceiptItem.class, modelEditor.forClass(GoodsReceiptItem.class));
        binder.registerCustomEditor(PaymentMethodType.class, enumEditor.forClass(PaymentMethodType.class));
        binder.registerCustomEditor(ExchangeType.class, enumEditor.forClass(ExchangeType.class));
    }

    @RequestMapping("/invoiceverificationview.htm")
    public ModelAndView view(HttpServletRequest request) throws Exception {
        return new ModelAndView("/payable/invoiceVerificationList", service.view(criteriaFactory.create(request, InvoiceVerificationFilterCriteria.class), InvoiceVerificationGridViewQuery.class));
    }
    
    @RequestMapping("/invoiceverificationpreedit.htm")
	public ModelAndView preedit(@RequestParam("id")Long id) throws Exception {
		return new ModelAndView("/payable/invoiceVerificationUpdate", service.preedit(id));
	}
    
    @RequestMapping("/invoiceverificationedit.htm")
	public ModelAndView edit(@ModelAttribute("verification_edit") PaymentForm form, SessionStatus status) throws Exception {
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(FormHelper.update(form.getInvoiceVerification(), form));
			status.setComplete();

			response.store("id", form.getId());
		} catch (Exception e)
		{
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getMessage());
		}

		return response;
	}
}
