/**
 * Nov 11, 2008 2:21:31 PM
 * com.siriuserp.popup
 * GeneralLedgerAccountPopup.java
 */
package com.siriuserp.accounting.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.accounting.criteria.GLAccountingFilterCriteria;
import com.siriuserp.accounting.query.GLAccountPopupGridViewQuery;
import com.siriuserp.accounting.service.GeneralLedgerAccountService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
public class GeneralLedgerAccountPopupController extends ControllerBase
{
	@Autowired
	private GeneralLedgerAccountService generalLedgerAccountService;

	@RequestMapping("/popupglaccountview.htm")
	public ModelAndView show(HttpServletRequest request, @RequestParam("target") String name) throws ServiceException
	{
		FastMap<String, Object> map = generalLedgerAccountService.view(criteriaFactory.createPopup(request, GLAccountingFilterCriteria.class), GLAccountPopupGridViewQuery.class);
		map.put("target", name);
		map.put("split", request.getParameter("split"));
		map.put("index", request.getParameter("index"));

		return new ModelAndView("/accounting/glAccountPopup", map);
	}

	@RequestMapping("/popupglaccountjsonview.htm")
	public ModelAndView json(@RequestParam("code") String code) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			response.store("account", generalLedgerAccountService.load(code));
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getMessage());
		}

		return response;
	}
}
