/**
 * Nov 18, 2008 2:28:27 PM
 * com.siriuserp.accounting.controller
 * SchematicJournalEntryController.java
 */
package com.siriuserp.accounting.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.accounting.adapter.JournalEntryUIAdapter;
import com.siriuserp.accounting.criteria.JournalEntryFilterCriteria;
import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.accounting.dm.EntrySourceType;
import com.siriuserp.accounting.dm.GLAccount;
import com.siriuserp.accounting.dm.GLPostingType;
import com.siriuserp.accounting.dm.JournalEntry;
import com.siriuserp.accounting.dm.JournalSchema;
import com.siriuserp.accounting.query.JournalEntryComboGridViewQuery;
import com.siriuserp.accounting.service.JournalEntryService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.ExchangeType;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.editor.GenericEnumEditor;
import com.siriuserp.sdk.springmvc.editor.GenericModelEditor;
import com.siriuserp.sdk.springmvc.view.ViewHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = { "journalEntry_add", "journalEntry_edit" }, types = JournalEntry.class)
@DefaultRedirect(url = "schematicjournalentryview.htm")
public class SchematicJournalEntryController extends ControllerBase
{
	@Autowired
	private JournalEntryService journalEntryService;

	@Autowired
	private GenericModelEditor modelEditor;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(GLAccount.class, modelEditor.forClass(GLAccount.class));
		binder.registerCustomEditor(GLPostingType.class, new GenericEnumEditor(GLPostingType.class));
		binder.registerCustomEditor(ExchangeType.class, new GenericEnumEditor(ExchangeType.class));
		binder.registerCustomEditor(Currency.class, modelEditor.forClass(Currency.class));
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
		binder.registerCustomEditor(AccountingPeriod.class, modelEditor.forClass(AccountingPeriod.class));
		binder.registerCustomEditor(JournalSchema.class, modelEditor.forClass(JournalSchema.class));
	}

	@RequestMapping("/schematicjournalentryview.htm")
	public ModelAndView view(HttpServletRequest request) throws ServiceException
	{
		JournalEntryFilterCriteria criteria = (JournalEntryFilterCriteria) criteriaFactory.create(request, JournalEntryFilterCriteria.class);
		criteria.setEntrySourceType(EntrySourceType.SCHEMATIC.toString());

		return new ModelAndView("/accounting/schematicJournalEntryList", journalEntryService.view(criteria, JournalEntryComboGridViewQuery.class));
	}

	@RequestMapping("/schematicjournalentrypreadd.htm")
	public ModelAndView preadd() throws ServiceException
	{
		return new ModelAndView("/accounting/schematicJournalEntryAdd", journalEntryService.preadd());
	}

	@RequestMapping("/schematicjournalentryadd.htm")
	public ModelAndView add(@ModelAttribute("adapter") JournalEntryUIAdapter adapter, SessionStatus status, @RequestParam("accounts") String[] accounts, @RequestParam("amounts") String[] amounts, @RequestParam("postingTypes") String[] postingTypes,
			@RequestParam("notes") String[] notes, @RequestParam(value = "journalSchema", required = false) JournalSchema journalSchema) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			journalEntryService.add(journalEntryService.schematic(adapter, accounts, amounts, postingTypes, notes, journalSchema));
			status.setComplete();
			response.store("id", adapter.getJournalEntry().getId());
		} catch (Exception e)
		{
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getMessage());
		}

		return response;
	}

	@RequestMapping("/schematicjournalentrypreedit.htm")
	public ModelAndView preedit(@RequestParam("id") String id)
	{
		return new ModelAndView("/accounting/schematicJournalEntryUpdate", journalEntryService.preedit(id));
	}

	@RequestMapping("/schematicjournalentrypreview.htm")
	public ModelAndView preview(@RequestParam("id") String id)
	{
		return new ModelAndView("/accounting/schematicJournalEntryView", journalEntryService.preedit(id));
	}

	@RequestMapping("/schematicjournalentryedit.htm")
	public ModelAndView edit(@ModelAttribute("journalEntry_edit") JournalEntry journalEntry, BindingResult result, SessionStatus status, @RequestParam("accounts") GLAccount[] accounts, @RequestParam("amounts") BigDecimal[] amounts,
			@RequestParam("types") GLPostingType[] types, @RequestParam("notes") String[] notes) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			journalEntryService.edit(journalEntry, accounts, amounts, types, notes);
			status.setComplete();
		} catch (Exception e)
		{
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getMessage());
		}

		return response;
	}

	@RequestMapping("/schematicjournalentrydelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws ServiceException
	{
		journalEntryService.delete(journalEntryService.load(id));

		return ViewHelper.redirectTo("schematicjournalentryview.htm");
	}

	@RequestMapping("/schematicjournalentryunpost.htm")
	public ModelAndView unpost(@RequestParam("id") Long id) throws ServiceException
	{
		journalEntryService.unpost(id);
		return ViewHelper.redirectTo("schematicjournalentryview.htm");
	}
}
