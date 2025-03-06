/**
 * File Name  : PurchaseRequisitionPopupController.java
 * Created On : Feb 23, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.procurement.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.procurement.criteria.PurchaseRequisitionFilterCriteria;
import com.siriuserp.procurement.query.PurchaseRequisitionItemViewQuery;
import com.siriuserp.procurement.service.PurchaseRequisitionService;
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
public class PurchaseRequisitionPopupController extends ControllerBase
{
	@Autowired
	private PurchaseRequisitionService service;

	@RequestMapping("/popuppurchaserequisitionitemview.htm")
	public ModelAndView viewItemPopup(HttpServletRequest request) throws Exception
	{
		return new ModelReferenceView("/procurement-popup/purchaseRequisitionItemPopup", request.getParameter("ref"),
				service.view(criteriaFactory.createPopup(request, PurchaseRequisitionFilterCriteria.class), PurchaseRequisitionItemViewQuery.class));
	}

	@RequestMapping("/popuppurchaserequisitionitemjson.htm")
	public ModelAndView view(@RequestParam("id") Long id) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			response.store("requisitionItem", service.loadItem(id));
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}
}
