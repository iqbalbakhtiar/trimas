/**
 * File Name  : PaymentManualController.java
 * Created On : Oct 17, 2023
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

import com.siriuserp.accounting.dm.BankAccount;
import com.siriuserp.accountpayable.criteria.PaymentFilterCriteria;
import com.siriuserp.accountpayable.dm.Payable;
import com.siriuserp.accountpayable.dm.PaymentManual;
import com.siriuserp.accountpayable.dm.PaymentManualType;
import com.siriuserp.accountpayable.dm.PaymentMethodType;
import com.siriuserp.accountpayable.form.PayablesForm;
import com.siriuserp.accountpayable.query.PaymentManualViewQuery;
import com.siriuserp.accountpayable.service.PaymentManualService;
import com.siriuserp.accountreceivable.dm.WriteOffType;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Exchange;
import com.siriuserp.sdk.dm.ExchangeType;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.view.ViewHelper;
import com.siriuserp.sdk.utility.FormHelper;

import javolution.util.FastMap;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = "paymentManual_form", types = PayablesForm.class)
@DefaultRedirect(url = "paymentmanualview.htm")
public class PaymentManualController extends ControllerBase
{
	@Autowired
	private PaymentManualService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
		binder.registerCustomEditor(Payable.class, modelEditor.forClass(Payable.class));
		binder.registerCustomEditor(Currency.class, modelEditor.forClass(Currency.class));
		binder.registerCustomEditor(Exchange.class, modelEditor.forClass(Exchange.class));
		binder.registerCustomEditor(BankAccount.class, modelEditor.forClass(BankAccount.class));
		binder.registerCustomEditor(Facility.class, modelEditor.forClass(Facility.class));
		binder.registerCustomEditor(PaymentManualType.class, modelEditor.forClass(PaymentManualType.class));
		binder.registerCustomEditor(PaymentMethodType.class, enumEditor.forClass(PaymentMethodType.class));
		binder.registerCustomEditor(ExchangeType.class, enumEditor.forClass(ExchangeType.class));
		binder.registerCustomEditor(WriteOffType.class, enumEditor.forClass(WriteOffType.class));
	}

	@RequestMapping("/paymentmanualview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/payable/paymentManualList", service.view(criteriaFactory.create(request, PaymentFilterCriteria.class), PaymentManualViewQuery.class));
	}

	@RequestMapping("/paymentmanualpreadd.htm")
	public ModelAndView preadd() throws Exception
	{
		return new ModelAndView("/payable/paymentManualAdd", service.preadd());
	}

	@RequestMapping("/paymentmanualadd.htm")
	public ModelAndView add(@ModelAttribute("paymentManual_form") PayablesForm paymentForm, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(FormHelper.create(PaymentManual.class, paymentForm));
			status.setComplete();

			response.store("id", paymentForm.getPaymentManual().getId());
		} catch (Exception e)
		{
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getMessage());
		}

		return response;
	}

	@RequestMapping("/paymentmanualpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/payable/paymentManualUpdate", service.preedit(id));
	}

	@RequestMapping("/paymentmanualedit.htm")
	public ModelAndView edit(@ModelAttribute("paymentManual_form") PayablesForm form, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(FormHelper.update(form.getPaymentManual(), form));
			status.setComplete();

			response.store("id", form.getPaymentManualType().getId());
		} catch (Exception e)
		{
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getMessage());
		}

		return response;
	}

	@RequestMapping("/paymentmanualrejournal.htm")
	public ModelAndView rejournal(Long id) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.rejournal(service.load(id));
			response.store("id", id);
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping("/paymentmanualdelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws Exception
	{
		service.delete(service.load(id));

		return ViewHelper.redirectTo("paymentmanualview.htm");
	}

	@RequestMapping("/paymentmanualprintoption.htm")
	public ModelAndView printOption(@RequestParam("id") Long id) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("payment", service.load(id));

		return new ModelAndView("/payable/paymentManualPrintOption", map);
	}

	@RequestMapping("/paymentmanualprint.htm")
	public ModelAndView print(@RequestParam("id") Long id, @RequestParam("invType") String invType) throws Exception
	{
		if (id != null && invType != null)
		{
			if (invType.equals("1"))
				return new ModelAndView("/payable/paymentManualPrint", service.preedit(id));
		}

		return ViewHelper.redirectTo("paymentmanualview.htm");
	}

	@RequestMapping("/paymentmanualrecalculatejournal.htm")
	public ModelAndView rejournal(HttpServletRequest request) throws Exception
	{
		service.recalculateJournal(criteriaFactory.create(request, PaymentFilterCriteria.class));

		return ViewHelper.redirectTo("/page/paymentmanualview.htm");
	}
}
