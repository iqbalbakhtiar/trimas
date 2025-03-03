package com.siriuserp.accountpayable.controller;

import com.siriuserp.accounting.dm.BankAccount;
import com.siriuserp.accountpayable.criteria.PaymentFilterCriteria;
import com.siriuserp.accountpayable.dm.InvoiceVerification;
import com.siriuserp.accountpayable.dm.Payable;
import com.siriuserp.accountpayable.dm.Payment;
import com.siriuserp.accountpayable.dm.PaymentMethodType;
import com.siriuserp.accountpayable.form.PayablesForm;
import com.siriuserp.accountpayable.query.PaymentGridViewQuery;
import com.siriuserp.accountpayable.service.PaymentService;
import com.siriuserp.accountreceivable.dm.WriteOffType;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Exchange;
import com.siriuserp.sdk.dm.ExchangeType;
import com.siriuserp.sdk.dm.InvoiceVerification;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.Payable;
import com.siriuserp.sdk.dm.PaymentMethodType;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.utility.FormHelper;
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

import javax.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes(value = { "payment_add", "payment_edit" }, types = Payment.class)
@DefaultRedirect(url = "paymentview.htm")
public class PaymentController extends ControllerBase {
    @Autowired
    private PaymentService service;

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request)
    {
        binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
        binder.registerCustomEditor(Payable.class, modelEditor.forClass(Payable.class));
        binder.registerCustomEditor(Currency.class, modelEditor.forClass(Currency.class));
        binder.registerCustomEditor(Exchange.class, modelEditor.forClass(Exchange.class));
        binder.registerCustomEditor(BankAccount.class, modelEditor.forClass(BankAccount.class));
        binder.registerCustomEditor(InvoiceVerification.class, modelEditor.forClass(InvoiceVerification.class));
        binder.registerCustomEditor(ExchangeType.class, enumEditor.forClass(ExchangeType.class));
        binder.registerCustomEditor(WriteOffType.class, enumEditor.forClass(WriteOffType.class));
        binder.registerCustomEditor(PaymentMethodType.class, enumEditor.forClass(PaymentMethodType.class));
    }

    @RequestMapping("/paymentview.htm")
    public ModelAndView view(HttpServletRequest request) throws Exception
    {
        return new ModelAndView("/payable/paymentList", service.view(criteriaFactory.create(request, PaymentFilterCriteria.class), PaymentGridViewQuery.class));
    }

    @RequestMapping("/paymentpreadd.htm")
    public ModelAndView preadd() throws ServiceException
    {
        return new ModelAndView("/payable/paymentAdd", service.preadd());
    }

    @RequestMapping("/paymentadd.htm")
	public ModelAndView add(@ModelAttribute("payment_add") PayablesForm paymentForm, SessionStatus status) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(FormHelper.create(Payment.class, paymentForm));
			status.setComplete();

			response.store("id", paymentForm.getPayment().getId());
		} catch (Exception e)
		{
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getMessage());
		}

		return response;
	}

    @RequestMapping("/paymentpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws ServiceException
	{
		return new ModelAndView("/payable/paymentUpdate", service.preedit(id));
	}

    @RequestMapping("/paymentedit.htm")
	public ModelAndView edit(@ModelAttribute("payment_edit") Payment payment, SessionStatus status) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(payment);
			status.setComplete();

			response.store("id", payment.getId());
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getMessage());
		}

		return response;
	}
}
