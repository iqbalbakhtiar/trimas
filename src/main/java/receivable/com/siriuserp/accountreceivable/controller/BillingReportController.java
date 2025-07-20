/**
 * File Name  : BillingReportController.java
 * Created On : Jul 19, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountreceivable.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.accountreceivable.criteria.BillingReportFilterCriteria;
import com.siriuserp.accountreceivable.service.BillingReportService;
import com.siriuserp.sdk.base.ControllerBase;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Controller
public class BillingReportController extends ControllerBase
{
	@Autowired
	private BillingReportService service;

	@RequestMapping("/billingreportpre.htm")
	public ModelAndView pre()
	{
		return new ModelAndView("/report/receivable/billingReportAdd", service.pre());
	}

	@RequestMapping("/billingreportview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/report/receivable/billingReportList", service.view(criteriaFactory.createReport(request, BillingReportFilterCriteria.class)));
	}
}
