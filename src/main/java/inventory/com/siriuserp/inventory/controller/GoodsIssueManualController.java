package com.siriuserp.inventory.controller;

import com.siriuserp.inventory.criteria.GoodsIssueFilterCriteria;
import com.siriuserp.inventory.dm.GoodsIssue;
import com.siriuserp.inventory.dm.GoodsIssueManual;
import com.siriuserp.inventory.dm.Product;
import com.siriuserp.inventory.dm.ProductCategory;
import com.siriuserp.inventory.form.InventoryForm;
import com.siriuserp.inventory.query.GoodsIssueManualGridViewQuery;
import com.siriuserp.inventory.service.GoodsIssueManualService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.utility.FormHelper;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@SessionAttributes(value = "transaction_form", types = InventoryForm.class)
@DefaultRedirect(url = "goodsissuemanualview.htm")
public class GoodsIssueManualController extends ControllerBase
{

	@Autowired
	private GoodsIssueManualService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		initBinderFactory.initBinder(binder, Party.class, Date.class, Facility.class, Product.class, ProductCategory.class, GoodsIssue.class, Container.class);
	}

	@RequestMapping("/goodsissuemanualview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/inventory/goods-issue/goodsIssueManualList", service.view(criteriaFactory.create(request, GoodsIssueFilterCriteria.class), GoodsIssueManualGridViewQuery.class));
	}

	@RequestMapping("/goodsissuemanualpreadd.htm")
	public ModelAndView preadd(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/inventory/goods-issue/goodsIssueManualAdd", service.preadd());
	}

	@RequestMapping("/goodsissuemanualadd.htm")
	public ModelAndView add(@ModelAttribute("transaction_form") InventoryForm form, SessionStatus status) {
		JSONResponse response = new JSONResponse();

		try {
			service.add(FormHelper.create(GoodsIssueManual.class, form));
			status.setComplete();

			response.store("id", form.getGoodsIssueManual().getId());
		} catch (Exception e) {
			response.statusError();
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping("/goodsissuemanualpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/inventory/goods-issue/goodsIssueManualUpdate", service.preedit(id));
	}

	@RequestMapping("/goodsissuemanualedit.htm")
	public ModelAndView edit(@ModelAttribute("transaction_form") InventoryForm form, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(FormHelper.update(form.getGoodsIssueManual(), form));
			status.setComplete();

			response.store("id", form.getGoodsIssueManual().getId());
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
			e.printStackTrace();
		}

		return response;
	}
}
