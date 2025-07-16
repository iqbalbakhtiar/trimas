package com.siriuserp.inventory.controller;

import com.siriuserp.inventory.criteria.InventoryLedgerFilterCriteria;
import com.siriuserp.inventory.dm.Product;
import com.siriuserp.inventory.service.GoodsReceiptReportService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.Month;

/**
 * @author Rama Almer Felix
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = "criteria", types = InventoryLedgerFilterCriteria.class)
public class GoodsReceiptReportController extends ControllerBase {
	@Autowired
	private GoodsReceiptReportService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		initBinderFactory.initBinder(binder, Product.class, Month.class);
	}

	@RequestMapping("/goodsreceiptreportpre.htm")
	public ModelAndView predetail() throws ServiceException
	{
		return new ModelAndView("inventory-report/goodsReceiptReportAdd", service.pre());
	}

	@RequestMapping("/goodsreceiptreportview.htm")
	public ModelAndView viewdetail(HttpServletRequest request) throws ServiceException
	{
		return new ModelAndView("inventory-report/goodsReceiptReportList", service.detailview(service.createMonth((InventoryLedgerFilterCriteria) criteriaFactory.createReport(request, InventoryLedgerFilterCriteria.class))));
	}
}
