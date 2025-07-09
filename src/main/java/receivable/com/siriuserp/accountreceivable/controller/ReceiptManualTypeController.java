/**
 * File Name  : ReceiptManualTypeController.java
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

import com.siriuserp.accounting.dm.GLAccount;
import com.siriuserp.accountreceivable.criteria.ReceiptFilterCriteria;
import com.siriuserp.accountreceivable.dm.ReceiptManualType;
import com.siriuserp.accountreceivable.form.ReceivablesForm;
import com.siriuserp.accountreceivable.query.ReceiptManualTypeViewQuery;
import com.siriuserp.accountreceivable.service.ReceiptManualTypeService;
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
@SessionAttributes(value = "receiptType_form", types = ReceivablesForm.class)
@DefaultRedirect(url = "receiptmanualtypeview.htm")
public class ReceiptManualTypeController extends ControllerBase
{
	@Autowired
	private ReceiptManualTypeService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(GLAccount.class, modelEditor.forClass(GLAccount.class));
	}

	@RequestMapping("/receiptmanualtypeview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/receivable/receiptManualTypeList", service.view(criteriaFactory.create(request, ReceiptFilterCriteria.class), ReceiptManualTypeViewQuery.class));
	}

	@RequestMapping("/receiptmanualtypepreadd.htm")
	public ModelAndView preadd1() throws Exception
	{
		return new ModelAndView("/receivable/receiptManualTypeAdd", service.preadd());
	}

	@RequestMapping("/receiptmanualtypeadd.htm")
	public ModelAndView add(@ModelAttribute("receiptType_form") ReceivablesForm form, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(FormHelper.create(ReceiptManualType.class, form));
			status.setComplete();

			response.store("id", form.getReceiptManualType().getId());
		} catch (Exception e)
		{
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getMessage());
		}

		return response;
	}

	@RequestMapping("/receiptmanualtypepreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/receivable/receiptManualTypeUpdate", service.preedit(id));
	}

	@RequestMapping("/receiptmanualtypeedit.htm")
	public ModelAndView edit(@ModelAttribute("receiptType_form") ReceivablesForm form, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(FormHelper.update(form.getReceiptManualType(), form));
			status.setComplete();

			response.store("id", form.getReceiptManualType().getId());
		} catch (Exception e)
		{
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getMessage());
		}

		return response;
	}

	@RequestMapping("/receiptmanualtypedelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws Exception
	{
		service.delete(service.load(id));
		return ViewHelper.redirectTo("receiptmanualtypeview.htm");
	}

	@RequestMapping("/popupreceiptmanualtypeview.htm")
	public ModelAndView viewItem(HttpServletRequest request) throws ServiceException
	{
		return new ModelAndView("/receivable-popup/receiptManualTypePopup", service.view(criteriaFactory.create(request, ReceiptFilterCriteria.class), ReceiptManualTypeViewQuery.class));
	}
}
