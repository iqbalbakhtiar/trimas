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

import com.siriuserp.inventory.adapter.WarehouseItemAdapter;
import com.siriuserp.inventory.criteria.GoodsReceiptFilterCriteria;
import com.siriuserp.inventory.dm.GoodsReceipt;
import com.siriuserp.inventory.dm.WarehouseReferenceItem;
import com.siriuserp.inventory.dm.WarehouseTransactionItem;
import com.siriuserp.inventory.form.InventoryForm;
import com.siriuserp.inventory.query.GoodsReceiptGridViewQuery;
import com.siriuserp.inventory.query.ReceiptableGridViewQuery;
import com.siriuserp.inventory.service.GoodsReceiptService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Grid;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.utility.FormHelper;

@Controller
@SessionAttributes(value = { "goodsReceipt_add", "goodsReceipt_edit", "adapter" }, types =
{ GoodsReceipt.class, WarehouseItemAdapter.class, InventoryForm.class })
@DefaultRedirect(url = "goodsreceiptview.htm")
public class GoodsReceiptController extends ControllerBase
{
	@Autowired
	GoodsReceiptService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
		binder.registerCustomEditor(Facility.class, modelEditor.forClass(Facility.class));
		binder.registerCustomEditor(Container.class, modelEditor.forClass(Container.class));
		binder.registerCustomEditor(Grid.class, modelEditor.forClass(Grid.class));
		binder.registerCustomEditor(WarehouseTransactionItem.class, modelEditor.forClass(WarehouseTransactionItem.class));
		binder.registerCustomEditor(WarehouseReferenceItem.class, modelEditor.forClass(WarehouseReferenceItem.class));
	}

	@RequestMapping("/goodsreceiptview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/inventory/goods-receipt/goodsReceiptList", service.view(criteriaFactory.create(request, GoodsReceiptFilterCriteria.class), GoodsReceiptGridViewQuery.class));
	}

	@RequestMapping("/goodsreceiptpreadd1.htm")
	public ModelAndView preadd1(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/inventory/goods-receipt/goodsReceiptAdd1", service.preadd1(criteriaFactory.create(request, GoodsReceiptFilterCriteria.class), ReceiptableGridViewQuery.class));
	}

	@RequestMapping("/goodsreceiptpreadd2.htm")
	public ModelAndView preadd2(@ModelAttribute("adapter") WarehouseItemAdapter adapter) throws Exception
	{
		return new ModelAndView("/inventory/goods-receipt/goodsReceiptAdd2", service.preadd2(adapter));
	}

	@RequestMapping("/goodsreceiptadd.htm")
	public ModelAndView add(@ModelAttribute("goodsReceipt_add") InventoryForm form, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(FormHelper.create(GoodsReceipt.class, form));
			status.setComplete();

			response.store("id", form.getGoodsReceipt().getId());
		} catch (Exception e)
		{
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getMessage());
		}

		return response;
	}

	@RequestMapping("/goodsreceiptpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/inventory/goods-receipt/goodsReceiptUpdate", service.preedit(id));
	}

	@RequestMapping("/goodsreceiptedit.htm")
	public ModelAndView edit(@ModelAttribute("adapter") InventoryForm form, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(FormHelper.update(form.getGoodsReceipt(), form));
			status.setComplete();

			response.store("id", form.getGoodsReceipt().getId());
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getMessage());
		}

		return response;
	}

	@RequestMapping("/goodsreceiptprint.htm")
	public ModelAndView print(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/inventory/goods-receipt/goodsReceiptPrint", service.preedit(id));
	}
}
