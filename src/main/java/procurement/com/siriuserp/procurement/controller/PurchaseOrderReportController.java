/**
 * Jun 30, 2011
 * PurchaseOrderReportController.java
 */
package com.siriuserp.procurement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.procurement.criteria.PurchaseOrderReportFilterCriteria;
import com.siriuserp.procurement.service.PurchaseOrderReportService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.exceptions.ServiceException;

/**
 * @author Iqbal
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Controller
public class PurchaseOrderReportController extends ControllerBase
{
	@Autowired
	private PurchaseOrderReportService service;
    
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) 
    {
    	initBinderFactory.initBinder(binder, Party.class);
    }
    
    @RequestMapping("/purchaseorderreportpre.htm")
    public ModelAndView pre() throws ServiceException
    {
    	return new ModelAndView("procurement-report/purchaseOrderReportAdd", service.preadd());
    }
    
    @RequestMapping("/purchaseorderreportview.htm")
    public ModelAndView view(@ModelAttribute("criteria") PurchaseOrderReportFilterCriteria filterCriteria)
    {
    	return new ModelAndView("procurement-report/purchaseOrderReportList",service.genReport(filterCriteria));
    }
}
