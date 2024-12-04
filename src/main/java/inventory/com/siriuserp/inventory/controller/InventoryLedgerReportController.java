/**
 * 
 */
package com.siriuserp.inventory.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.inventory.criteria.InventoryLedgerFilterCriteria;
import com.siriuserp.inventory.dm.Product;
import com.siriuserp.inventory.service.InventoryLedgerReportService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Month;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.XLSFile;

/**
 * @author rizal
 */

@Controller
@SessionAttributes(value = "criteria", types = InventoryLedgerFilterCriteria.class)
public class InventoryLedgerReportController extends ControllerBase 
{
	@Autowired
	private InventoryLedgerReportService service;
	
	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		initBinderFactory.initBinder(binder, Product.class, Month.class);
	}

	@RequestMapping("/inventoryledgersummarypre.htm")
	public ModelAndView pre() throws ServiceException
	{
		return new ModelAndView("inventory-report/inventoryLedgerSummaryAdd", service.pre());
	}
	
	@RequestMapping("/inventoryledgersummaryview.htm")
	public ModelAndView view(HttpServletRequest request) throws ServiceException
	{
		return new ModelAndView("inventory-report/inventoryLedgerSummaryList", service.view(service.createMonth((InventoryLedgerFilterCriteria) criteriaFactory.createReport(request, InventoryLedgerFilterCriteria.class))));
	}
	
	@RequestMapping("/inventoryledgersummaryexcell.xls")
	public ModelAndView excell(@ModelAttribute("criteria") InventoryLedgerFilterCriteria criteria)
	{
		return new XLSFile("inventory-report/inventoryLedgerSummaryPrint", service.view(service.createMonth(criteria)));
	}
	
	//Inventory Ledger Detail
	@RequestMapping("/inventoryledgerdetailpre.htm")
	public ModelAndView predetail() throws ServiceException
	{
		return new ModelAndView("inventory-report/inventoryLedgerDetailAdd", service.pre());
	}

	@RequestMapping("/inventoryledgerdetailview.htm")
	public ModelAndView viewdetail(HttpServletRequest request) throws ServiceException
	{
		return new ModelAndView("inventory-report/inventoryLedgerDetailList", service.detailview(service.createMonth((InventoryLedgerFilterCriteria) criteriaFactory.createReport(request, InventoryLedgerFilterCriteria.class))));
	}

	@RequestMapping("/inventoryledgerdetailexcell.xls")
	public ModelAndView excelldetail(@ModelAttribute("criteria") InventoryLedgerFilterCriteria criteria)
	{
		return new XLSFile("inventory-report/inventoryLedgerDetailPrint", service.detailview(criteria));
	}


}
