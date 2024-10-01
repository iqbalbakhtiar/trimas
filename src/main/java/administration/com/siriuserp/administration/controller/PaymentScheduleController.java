package com.siriuserp.administration.controller;

import org.apache.commons.lang.StringUtils;
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

import com.siriuserp.administration.service.PaymentScheduleService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.PaymentMethod;
import com.siriuserp.sdk.dm.PaymentSchedule;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.view.ViewHelper;

@Controller
@SessionAttributes(value = { "paymentSchedule_add", "paymentSchedule_edit" }, types = PaymentSchedule.class)
public class PaymentScheduleController extends ControllerBase {
	
	@Autowired
	private PaymentScheduleService paymentScheduleService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(PaymentMethod.class, modelEditor.forClass(PaymentMethod.class));
	}
	
	@RequestMapping("/paymentschedulepreadd.htm")
	public ModelAndView preadd(@RequestParam("payment") String paymentMethod, @RequestParam(value = "relationshipId", required = false) Long relationshipId, @RequestParam(value = "uri", required = false) String uri) {
		ModelAndView view = new ModelAndView("/administration/paymentScheduleAdd", paymentScheduleService.preadd(paymentMethod));
		view.addObject("redirectURL", uri);
		view.addObject("relationshipId", relationshipId);

		return view;
	}
	
	@RequestMapping("/paymentscheduleadd.htm")
	public ModelAndView add(@ModelAttribute("paymentSchedule_add") PaymentSchedule paymentSchedule, SessionStatus status)
	{
		JSONResponse response = new JSONResponse();

		try
		{
			paymentScheduleService.add(paymentSchedule);
			status.setComplete();
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}
	
	@RequestMapping("/paymentschedulepreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id, @RequestParam(value = "relationshipId", required = false) Long relationshipId, @RequestParam(value = "uri", required = false) String uri)
	{
		ModelAndView view = new ModelAndView("/administration/paymentScheduleUpdate", paymentScheduleService.preedit(id));
		view.addObject("redirectURL", uri);
		view.addObject("relationshipId", relationshipId);

		return view;
	}
	
	@RequestMapping("/paymentscheduleedit.htm")
	public ModelAndView edit(@ModelAttribute("paymentSchedule_edit") PaymentSchedule paymentSchedule, SessionStatus status)
	{
		JSONResponse response = new JSONResponse();

		try
		{
			paymentScheduleService.edit(paymentSchedule);
			status.setComplete();
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}
	
	@RequestMapping("/paymentscheduledelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id, @RequestParam("party") Long party, @RequestParam(value = "relationshipId", required = false) Long relationshipId, @RequestParam(value = "uri", required = false) String uri) throws Exception
	{
		paymentScheduleService.delete(id);

		if (StringUtils.isNotEmpty(uri))
			return ViewHelper.redirectTo(uri + "?id=" + relationshipId);

		return ViewHelper.redirectTo("partypreedit.htm?id=" + party);
	}
}
