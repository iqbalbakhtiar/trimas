package com.siriuserp.procurement.controller;

import com.siriuserp.inventory.dm.Product;
import com.siriuserp.procurement.criteria.PurchaseReportFilterCriteria;
import com.siriuserp.procurement.service.PurchaseMonthlyReportService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Month;
import com.siriuserp.sdk.dm.Party;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rama Almer Felix
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
public class PurchaseMonthlyReportController extends ControllerBase {
	@Autowired
	private PurchaseMonthlyReportService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		initBinderFactory.initBinder(binder, Product.class, Month.class);
	}

	@RequestMapping("/purchasereportmonthlypre.htm")
	public ModelAndView pre() throws Exception
	{
		return new ModelAndView("/report/purchase/purchaseMonthlyReportAdd", service.pre());
	}

	@RequestMapping("/purchasemonthlyreportview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/report/purchase/purchaseMonthlyReportList", service.view(service.createMonth((PurchaseReportFilterCriteria) criteriaFactory.createReport(request, PurchaseReportFilterCriteria.class))));
	}
}
