package com.siriuserp.inventory.controller;

import com.siriuserp.inventory.criteria.BarcodeGroupFilterCriteria;
import com.siriuserp.inventory.dm.Product;
import com.siriuserp.inventory.form.TransactionForm;
import com.siriuserp.inventory.query.BarcodeGroupGridViewQuery;
import com.siriuserp.inventory.service.BarcodeGroupService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.BarcodeGroup;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.view.ViewHelper;
import com.siriuserp.sdk.utility.FormHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes(value = {"barcode_add", "barcode_edit"}, types = {TransactionForm.class, BarcodeGroup.class})
@DefaultRedirect(url = "barcodegroupview.htm")
public class BarcodeGroupController extends ControllerBase {
    @Autowired
	private BarcodeGroupService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
		binder.registerCustomEditor(Product.class, modelEditor.forClass(Product.class));
		binder.registerCustomEditor(Facility.class, modelEditor.forClass(Facility.class));
		binder.registerCustomEditor(BarcodeGroup.class, modelEditor.forClass(BarcodeGroup.class));
	}

	@RequestMapping("/barcodegroupview.htm")
	public ModelAndView view(HttpServletRequest request) throws ServiceException
	{
		return new ModelAndView("/inventory/item-management/barcodeGroupList", service.view(criteriaFactory.create(request, BarcodeGroupFilterCriteria.class), BarcodeGroupGridViewQuery.class));
	}

	@RequestMapping("/barcodegrouppreadd1.htm")
	public ModelAndView preadd1() throws ServiceException
	{
		return new ModelAndView("/inventory/item-management/barcodeGroupAdd1", service.preadd1());
	}

	@RequestMapping("/barcodegrouppreadd2.htm")
	public ModelAndView preadd2(@ModelAttribute("barcode_add") TransactionForm form) throws ServiceException
	{
		return new ModelAndView("/inventory/item-management/barcodeGroupAdd2", service.preadd2(form));
	}

	@RequestMapping("/barcodegrouppreadd3.htm")
	public ModelAndView preadd3(@ModelAttribute("barcode_add") TransactionForm form) throws ServiceException
	{
		return new ModelAndView("/inventory/item-management/barcodeGroupAdd3", service.preadd3(form));
	}

	@RequestMapping("/barcodegroupadd.htm")
	public ModelAndView add(@ModelAttribute("barcode_add") TransactionForm form, SessionStatus status) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(FormHelper.create(BarcodeGroup.class, form));

			status.setComplete();
			response.store("id", form.getBarcodeGroup().getId());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getMessage());
		}

		return response;
	}

	@RequestMapping("/barcodegrouppreedit.htm")
	public ModelAndView preedit(@RequestParam("id")Long id) throws Exception
	{
		return new ModelAndView("/inventory/item-management/barcodeGroupUpdate", service.preedit(id));
	}

	@RequestMapping("/barcodegroupedit.htm")
	public ModelAndView edit(@ModelAttribute("barcode_add") TransactionForm form, BindingResult result, SessionStatus status) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.update(FormHelper.update(form.getBarcodeGroup(), form));
			status.setComplete();

			response.store("id", form.getBarcodeGroup().getId());
		}
		catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getMessage());
		}

		return response;
	}

	@RequestMapping("/barcodegroupdelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws ServiceException
	{
		service.delete(service.load(id));
		return ViewHelper.redirectTo("barcodegroupview.htm");
	}

	@RequestMapping("/barcodegroupprint.htm")
	public ModelAndView barcodeGroupPrint(HttpServletRequest request)
	{
		String id = request.getParameter("id");

		return new ModelAndView("/inventory/item-management/barcodeGroupPrint", "data", service.barcodeGroupPrint(Long.valueOf(id)));
	}

	@RequestMapping("/barcodeprint.htm")
	public ModelAndView barcodeIdPrint(HttpServletRequest request)
	{
		String id = request.getParameter("id");

		return new ModelAndView("/inventory/item-management/barcodePrint", "data", service.barcodePrint(Long.valueOf(id)));
	}

	@RequestMapping("/barcodedelete.htm")
	public ModelAndView barcodeIdDelete(@RequestParam("id") Long id) throws ServiceException
	{
		Long groupId = service.loadBarcode(id).getBarcodeGroup().getId();
		service.deleteBarcode(service.loadBarcode(id));

		return ViewHelper.redirectTo("barcodegrouppreedit.htm?id="+groupId);
	}

	@RequestMapping("/barcodegrouppackinglistprint.htm")
	public ModelAndView packingList(HttpServletRequest request)
	{
		String id = request.getParameter("id");

		return new ModelAndView("/inventory/item-management/barcodeGroupPackingListPrint", service.packingList(Long.valueOf(id)));
	}
}
