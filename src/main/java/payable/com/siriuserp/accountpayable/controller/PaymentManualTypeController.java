/**
 * File Name  : PaymentManualTypeController.java
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

import com.siriuserp.accounting.dm.GLAccount;
import com.siriuserp.accountpayable.criteria.PaymentFilterCriteria;
import com.siriuserp.accountpayable.dm.PaymentManualType;
import com.siriuserp.accountpayable.form.PayablesForm;
import com.siriuserp.accountpayable.query.PaymentManualTypeViewQuery;
import com.siriuserp.accountpayable.service.PaymentManualTypeService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.view.ViewHelper;
import com.siriuserp.sdk.utility.FormHelper;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = "paymentType_form", types = PayablesForm.class)
@DefaultRedirect(url = "paymentmanualtypeview.htm")
public class PaymentManualTypeController extends ControllerBase
{
	@Autowired
	private PaymentManualTypeService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(GLAccount.class, modelEditor.forClass(GLAccount.class));
	}

	@RequestMapping("/paymentmanualtypeview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/payable/paymentManualTypeList", service.view(criteriaFactory.create(request, PaymentFilterCriteria.class), PaymentManualTypeViewQuery.class));
	}

	@RequestMapping("/paymentmanualtypepreadd.htm")
	public ModelAndView preadd1() throws Exception
	{
		return new ModelAndView("/payable/paymentManualTypeAdd", service.preadd());
	}

	@RequestMapping("/paymentmanualtypeadd.htm")
	public ModelAndView add(@ModelAttribute("paymentType_form") PayablesForm form, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(FormHelper.create(PaymentManualType.class, form));
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

	@RequestMapping("/paymentmanualtypepreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/payable/paymentManualTypeUpdate", service.preedit(id));
	}

	@RequestMapping("/paymentmanualtypeedit.htm")
	public ModelAndView edit(@ModelAttribute("paymentType_form") PayablesForm form, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(FormHelper.update(form.getPaymentManualType(), form));
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

	@RequestMapping("/paymentmanualtypedelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws Exception
	{
		service.delete(service.load(id));
		return ViewHelper.redirectTo("paymentmanualtypeview.htm");
	}

	@RequestMapping("/popuppaymentmanualtypeview.htm")
	public ModelAndView viewItem(HttpServletRequest request) throws ServiceException
	{
		return new ModelAndView("/payable-popup/paymentManualTypePopup", service.view(criteriaFactory.create(request, PaymentFilterCriteria.class), PaymentManualTypeViewQuery.class));
	}
}
