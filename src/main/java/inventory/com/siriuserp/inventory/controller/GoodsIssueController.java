package com.siriuserp.inventory.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.inventory.criteria.GoodsIssueFilterCriteria;
import com.siriuserp.inventory.criteria.InventoryItemFilterCriteria;
import com.siriuserp.inventory.dm.GoodsIssue;
import com.siriuserp.inventory.dm.Product;
import com.siriuserp.inventory.dm.ProductCategory;
import com.siriuserp.inventory.form.InventoryForm;
import com.siriuserp.inventory.query.GoodsIssueGridViewQuery;
import com.siriuserp.inventory.service.GoodsIssueService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.ModelReferenceView;
import com.siriuserp.sdk.utility.FormHelper;

@Controller
@SessionAttributes(value = "transaction_form", types = InventoryForm.class)
@DefaultRedirect(url = "goodsissueview.htm")
public class GoodsIssueController extends ControllerBase
{

	@Autowired
	private GoodsIssueService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		initBinderFactory.initBinder(binder, Party.class, Date.class, Facility.class, Product.class, ProductCategory.class, GoodsIssue.class);
	}

	@RequestMapping("/goodsissueview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/inventory/goods-issue/goodsIssueList", service.view(criteriaFactory.create(request, GoodsIssueFilterCriteria.class), GoodsIssueGridViewQuery.class));
	}

	@RequestMapping("/goodsissuepreedit.htm")
	public ModelAndView preedit(HttpServletRequest request) throws Exception
	{
		return new ModelReferenceView("/inventory/goods-issue/goodsIssueUpdate", request.getParameter("type"), service.preedit(criteriaFactory.create(request, InventoryItemFilterCriteria.class)));
	}

	@RequestMapping("/goodsissueedit.htm")
	public ModelAndView edit(@ModelAttribute("transaction_form") InventoryForm form, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(FormHelper.update(form.getGoodsIssue(), form));
			status.setComplete();

			response.store("id", form.getGoodsIssue().getId());
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
			e.printStackTrace();
		}

		return response;
	}
}
