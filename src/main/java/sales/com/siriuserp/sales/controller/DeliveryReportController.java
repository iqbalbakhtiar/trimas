/**
 * File Name  : DeliveryReportController.java
 * Created On : Apr 30, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.sales.criteria.SalesReportFilterCriteria;
import com.siriuserp.sales.service.DeliveryReportService;
import com.siriuserp.sdk.base.ControllerBase;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = "reportCriteria", types = SalesReportFilterCriteria.class)
public class DeliveryReportController extends ControllerBase
{
	@Autowired
	private DeliveryReportService service;

	@RequestMapping("/deliveryreportpre.htm")
	public ModelAndView pre()
	{
		return new ModelAndView("/report/sales/deliveryReportAdd", service.pre());
	}

	@RequestMapping("/deliveryreportview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/report/sales/deliveryReportList", service.view(criteriaFactory.createReport(request, SalesReportFilterCriteria.class)));
	}

	@RequestMapping("/deliverytaxreportpre.htm")
	public ModelAndView preTax()
	{
		return new ModelAndView("/report/sales/deliveryTaxReportAdd", service.pre());
	}

	@RequestMapping("/deliverytaxreportview.htm")
	public ModelAndView viewTax(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/report/sales/deliveryTaxReportList", service.view(criteriaFactory.createReport(request, SalesReportFilterCriteria.class)));
	}
	
	@RequestMapping("/deliveryplanningreportpre.htm")
	public ModelAndView prePlanning()
	{
		return new ModelAndView("/report/sales/deliveryPlanningReportAdd", service.pre());
	}

	@RequestMapping("/deliveryplanningreportview.htm")
	public ModelAndView viewPlanning(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/report/sales/deliveryPlanningReportList", service.viewPlanning(criteriaFactory.createReport(request, SalesReportFilterCriteria.class)));
	}
}
