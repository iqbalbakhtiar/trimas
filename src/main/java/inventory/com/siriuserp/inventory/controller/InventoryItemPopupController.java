/**
 * File Name  : InventoryItemPopupController.java
 * Created On : Mar 24, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.inventory.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.inventory.criteria.ProductFilterCriteria;
import com.siriuserp.inventory.query.InventoryItemPopupViewQuery;
import com.siriuserp.inventory.service.OnHandQuantityService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.ModelReferenceView;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Controller
public class InventoryItemPopupController extends ControllerBase
{
	@Autowired
	private OnHandQuantityService service;

	@RequestMapping("/popupinventoryitemview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelReferenceView("/inventory-popup/inventoryItemPopup", request.getParameter("ref"), service.view(criteriaFactory.createPopup(request, ProductFilterCriteria.class), InventoryItemPopupViewQuery.class));
	}

	@RequestMapping("/popupinventoryitemjson.htm")
	public ModelAndView view(@RequestParam("id") Long id) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			response.store("inventory", service.load(id));
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}
	
	@RequestMapping("/popupinventoryitemlistjson.htm")
	public ModelAndView viewList(@RequestParam("productId") Long productId, @RequestParam("containerId") Long containerId) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			response.store("barcodes", service.loadList(productId, containerId));
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}
}
