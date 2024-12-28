/**
 * Nov 18, 2008 5:09:28 PM
 * com.siriuserp.popup
 * AccountingPeriodController.java
 */
package com.siriuserp.accounting.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.accounting.criteria.AccountingPeriodFilterCriteria;
import com.siriuserp.accounting.service.AccountingPeriodService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;

import javolution.util.FastMap;

/**
 * @author Muhammad Rizal
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
public class AccountingPeriodPopupController extends ControllerBase
{
	@Autowired
	private AccountingPeriodService service;

	@RequestMapping("/popupaccountingperiodview.htm")
	public ModelAndView view(HttpServletRequest request, @RequestParam("target") String target) throws ServiceException
	{
		FastMap<String, Object> map = service.view(criteriaFactory.createPopup(request, AccountingPeriodFilterCriteria.class));
		map.put("target", target);

		return new ModelAndView("/accounting/accountingPeriodPopup", map);
	}

	@RequestMapping("/popupaccountingperiodreport.htm")
	public ModelAndView report(HttpServletRequest request, @RequestParam("target") String target) throws ServiceException
	{
		FastMap<String, Object> map = service.report(criteriaFactory.createPopup(request, AccountingPeriodFilterCriteria.class));
		map.put("target", target);
	
		return new ModelAndView("/report/accounting/accountingPeriodReportPopup", map);
	}

	@RequestMapping("/popupaccountingperiodcheckcloseability.htm")
	public ModelAndView closeable(@RequestParam("id") Long id)
	{
		JSONResponse response = new JSONResponse();

		try
		{
			response.store(service.closeable(id));
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getMessage());
		}

		return response;
	}
}
