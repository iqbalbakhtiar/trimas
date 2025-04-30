/**
 * File Name  : DeliveryReportController.java
 * Created On : Apr 30, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	public ModelAndView view(@ModelAttribute("reportCriteria") SalesReportFilterCriteria criteria)
	{
		return new ModelAndView("/report/sales/deliveryReportList", service.view(criteria));
	}
}
