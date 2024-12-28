/**
 * Nov 12, 2008 10:21:43 AM
 * com.siriuserp.accounting.controller
 * JournalEntryIndexController.java
 */
package com.siriuserp.accounting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.accounting.dm.IndexType;
import com.siriuserp.accounting.service.JournalEntryIndexService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.view.ViewHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = { "journalEntryIndex_add", "journalEntryIndex_edit" }, types = IndexType.class)
@DefaultRedirect(url = "journalentryindexview.htm")
public class JournalEntryIndexController
{
	@Autowired
	private JournalEntryIndexService journalEntryIndexService;

	@RequestMapping("/journalentryindexview.htm")
	public ModelAndView view()
	{
		return new ModelAndView("/accounting/journalEntryIndexList", journalEntryIndexService.view());
	}

	@RequestMapping("/journalentryindexpreadd.htm")
	public ModelAndView preadd()
	{
		return new ModelAndView("/accounting/journalEntryIndexAdd", journalEntryIndexService.preadd());
	}

	@RequestMapping("/journalentryindexadd.htm")
	public ModelAndView add(@ModelAttribute("journalEntryIndex_add") IndexType journalEntryIndex, BindingResult result, SessionStatus status) throws ServiceException
	{
		journalEntryIndexService.add(journalEntryIndex);
		status.setComplete();

		return ViewHelper.redirectTo("journalentryindexview.htm");
	}

	@RequestMapping("/journalentryindexpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") String id)
	{
		return new ModelAndView("/accounting/journalEntryIndexUpdate", journalEntryIndexService.preedit(id));
	}

	@RequestMapping("/journalentryindexedit.htm")
	public ModelAndView edit(@ModelAttribute("journalEntryIndex_edit") IndexType journalEntryIndex, BindingResult result, SessionStatus status) throws ServiceException
	{
		journalEntryIndexService.edit(journalEntryIndex);
		status.setComplete();

		return ViewHelper.redirectTo("journalentryindexview.htm");
	}

	@RequestMapping("/journalentryindexdelete.htm")
	public ModelAndView delete(@RequestParam("id") String id) throws ServiceException
	{
		journalEntryIndexService.delete(journalEntryIndexService.load(id));
		return ViewHelper.redirectTo("journalentryindexview.htm");
	}
}
