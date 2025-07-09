package com.siriuserp.accountpayable.controller;

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
import com.siriuserp.accountpayable.dm.InvoiceVerification;
import com.siriuserp.accountpayable.dm.PaymentMethodType;
import com.siriuserp.accountpayable.form.PayablesForm;
import com.siriuserp.accountpayable.query.ManualInvoiceVerificationGridViewQuery;
import com.siriuserp.accountpayable.service.ManualInvoiceVerificationService;
import com.siriuserp.inventory.dm.GoodsReceiptItem;
import com.siriuserp.inventory.dm.Product;
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
 * @author Rama Almer Felix
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = "verification_form", types = PayablesForm.class)
@DefaultRedirect(url = "manualinvoiceverificationview.htm")
public class ManualInvoiceVerificationController extends ControllerBase
{
	@Autowired
	private ManualInvoiceVerificationService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(Tax.class, modelEditor.forClass(Tax.class));
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
		binder.registerCustomEditor(Product.class, modelEditor.forClass(Product.class));
		binder.registerCustomEditor(Currency.class, modelEditor.forClass(Currency.class));
		binder.registerCustomEditor(Exchange.class, modelEditor.forClass(Exchange.class));
		binder.registerCustomEditor(GoodsReceiptItem.class, modelEditor.forClass(GoodsReceiptItem.class));
		binder.registerCustomEditor(PaymentMethodType.class, enumEditor.forClass(PaymentMethodType.class));
		binder.registerCustomEditor(ExchangeType.class, enumEditor.forClass(ExchangeType.class));
	}

	@RequestMapping("/manualinvoiceverificationview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/payable/manualInvoiceVerificationList", service.view(criteriaFactory.create(request, InvoiceVerificationFilterCriteria.class), ManualInvoiceVerificationGridViewQuery.class));
	}

	@RequestMapping("/manualinvoiceverificationpreadd.htm")
	public ModelAndView preadd() throws Exception
	{
		return new ModelAndView("/payable/manualInvoiceVerificationAdd", service.preadd());
	}

	@RequestMapping("/manualinvoiceverificationadd.htm")
	public ModelAndView add(@ModelAttribute("verification_form") PayablesForm form, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(FormHelper.create(InvoiceVerification.class, form));
			status.setComplete();

			response.store("id", form.getInvoiceVerification().getId());
		} catch (Exception e)
		{
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}

	@RequestMapping("/manualinvoiceverificationpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/payable/manualInvoiceVerificationUpdate", service.preedit(id));
	}

	@RequestMapping("/manualinvoiceverificationedit.htm")
	public ModelAndView edit(@ModelAttribute("verification_form") PayablesForm form, SessionStatus status) throws Exception
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

//	@RequestMapping("/popupmanualinvoiceverificationview.htm")
//	public ModelAndView popup(HttpServletRequest request, @RequestParam(value = "target", required = false) String target) throws Exception
//	{
//		ModelAndView view = new ModelAndView("/payable-popup/invoicePopup");
//		view.addAllObjects(service.view(criteriaFactory.createPopup(request, InvoiceVerificationFilterCriteria.class), InvoiceVerificationGridViewQuery.class));
//		view.addObject("target", target);
//
//		return view;
//	}

	@RequestMapping("/manualinvoiceverificationprint.htm")
	public ModelAndView print(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/payable/invoiceVerificationPrint", service.preedit(id));
	}

//	@RequestMapping("/popupmanualinvoiceverificationviewjson.htm")
//	public ModelAndView getpopupjson(HttpServletRequest request) throws Exception
//	{
//		return new JSONResponse(service.viewJson(criteriaFactory.create(request, InvoiceVerificationFilterCriteria.class), InvoiceVerificationGridViewQuery.class));
//	}
}
