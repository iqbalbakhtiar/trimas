/**
 * File Name  : SalesReportController.java
 * Created On : May 10, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.sales.criteria.SalesReportFilterCriteria;
import com.siriuserp.sales.service.SalesReportService;
import com.siriuserp.sdk.base.ControllerBase;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Controller
public class SalesReportController extends ControllerBase
{
	@Autowired
	private SalesReportService service;

	@RequestMapping("/salesonprogressreportpre.htm")
	public ModelAndView pre()
	{
		return new ModelAndView("/report/sales/salesOnProgressReportAdd", service.pre());
	}

	@RequestMapping("/salesonprogressreportview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/report/sales/salesOnProgressReportList", service.view(criteriaFactory.createReport(request, SalesReportFilterCriteria.class)));
	}

	@RequestMapping("/salessummaryreportpre.htm")
	public ModelAndView preSummary()
	{
		return new ModelAndView("/report/sales/salesSummaryReportAdd", service.pre());
	}

	@RequestMapping("/salessummaryreportview.htm")
	public ModelAndView viewSummary(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/report/sales/salesSummaryReportList", service.viewSummary(criteriaFactory.createReport(request, SalesReportFilterCriteria.class)));
	}

	@RequestMapping("/salesdetailreportpre.htm")
	public ModelAndView preDetail()
	{
		return new ModelAndView("/report/sales/salesDetailReportAdd", service.pre());
	}

	@RequestMapping("/salesdetailreportview.htm")
	public ModelAndView viewDetail(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/report/sales/salesDetailReportList", service.viewDetail(criteriaFactory.createReport(request, SalesReportFilterCriteria.class)));
	}
}
