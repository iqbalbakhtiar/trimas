/**
 * Nov 18, 2008 4:35:58 PM
 * com.siriuserp.popup
 * JournalSchemaPopupController.java
 */
package com.siriuserp.accounting.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.accounting.criteria.JournalSchemaAccountFilterCriteria;
import com.siriuserp.accounting.criteria.StandardJournalSchemaFilterCriteria;
import com.siriuserp.accounting.service.JournalSchemaAccountService;
import com.siriuserp.accounting.service.StandardJournalSchemaService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
public class JournalSchemaPopupController extends ControllerBase
{
	@Autowired
	private StandardJournalSchemaService standardJournalSchemaService;

	@Autowired
	private JournalSchemaAccountService journalSchemaAccountService;

	@RequestMapping("/popupjournalschemaview.htm")
	public ModelAndView views(HttpServletRequest request, @RequestParam("target") String target) throws ServiceException
	{
		ModelAndView view = new ModelAndView("/accounting/journalSchemaPopup");
		view.addAllObjects(standardJournalSchemaService.view(criteriaFactory.createPopup(request, StandardJournalSchemaFilterCriteria.class)));
		view.addObject("target", target);

		return view;
	}

	@RequestMapping("/popupjournalschema4cashbanktransactionview.htm")
	public ModelAndView cashbanktransaction(HttpServletRequest request, @RequestParam("target") String target) throws ServiceException
	{
		ModelAndView view = new ModelAndView("journalSchema4CashBankPopup");
		view.addAllObjects(standardJournalSchemaService.view(criteriaFactory.createPopup(request, StandardJournalSchemaFilterCriteria.class)));
		view.addObject("target", target);

		return view;
	}

	@RequestMapping("/popupjournalschemaaccountview.htm")
	public ModelAndView accounts(HttpServletRequest request) throws ServiceException
	{
		FastMap<String, Object> map = journalSchemaAccountService.view(criteriaFactory.createPopup(request, JournalSchemaAccountFilterCriteria.class));
		map.put("target", request.getParameter("target"));
		map.put("index", request.getParameter("index"));
		map.put("split", request.getParameter("split"));
		map.put("postingType", request.getParameter("postingType"));

		return new ModelAndView("/accounting/journalSchemaAccountPopup", map);
	}
}
