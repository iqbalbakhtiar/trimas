package com.siriuserp.accountreceivable.controller;

import com.siriuserp.accountreceivable.criteria.ReceiptFilterCriteria;
import com.siriuserp.accountreceivable.dm.BankAccount;
import com.siriuserp.accountreceivable.dm.Receipt;
import com.siriuserp.accountreceivable.form.AccountingForm;
import com.siriuserp.accountreceivable.query.ReceiptViewQuery;
import com.siriuserp.accountreceivable.service.ReceiptService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.ResponseStatus;
import com.siriuserp.sdk.utility.FormHelper;
import javolution.util.FastMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@SessionAttributes(value = "receipt_form", types = AccountingForm.class)
@DefaultRedirect(url = "receiptview.htm")
public class ReceiptController extends ControllerBase {

    @Autowired
    private ReceiptService service;

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request)
    {
        initBinderFactory.initBinder(binder, Party.class, Date.class, Facility.class, Currency.class, BankAccount.class);
    }

    @RequestMapping("/receiptview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/accounting/receiptList", service.view(criteriaFactory.create(request, ReceiptFilterCriteria.class), ReceiptViewQuery.class));
	}

    @RequestMapping("/receiptpreadd.htm")
	public ModelAndView preadd(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/accounting/receiptAdd", service.preadd());
	}

    @RequestMapping("/receiptadd.htm")
	public ModelAndView add(@ModelAttribute("receipt_form") AccountingForm accountingForm, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try {
			FastMap<String, Object> map = service.add(FormHelper.create(Receipt.class, accountingForm));
			status.setComplete();

			response.store("data", map);
		} catch (Exception e) {
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}

	@RequestMapping("/receiptpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id, @RequestParam(value = "redirecturi", required = false) String redirectUri) throws Exception
	{
		return new ModelAndView("/accounting/receiptUpdate", service.preedit(id, redirectUri));
	}

	@RequestMapping("/receiptedit.htm")
	public ModelAndView edit(@ModelAttribute("receipt_form") AccountingForm form, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(form);
			status.setComplete();

			response.store("id", form.getReceipt().getId());
		} catch (Exception e)
		{
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}
}
