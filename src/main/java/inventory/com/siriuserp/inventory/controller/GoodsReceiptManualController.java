package com.siriuserp.inventory.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.inventory.criteria.GoodsReceiptFilterCriteria;
import com.siriuserp.inventory.dm.GoodsReceiptManual;
import com.siriuserp.inventory.dm.Product;
import com.siriuserp.inventory.dm.WarehouseTransactionSource;
import com.siriuserp.inventory.form.InventoryForm;
import com.siriuserp.inventory.query.GoodsReceiptManualGridViewQuery;
import com.siriuserp.inventory.service.GoodsReceiptManualService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Grid;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.view.ViewHelper;
import com.siriuserp.sdk.utility.FormHelper;

/**
 * @author Andres Nodas
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = { "receiptManual_form", "receiptManual_edit" }, types = {GoodsReceiptManual.class, InventoryForm.class})
@DefaultRedirect(url = "goodsreceiptmanualview.htm")
public class GoodsReceiptManualController extends ControllerBase {

	@Autowired
	private GoodsReceiptManualService service;
	
	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		initBinderFactory.initBinder(binder, Product.class, Facility.class, Container.class, Grid.class,Party.class, WarehouseTransactionSource.class);
	}
	
	@RequestMapping("/goodsreceiptmanualview.htm")
	public ModelAndView view(HttpServletRequest request) throws ServiceException
	{
		return new ModelAndView("/inventory/goods-receipt/goodsReceiptManualList", service.view(criteriaFactory.create(request, GoodsReceiptFilterCriteria.class), GoodsReceiptManualGridViewQuery.class));
	}

	@RequestMapping("/goodsreceiptmanualpreadd.htm")
	public ModelAndView preadd() throws ServiceException
	{
		return new ModelAndView("/inventory/goods-receipt/goodsReceiptManualAdd", service.preadd());
	}

	@RequestMapping("/goodsreceiptmanualadd.htm")
	public ModelAndView add(@ModelAttribute("receiptManual_form") InventoryForm form, SessionStatus status)
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(FormHelper.create(GoodsReceiptManual.class, form));
			status.setComplete();
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping("/goodsreceiptmanualpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws ServiceException
	{
		return new ModelAndView("/inventory/goods-receipt/goodsReceiptManualUpdate", service.preedit(id));
	}

	@RequestMapping("/goodsreceiptmanualedit.htm")
	public ModelAndView edit(@ModelAttribute("receiptManual_edit") GoodsReceiptManual goodsReceiptManual, SessionStatus status) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(goodsReceiptManual);
			status.setComplete();
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}
	
	@RequestMapping("/goodsreceiptmanualdelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws Exception
	{
		//service.delete(service.load(id));
		return ViewHelper.redirectTo("goodsreceiptmanualview.htm");
	}
	
}
