/**
 * File Name  : ProductInOutTransactionController.java
 * Created On : Oct 2, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.inventory.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.inventory.criteria.OnHandQuantityFilterCriteria;
import com.siriuserp.inventory.query.ProductInOutTransactionVIewQuery;
import com.siriuserp.inventory.service.OnHandQuantityService;
import com.siriuserp.sdk.base.ControllerBase;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Controller
public class ProductInOutTransactionController extends ControllerBase
{
	@Autowired
	private OnHandQuantityService service;

	@RequestMapping("/popupproductinouttransactionview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("inventory-popup/productInOutTransactionPopup", service.view(criteriaFactory.create(request, OnHandQuantityFilterCriteria.class), ProductInOutTransactionVIewQuery.class));
	}
}
