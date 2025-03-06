/**
 * File Name  : InvoiceVerificationController.java
 * Created On : Feb 26, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountpayable.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.siriuserp.accountpayable.dm.PaymentMethodType;
import com.siriuserp.accountpayable.form.PaymentForm;
import com.siriuserp.accountpayable.query.InvoiceVerificationGridViewQuery;
import com.siriuserp.accountpayable.service.InvoiceVerificationService;
import com.siriuserp.inventory.dm.GoodsReceiptItem;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Exchange;
import com.siriuserp.sdk.dm.ExchangeType;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.Tax;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.ResponseStatus;
import com.siriuserp.sdk.utility.FormHelper;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = "verification_form", types = PaymentForm.class)
@DefaultRedirect(url = "invoiceverificationview.htm")
public class InvoiceVerificationController extends ControllerBase
{
	@Autowired
	private InvoiceVerificationService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(Tax.class, modelEditor.forClass(Tax.class));
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
		binder.registerCustomEditor(Currency.class, modelEditor.forClass(Currency.class));
		binder.registerCustomEditor(Exchange.class, modelEditor.forClass(Exchange.class));
		binder.registerCustomEditor(GoodsReceiptItem.class, modelEditor.forClass(GoodsReceiptItem.class));
		binder.registerCustomEditor(PaymentMethodType.class, enumEditor.forClass(PaymentMethodType.class));
		binder.registerCustomEditor(ExchangeType.class, enumEditor.forClass(ExchangeType.class));
	}

	@RequestMapping("/invoiceverificationview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/payable/invoiceVerificationList", service.view(criteriaFactory.create(request, InvoiceVerificationFilterCriteria.class), InvoiceVerificationGridViewQuery.class));
	}

	@RequestMapping("/invoiceverificationpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/payable/invoiceVerificationUpdate", service.preedit(id));
	}

	@RequestMapping("/invoiceverificationedit.htm")
	public ModelAndView edit(@ModelAttribute("verification_form") PaymentForm form, SessionStatus status) throws Exception
	{
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

	@RequestMapping("/popupinvoiceverificationview.htm")
	public ModelAndView popup(HttpServletRequest request, @RequestParam(value = "target", required = false) String target) throws Exception
	{
		ModelAndView view = new ModelAndView("/payable-popup/invoicePopup");
		view.addAllObjects(service.view(criteriaFactory.createPopup(request, InvoiceVerificationFilterCriteria.class), InvoiceVerificationGridViewQuery.class));
		view.addObject("target", target);

		return view;
	}

	@RequestMapping("/invoiceverificationprint.htm")
	public ModelAndView print(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/payable/invoiceVerificationPrint", service.preedit(id));
	}
}
