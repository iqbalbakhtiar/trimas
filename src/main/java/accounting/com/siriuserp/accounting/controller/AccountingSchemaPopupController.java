/**
 * Nov 24, 2008 3:49:41 PM
 * com.siriuserp.popup
 * AccountingSchemaPopupController.java
 */
package com.siriuserp.accounting.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.accounting.criteria.AccountingSchemaFilterCriteria;
import com.siriuserp.accounting.service.AccountingSchemaService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
public class AccountingSchemaPopupController extends ControllerBase
{
	@Autowired
	private AccountingSchemaService accountingSchemaService;

	@RequestMapping("/popupaccountingschemaview.htm")
	public ModelAndView view(HttpServletRequest request) throws ServiceException
	{
		return new ModelAndView("/accounting/accountingSchemaPopup", accountingSchemaService.view(criteriaFactory.createPopup(request, AccountingSchemaFilterCriteria.class)));
	}
}
