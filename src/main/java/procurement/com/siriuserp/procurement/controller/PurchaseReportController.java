/**
 * Jun 30, 2011
 * PurchaseReportController.java
 */
package com.siriuserp.procurement.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.procurement.criteria.PurchaseReportFilterCriteria;
import com.siriuserp.procurement.service.PurchaseReportService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.exceptions.ServiceException;

/**
 * @author Iqbal Bakhtiar
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
public class PurchaseReportController extends ControllerBase
{
	@Autowired
	private PurchaseReportService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		initBinderFactory.initBinder(binder, Party.class);
	}

	@RequestMapping("/purchasereportpre.htm")
	public ModelAndView pre() throws ServiceException
	{
		return new ModelAndView("/report/purchase/purchaseReportAdd", service.pre());
	}

	@RequestMapping("/purchasereportview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/report/purchase/purchaseReportList", service.view(criteriaFactory.createReport(request, PurchaseReportFilterCriteria.class)));
	}
}
