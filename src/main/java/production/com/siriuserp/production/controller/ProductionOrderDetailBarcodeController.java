package com.siriuserp.production.controller;

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

import com.siriuserp.inventory.criteria.ProductionOrderDetailBarcodeFilterCriteria;
import com.siriuserp.inventory.dm.Product;
import com.siriuserp.production.dm.ProductionOrderDetail;
import com.siriuserp.production.dm.ProductionOrderDetailBarcode;
import com.siriuserp.production.form.ProductionForm;
import com.siriuserp.production.query.ProductionOrderDetailBarcodeGridViewQuery;
import com.siriuserp.production.service.ProductionOrderDetailBarcodeService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.ResponseStatus;
import com.siriuserp.sdk.utility.FormHelper;

/**
 * @author ferdinand
 */

@Controller
@SessionAttributes(value = {"barcode_add", "barcode_edit"}, types = {ProductionOrderDetailBarcode.class, ProductionForm.class})
@DefaultRedirect(url = "productionorderdetailbarcodeview.htm")
public class ProductionOrderDetailBarcodeController extends ControllerBase
{
	@Autowired
	private ProductionOrderDetailBarcodeService service;
	
	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		
		binder.registerCustomEditor(Product.class, modelEditor.forClass(Product.class));
		binder.registerCustomEditor(ProductionOrderDetail.class, modelEditor.forClass(ProductionOrderDetail.class));
	}
	
	@RequestMapping("/productionorderdetailbarcodeview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/production/production-order/productionOrderDetailBarcodeList", service.view(criteriaFactory.create(request, ProductionOrderDetailBarcodeFilterCriteria.class), ProductionOrderDetailBarcodeGridViewQuery.class));
	}
	
	@RequestMapping("/productionorderdetailbarcodepreadd.htm")
	public ModelAndView preadd(@RequestParam(required = false, value = "id") Long id) throws ServiceException
	{
		return new ModelAndView("/production/production-order/productionOrderDetailBarcodeAdd", service.preadd(id));
	}
	
	@RequestMapping("/productionorderdetailbarcodepreadd2.htm")
	public ModelAndView preadd2(@ModelAttribute("barcode_add") ProductionForm form, SessionStatus status) throws ServiceException
	{
		return new ModelAndView("/production/production-order/productionOrderDetailBarcodeAdd2", service.preadd2(form));
	}
	
	@RequestMapping("/productionorderdetailbarcodeadd.htm")
	public ModelAndView add(@ModelAttribute("barcode_add") ProductionForm form, SessionStatus status) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(FormHelper.create(ProductionOrderDetailBarcode.class, form));
			status.setComplete();

			response.store("id", form.getProductionOrderDetailBarcode().getId());
		} 
		catch (Exception e)
		{
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}
	
	@RequestMapping("/productionorderdetailbarcodepreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/production/production-order/productionOrderDetailBarcodeUpdate", service.preedit(id));
	}

	@RequestMapping("/productionorderdetailbarcodeedit.htm")
	public ModelAndView edit(@ModelAttribute("barcode_edit") ProductionOrderDetailBarcode productionOrderDetailBarcode, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.update(productionOrderDetailBarcode);
			status.setComplete();

			response.store("id", productionOrderDetailBarcode.getId());
		} 
		catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getMessage());
		}

		return response;
	}
	
	@RequestMapping("/productionorderdetailbarcodeprint.htm")
	public ModelAndView print(@RequestParam("id") Long id, @RequestParam("type") String type) throws Exception
	{
		return new ModelAndView("/production/production-order/productionOrderDetailBarcodePrint", "data", service.print(id, type));
	}
}
