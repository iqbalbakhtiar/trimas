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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.inventory.criteria.BarcodeGroupFilterCriteria;
import com.siriuserp.inventory.criteria.StockAdjustmentFilterCriteria;
import com.siriuserp.inventory.dm.Product;
import com.siriuserp.inventory.dm.StockAdjustment;
import com.siriuserp.inventory.form.InventoryForm;
import com.siriuserp.inventory.query.BarcodeGroupGridViewQuery;
import com.siriuserp.inventory.query.StockAdjustmentGridViewQuery;
import com.siriuserp.inventory.service.BarcodeGroupService;
import com.siriuserp.inventory.service.StockAdjustmentService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.BarcodeStatus;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.ExchangeType;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Grid;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.ResponseStatus;
import com.siriuserp.sdk.utility.FormHelper;

/**
 * @author ferdinand
 * @author Rama Almer Felix
 */

@Controller
@SessionAttributes(value = { "adjustment_add", "adjustment_edit" }, types = { StockAdjustment.class, InventoryForm.class })
@DefaultRedirect(url = "stockadjustmentview.htm")
public class StockAdjustmentController extends ControllerBase 
{
	@Autowired
	private StockAdjustmentService service;

	@Autowired
	private BarcodeGroupService barcodeGroupService;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(Grid.class, modelEditor.forClass(Grid.class));
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
		binder.registerCustomEditor(Product.class, modelEditor.forClass(Product.class));
		binder.registerCustomEditor(Currency.class, modelEditor.forClass(Currency.class));
		binder.registerCustomEditor(Facility.class, modelEditor.forClass(Facility.class));
		binder.registerCustomEditor(Container.class, modelEditor.forClass(Container.class));
		binder.registerCustomEditor(ExchangeType.class, enumEditor.forClass(ExchangeType.class));
	}
	
	@RequestMapping("/stockadjustmentview.htm")
	public ModelAndView view(HttpServletRequest request) throws ServiceException
	{
		return new ModelAndView("/inventory/warehouse-management/stockAdjustmentList", service.view(criteriaFactory.create(request, StockAdjustmentFilterCriteria.class), StockAdjustmentGridViewQuery.class));
	}
	
	@RequestMapping("/stockadjustmentpreadd.htm")
	public ModelAndView preadd() throws ServiceException
	{
		return new ModelAndView("/inventory/warehouse-management/stockAdjustmentAdd", service.preadd());
	}
	
	@RequestMapping("/stockadjustmentadd.htm")
	public ModelAndView add(@ModelAttribute("adjustment_add") InventoryForm form, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(FormHelper.create(StockAdjustment.class, form));
			status.setComplete();
			
			response.store("id", form.getStockAdjustment().getId());
		} 
		catch (Exception e)
		{
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getLocalizedMessage());
			e.printStackTrace();
		}

		return response;
	}
	
	@RequestMapping("/stockadjustmentpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws ServiceException
	{
		return new ModelAndView("/inventory/warehouse-management/stockAdjustmentUpdate", service.preedit(id));
	}

	@RequestMapping("/stockadjustmentedit.htm")
	public ModelAndView edit(@ModelAttribute("adjustment_edit") StockAdjustment stockAdjustment, SessionStatus status) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(stockAdjustment);
			status.setComplete();
			
			response.store("id", stockAdjustment.getId());
		} 
		catch (Exception e)
		{
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}
	
	@RequestMapping("/stockadjustmentbyproductjson.htm")
	public ModelAndView viewJson(@RequestParam("productId") Long productId) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			response.store("product", service.loadInOut(productId));
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping("/stockadjustmentbarcodepreadd1.htm")
	public ModelAndView barcodePreAdd1(HttpServletRequest request) throws Exception
	{
		BarcodeGroupFilterCriteria criteria = (BarcodeGroupFilterCriteria) criteriaFactory.create(request, BarcodeGroupFilterCriteria.class);

		criteria.setBarcodeGroupType("STOCK_ADJUSTMENT");
		criteria.setStatus("CREATED");

		return new ModelAndView("/inventory/warehouse-management/stockAdjustmentBarcodeAdd1", barcodeGroupService.view(criteria, BarcodeGroupGridViewQuery.class));
	}

	@RequestMapping("/stockadjustmentbarcodepreadd2.htm")
	public ModelAndView barcodePreAdd2(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/inventory/warehouse-management/stockAdjustmentBarcodeAdd2", service.barcodePreadd2(id));
	}

	@RequestMapping("/stockadjustmentbarcodeadd.htm")
	public ModelAndView barcodeAdd(@RequestParam("id") Long barcodeGroupId, @ModelAttribute("adjustment_add") InventoryForm form, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(FormHelper.create(StockAdjustment.class, form));

			// Change Barcode Group Status to Available
			barcodeGroupService.updateStatus(barcodeGroupId, BarcodeStatus.AVAILABLE);

			status.setComplete();

			response.store("id", form.getStockAdjustment().getId());
		}
		catch (Exception e)
		{
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getLocalizedMessage());
			e.printStackTrace();
		}

		return response;
	}

//	@RequestMapping("/stockadjustmentdelete.htm")
//	public ModelAndView delete(@RequestParam("id") Long id) throws Exception
//	{
//		service.delete(service.load(id));
//
//		return ViewHelper.redirectTo("stockadjustmentview.htm");
//	}
}
