/**
 * File Name  : ReceiptManualController.java
 * Created On : Dec 5, 2023
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountreceivable.controller;

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
import com.siriuserp.accountpayable.dm.PaymentMethodType;
import com.siriuserp.accountreceivable.criteria.ReceiptFilterCriteria;
import com.siriuserp.accountreceivable.dm.Billing;
import com.siriuserp.accountreceivable.dm.ReceiptManualType;
import com.siriuserp.accountreceivable.dm.WriteOffType;
import com.siriuserp.accountreceivable.form.ReceiptManual;
import com.siriuserp.accountreceivable.form.ReceivablesForm;
import com.siriuserp.accountreceivable.query.ReceiptManualViewQuery;
import com.siriuserp.accountreceivable.service.ReceiptManualService;
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
@SessionAttributes(value = "receiptManual_form", types = ReceivablesForm.class)
@DefaultRedirect(url = "receiptmanualview.htm")
public class ReceiptManualController extends ControllerBase
{
	@Autowired
	private ReceiptManualService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
		binder.registerCustomEditor(Billing.class, modelEditor.forClass(Billing.class));
		binder.registerCustomEditor(Currency.class, modelEditor.forClass(Currency.class));
		binder.registerCustomEditor(Exchange.class, modelEditor.forClass(Exchange.class));
		binder.registerCustomEditor(BankAccount.class, modelEditor.forClass(BankAccount.class));
		binder.registerCustomEditor(Facility.class, modelEditor.forClass(Facility.class));
		binder.registerCustomEditor(ReceiptManualType.class, modelEditor.forClass(ReceiptManualType.class));
		binder.registerCustomEditor(PaymentMethodType.class, enumEditor.forClass(PaymentMethodType.class));
		binder.registerCustomEditor(ExchangeType.class, enumEditor.forClass(ExchangeType.class));
		binder.registerCustomEditor(WriteOffType.class, enumEditor.forClass(WriteOffType.class));
	}

	@RequestMapping("/receiptmanualview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/ar/receiptManualList", service.view(criteriaFactory.create(request, ReceiptFilterCriteria.class), ReceiptManualViewQuery.class));
	}

	@RequestMapping("/receiptmanualpreadd.htm")
	public ModelAndView preadd() throws Exception
	{
		return new ModelAndView("/ar/receiptManualAdd", service.preadd());
	}

	@RequestMapping("/receiptmanualadd.htm")
	public ModelAndView add(@ModelAttribute("receiptManual_form") ReceivablesForm receiptForm, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(FormHelper.create(ReceiptManual.class, receiptForm));
			status.setComplete();

			response.store("id", receiptForm.getReceiptManual().getId());
		} catch (Exception e)
		{
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getMessage());
		}

		return response;
	}

	@RequestMapping("/receiptmanualpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/ar/receiptManualUpdate", service.preedit(id));
	}

	@RequestMapping("/receiptmanualedit.htm")
	public ModelAndView edit(@ModelAttribute("receiptManual_form") ReceivablesForm form, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(FormHelper.update(form.getReceiptManual(), form));
			status.setComplete();

			response.store("id", form.getReceiptManual().getId());
		} catch (Exception e)
		{
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getMessage());
		}

		return response;
	}

	@RequestMapping("/receiptmanualrejournal.htm")
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

	@RequestMapping("/receiptmanualdelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws Exception
	{
		service.delete(service.load(id));

		return ViewHelper.redirectTo("receiptmanualview.htm");
	}

	@RequestMapping("/receiptmanualprintoption.htm")
	public ModelAndView printOption(@RequestParam("id") Long id) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("payment", service.load(id));

		return new ModelAndView("/payable/paymentManualPrintOption", map);
	}

	@RequestMapping("/receiptmanualprint.htm")
	public ModelAndView print(@RequestParam("id") Long id, @RequestParam("invType") String invType) throws Exception
	{
		if (id != null && invType != null)
		{
			if (invType.equals("1"))
				return new ModelAndView("/payable/paymentManualPrint", service.preedit(id));
		}

		return ViewHelper.redirectTo("receiptmanualview.htm");
	}

	@RequestMapping("/receiptmanualrecalculatejournal.htm")
	public ModelAndView rejournal(HttpServletRequest request) throws Exception
	{
		service.recalculateJournal(criteriaFactory.create(request, ReceiptFilterCriteria.class));

		return ViewHelper.redirectTo("/page/receiptmanualview.htm");
	}
}
