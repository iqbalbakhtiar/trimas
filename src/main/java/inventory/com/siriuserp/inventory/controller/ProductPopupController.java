/**
 * 
 */
package com.siriuserp.inventory.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.inventory.criteria.ProductFilterCriteria;
import com.siriuserp.inventory.query.ProductGridViewQuery;
import com.siriuserp.inventory.query.ProductPopup4TransferGridViewQuery;
import com.siriuserp.inventory.service.ProductService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;

import javolution.util.FastMap;

/**
 * @author ferdinand
 */

@Controller
public class ProductPopupController extends ControllerBase
{
	@Autowired
	private ProductService service;

	@RequestMapping("/popupproductview.htm")
	public ModelAndView popup(HttpServletRequest request, @RequestParam(value = "target", required = false) String target, @RequestParam(value = "purchaseRequestType", required = false) String purchaseRequestType) throws Exception
	{
		ModelAndView view = new ModelAndView("/inventory-popup/productPopup");
		view.addAllObjects(service.view(criteriaFactory.createPopup(request, ProductFilterCriteria.class), ProductGridViewQuery.class));
		view.addObject("target", target);

		return view;
	}

	@RequestMapping("/popupproductforadjustmentview.htm")
	public ModelAndView foradjustment(HttpServletRequest request, @RequestParam("target") String target, @RequestParam("index") String index) throws Exception
	{
		FastMap<String, Object> map = service.view(criteriaFactory.createPopup(request, ProductFilterCriteria.class), ProductPopup4TransferGridViewQuery.class);
		map.put("target", target);
		map.put("index", index);

		return new ModelAndView("/inventory-popup/product4AdjustmentPopup", map);
	}

	@RequestMapping("/popupproductfortransfer.htm")
	public ModelAndView forTransfer(HttpServletRequest request, @RequestParam("target") String target, @RequestParam(required = false, value = "index") String index) throws Exception
	{
		FastMap<String, Object> map = service.view(criteriaFactory.createPopup(request, ProductFilterCriteria.class), ProductPopup4TransferGridViewQuery.class);
		map.put("target", target);
		map.put("index", index);

		return new ModelAndView("/inventory-popup/product4TransferPopup", map);
	}

	@RequestMapping("/popupproductjson.htm")
	public ModelAndView view(@RequestParam("id") Long id) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			response.store("product", service.load(id));
		}
		catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

        return response;
	}
}
