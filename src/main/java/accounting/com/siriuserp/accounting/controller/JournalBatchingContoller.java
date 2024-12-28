/**
 * Feb 14, 2008 10:21:04 AM
 * com.siriuserp.accounting.controller
 * JournalBatchingContoller.java
 */
package com.siriuserp.accounting.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.accounting.criteria.JournalEntryFilterCriteria;
import com.siriuserp.accounting.query.JournalEntryBatchedGridViewQuery;
import com.siriuserp.accounting.service.JournalEntryService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.view.ViewHelper;
import com.siriuserp.sdk.utility.UserHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller("journalBatchingContoller")
public class JournalBatchingContoller extends ControllerBase
{
	@Autowired
	private JournalEntryService journalEntryService;

	@RequestMapping("/journalBatchingList.htm")
	public ModelAndView list(HttpServletRequest request) throws ServiceException
	{
		JournalEntryFilterCriteria criteria = (JournalEntryFilterCriteria) criteriaFactory.create(request, JournalEntryFilterCriteria.class);

		if (criteria.getOrganization() == null)
			criteria.setOrganization(UserHelper.activeUser().getProfile().getOrganization().getId());

		return new ModelAndView("/accounting/journalBatchingList", journalEntryService.view(criteria, JournalEntryBatchedGridViewQuery.class));
	}

	@RequestMapping("/journalBatchingListAll.htm")
	public ModelAndView view(HttpServletRequest request) throws ServiceException
	{
		JournalEntryFilterCriteria criteria = (JournalEntryFilterCriteria) criteriaFactory.create(request, JournalEntryFilterCriteria.class);

		return new ModelAndView("/accounting/journalBatchingList", journalEntryService.view(criteria, JournalEntryBatchedGridViewQuery.class));
	}

	@RequestMapping("/journalBatchingUpdate.htm")
	public ModelAndView edit(@RequestParam("ids") String[] ids) throws ServiceException
	{
		journalEntryService.post(ids);
		return ViewHelper.redirectTo("journalBatchingList.htm");
	}

	@RequestMapping("/journalBatchingPrint.htm")
	public ModelAndView print(HttpServletRequest request) throws ServiceException
	{
		return new ModelAndView("/accounting/journalBatchingPrint", journalEntryService.view(criteriaFactory.create(request, JournalEntryFilterCriteria.class), JournalEntryBatchedGridViewQuery.class));
	}
}
