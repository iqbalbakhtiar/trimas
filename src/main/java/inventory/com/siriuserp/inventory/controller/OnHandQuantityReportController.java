package com.siriuserp.inventory.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.inventory.criteria.InventoryLedgerFilterCriteria;
import com.siriuserp.inventory.dm.Product;
import com.siriuserp.inventory.dm.ProductCategory;
import com.siriuserp.inventory.service.OnHandQuantityReportService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.exceptions.ServiceException;

/**
 * @author ferdinand
 */

@Controller
@SessionAttributes(value = "criteria", types = InventoryLedgerFilterCriteria.class)
public class OnHandQuantityReportController extends ControllerBase
{
	@Autowired
	private OnHandQuantityReportService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		initBinderFactory.initBinder(binder, Product.class, Facility.class, ProductCategory.class);
	}

	@RequestMapping("/onhandquantitybydatereportpre.htm")
	public ModelAndView pre() throws ServiceException
	{
		return new ModelAndView("inventory-report/onHandQuantityByDateReportAdd", service.pre());
	}

	@RequestMapping("/onhandquantitybydatereportview.htm")
	public ModelAndView view(HttpServletRequest request) throws ServiceException
	{
		return new ModelAndView("inventory-report/onHandQuantityByDateReportList", service.view((InventoryLedgerFilterCriteria) criteriaFactory.createReport(request, InventoryLedgerFilterCriteria.class)));
	}
}
