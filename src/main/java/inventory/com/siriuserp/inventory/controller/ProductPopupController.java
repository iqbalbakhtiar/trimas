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
import com.siriuserp.inventory.service.ProductService;
import com.siriuserp.sdk.base.ControllerBase;

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
	public ModelAndView popup(HttpServletRequest request, @RequestParam(value = "target", required = false) String target) throws Exception
	{
		ModelAndView view = new ModelAndView("/inventory-popup/productPopup");
		view.addAllObjects(service.view(criteriaFactory.createPopup(request, ProductFilterCriteria.class), ProductGridViewQuery.class));
		view.addObject("target", target);

		return view;
	}

	@RequestMapping("/popupproductforadjustmentview.htm")
	public ModelAndView foradjustment(HttpServletRequest request, @RequestParam("target") String target, @RequestParam("index") String index) throws Exception
	{
		FastMap<String, Object> map = service.view(criteriaFactory.createPopup(request, ProductFilterCriteria.class), ProductGridViewQuery.class);
		map.put("target", target);
		map.put("index", index);

		return new ModelAndView("/inventory-popup/product4AdjustmentPopup", map);
	}
}
