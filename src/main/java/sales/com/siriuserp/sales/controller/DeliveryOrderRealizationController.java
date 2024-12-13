package com.siriuserp.sales.controller;

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

import com.siriuserp.sales.criteria.DeliveryOrderRealizationFilterCriteria;
import com.siriuserp.sales.dm.DeliveryOrder;
import com.siriuserp.sales.dm.DeliveryOrderItem;
import com.siriuserp.sales.dm.DeliveryOrderRealization;
import com.siriuserp.sales.dm.DeliveryOrderRealizationItem;
import com.siriuserp.sales.form.DeliveryOrderForm;
import com.siriuserp.sales.query.DeliveryOrderRealizationGridViewQuery;
import com.siriuserp.sales.query.DeliveryOrderRealizationPreaddViewQuery;
import com.siriuserp.sales.service.DeliveryOrderRealizationService;
import com.siriuserp.sales.service.DeliveryOrderService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PostalAddress;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.utility.FormHelper;

@Controller
@SessionAttributes(value = { "dor_form" }, types = DeliveryOrderForm.class)
@DefaultRedirect(url = "deliveryorderrealizationview.htm")
public class DeliveryOrderRealizationController extends ControllerBase {

    @Autowired
    private DeliveryOrderRealizationService dorService;

    @Autowired
    private  DeliveryOrderService deliveryOrderService;

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request)
    {
        binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
        binder.registerCustomEditor(Facility.class, modelEditor.forClass(Facility.class));
        binder.registerCustomEditor(PostalAddress.class, modelEditor.forClass(PostalAddress.class));
		binder.registerCustomEditor(DeliveryOrder.class, modelEditor.forClass(DeliveryOrder.class));
		binder.registerCustomEditor(DeliveryOrderItem.class, modelEditor.forClass(DeliveryOrderItem.class));
		binder.registerCustomEditor(DeliveryOrderRealization.class, modelEditor.forClass(DeliveryOrderRealization.class));
		binder.registerCustomEditor(DeliveryOrderRealizationItem.class, modelEditor.forClass(DeliveryOrderRealizationItem.class));
    }

    @RequestMapping("/deliveryorderrealizationview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/sales/deliveryOrderRealizationList",
				dorService.view(criteriaFactory.create(request, DeliveryOrderRealizationFilterCriteria.class), DeliveryOrderRealizationGridViewQuery.class));
	}

    @RequestMapping("/deliveryorderrealizationpreadd1.htm")
	public ModelAndView preadd1(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/sales/deliveryOrderRealizationAdd1",
				deliveryOrderService.view(criteriaFactory.create(request, DeliveryOrderRealizationFilterCriteria.class), DeliveryOrderRealizationPreaddViewQuery.class));
	}

    @RequestMapping("/deliveryorderrealizationpreadd2.htm")
	public ModelAndView preadd2(@RequestParam("DO") Long id) throws Exception
	{
		return new ModelAndView("/sales/deliveryOrderRealizationAdd2", dorService.preadd2(id));
	}

    @RequestMapping("/deliveryorderrealizationadd.htm")
	public ModelAndView add(@ModelAttribute("dor_form") DeliveryOrderForm deliveryOrderForm, BindingResult result, SessionStatus status) throws Exception
	{
        JSONResponse response = new JSONResponse();

		try {
			dorService.add(FormHelper.create(DeliveryOrderRealization.class, deliveryOrderForm));
			status.setComplete();

			response.store("id", deliveryOrderForm.getDeliveryOrderRealization().getId());
		} catch (Exception e) {
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}

	@RequestMapping("/deliveryorderrealizationpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws Exception {
		return new ModelAndView("/sales/deliveryOrderRealizationUpdate", dorService.preedit(id));
	}

	@RequestMapping("/deliveryorderrealizationedit.htm")
	public ModelAndView edit(@ModelAttribute("dor_form") DeliveryOrderForm deliveryOrderForm, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try {
			dorService.edit(deliveryOrderForm);
			status.setComplete();

			response.store("id", deliveryOrderForm.getDeliveryOrderRealization().getId());
		} catch (Exception e) {
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}
}
