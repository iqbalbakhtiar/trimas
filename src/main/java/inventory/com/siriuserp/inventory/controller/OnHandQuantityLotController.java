/**
 * File Name  : OnHandQuantityLotController.java
 * Created On : May 17, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.inventory.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.inventory.criteria.OnHandQuantityFilterCriteria;
import com.siriuserp.inventory.query.OnHandQuantityLotViewQuery;
import com.siriuserp.inventory.service.OnHandQuantityService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Controller
@DefaultRedirect(url = "onhandquantitylotview.htm")
public class OnHandQuantityLotController extends ControllerBase
{
	@Autowired
	private OnHandQuantityService service;

	@RequestMapping("/onhandquantitylotview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		OnHandQuantityFilterCriteria criteria = (OnHandQuantityFilterCriteria) criteriaFactory.create(request, OnHandQuantityFilterCriteria.class);
		criteria.setViewByLot(true);

		return new ModelAndView("/inventory/warehouse-management/onHandQuantityLotList", service.view(criteria, OnHandQuantityLotViewQuery.class));
	}

	@RequestMapping("/onhandquantityeditlotview.htm")
	public ModelAndView preedit(HttpServletRequest request) throws ServiceException
	{
		OnHandQuantityFilterCriteria criteria = (OnHandQuantityFilterCriteria) criteriaFactory.create(request, OnHandQuantityFilterCriteria.class);
		criteria.setViewByLot(true);

		return new ModelAndView("/inventory/warehouse-management/onHandQuantityLotUpdate", service.preedit(criteria));
	}
}
