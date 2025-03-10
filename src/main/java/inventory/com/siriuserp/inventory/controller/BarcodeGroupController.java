/**
 * File Name  : BarcodeGroupController.java
 * Created On : Feb 28, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.inventory.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.inventory.criteria.BarcodeGroupFilterCriteria;
import com.siriuserp.inventory.dm.Product;
import com.siriuserp.inventory.form.InventoryForm;
import com.siriuserp.inventory.query.BarcodeGroupGridViewQuery;
import com.siriuserp.inventory.service.BarcodeGroupService;
import com.siriuserp.procurement.dm.PurchaseOrder;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.BarcodeGroup;
import com.siriuserp.sdk.dm.BarcodeGroupType;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.view.ViewHelper;
import com.siriuserp.sdk.utility.FormHelper;

import java.util.Map;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = "barcode_form", types = InventoryForm.class)
@DefaultRedirect(url = "barcodegroupview.htm")
public class BarcodeGroupController extends ControllerBase
{
	@Autowired
	private BarcodeGroupService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
		binder.registerCustomEditor(Product.class, modelEditor.forClass(Product.class));
		binder.registerCustomEditor(Facility.class, modelEditor.forClass(Facility.class));
		binder.registerCustomEditor(BarcodeGroup.class, modelEditor.forClass(BarcodeGroup.class));
		binder.registerCustomEditor(PurchaseOrder.class, modelEditor.forClass(PurchaseOrder.class));
	}

	@RequestMapping("/barcodegroupview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/inventory/item-management/barcodeGroupList", service.view(criteriaFactory.create(request, BarcodeGroupFilterCriteria.class), BarcodeGroupGridViewQuery.class));
	}

	@RequestMapping("/barcodegrouppreadd1.htm")
	public ModelAndView preadd1(
			@RequestParam(value = "referenceId", required = false) Long referenceId,
			@RequestParam(value = "barcodeType", required = false) BarcodeGroupType barcodeType) throws Exception {

		Map<String, Object> model = service.preadd1(referenceId, barcodeType);

		model.put("referenceId", referenceId);
		model.put("barcodeType", barcodeType);

		return new ModelAndView("/inventory/item-management/barcodeGroupAdd1", model);
	}

	@RequestMapping("/barcodegrouppreadd2.htm")
	public ModelAndView preadd2(
			@ModelAttribute("barcode_form") InventoryForm form,
			@RequestParam(value = "referenceId", required = false) Long referenceId,
			@RequestParam(value = "barcodeType", required = false) BarcodeGroupType barcodeType) throws Exception {

		Map<String, Object> model = service.preadd2(form);

		model.put("referenceId", referenceId);
		model.put("barcodeType", barcodeType);

		return new ModelAndView("/inventory/item-management/barcodeGroupAdd2", model);
	}

	@RequestMapping("/barcodegrouppreadd3.htm")
	public ModelAndView preadd3(
			@ModelAttribute("barcode_form") InventoryForm form,
			@RequestParam(value = "referenceId", required = false) Long referenceId,
			@RequestParam(value = "barcodeType", required = false) BarcodeGroupType barcodeType) throws Exception
	{
		Map<String, Object> model = service.preadd3(form);

		model.put("referenceId", referenceId);
		model.put("barcodeType", barcodeType);

		return new ModelAndView("/inventory/item-management/barcodeGroupAdd3", model);
	}

	@RequestMapping("/barcodegroupadd.htm")
	public ModelAndView add(@ModelAttribute("barcode_form") InventoryForm form, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(FormHelper.create(BarcodeGroup.class, form));

			status.setComplete();
			response.store("id", form.getBarcodeGroup().getId());
		} catch (Exception e)
		{
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getMessage());
		}

		return response;
	}

	@RequestMapping("/barcodegrouppreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/inventory/item-management/barcodeGroupUpdate", service.preedit(id));
	}

	@RequestMapping("/barcodegroupedit.htm")
	public ModelAndView edit(@ModelAttribute("barcode_form") InventoryForm form, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.update(FormHelper.update(form.getBarcodeGroup(), form));
			status.setComplete();

			response.store("id", form.getBarcodeGroup().getId());
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getMessage());
		}

		return response;
	}

	@RequestMapping("/barcodegroupdelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws Exception
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
	public ModelAndView barcodeIdDelete(@RequestParam("id") Long id) throws Exception
	{
		Long groupId = service.loadBarcode(id).getBarcodeGroup().getId();
		service.deleteBarcode(service.loadBarcode(id));

		return ViewHelper.redirectTo("barcodegrouppreedit.htm?id=" + groupId);
	}

	@RequestMapping("/barcodegrouppackinglistprint.htm")
	public ModelAndView packingList(HttpServletRequest request)
	{
		String id = request.getParameter("id");

		return new ModelAndView("/inventory/item-management/barcodeGroupPackingListPrint", service.packingList(Long.valueOf(id)));
	}
}
