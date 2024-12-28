/**
 * Nov 20, 2008 4:29:54 PM
 * com.siriuserp.accounting.controller
 * JournalEntryController.java
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

import com.siriuserp.accounting.criteria.JournalEntryFilterCriteria;
import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.accounting.dm.EntrySourceType;
import com.siriuserp.accounting.dm.GLAccount;
import com.siriuserp.accounting.dm.GLPostingType;
import com.siriuserp.accounting.dm.IndexType;
import com.siriuserp.accounting.dm.JournalEntry;
import com.siriuserp.accounting.dm.JournalEntryDetail;
import com.siriuserp.accounting.dm.JournalEntryIndex;
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
import com.siriuserp.sdk.springmvc.view.ViewHelper;
import com.siriuserp.sdk.utility.DecimalHelper;
import com.siriuserp.sdk.utility.SiriusValidator;
import com.siriuserp.sdk.utility.StringHelper;

import javolution.util.FastMap;
import javolution.util.FastSet;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = { "journalEntry_add", "journalEntry_edit" }, types = JournalEntry.class)
@DefaultRedirect(url = "journalentryview.htm")
public class JournalEntryController extends ControllerBase
{
	@Autowired
	private JournalEntryService journalEntryService;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(GLAccount.class, modelEditor.forClass(GLAccount.class));
		binder.registerCustomEditor(GLPostingType.class, new GenericEnumEditor(GLPostingType.class));
		binder.registerCustomEditor(ExchangeType.class, new GenericEnumEditor(ExchangeType.class));
		binder.registerCustomEditor(Currency.class, modelEditor.forClass(Currency.class));
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
		binder.registerCustomEditor(AccountingPeriod.class, modelEditor.forClass(AccountingPeriod.class));
	}

	@RequestMapping("/journalentryview.htm")
	public ModelAndView view(HttpServletRequest request) throws ServiceException
	{
		return new ModelAndView("/accounting/journalEntryList", journalEntryService.view(criteriaFactory.create(request, JournalEntryFilterCriteria.class), JournalEntryComboGridViewQuery.class));
	}

	@RequestMapping("/journalentryviewall.htm")
	public ModelAndView view2(HttpServletRequest request) throws ServiceException
	{
		JournalEntryFilterCriteria criteria = (JournalEntryFilterCriteria) criteriaFactory.create(request, JournalEntryFilterCriteria.class);
		criteria.setEntrySourceType(EntrySourceType.STANDARD.toString());

		return new ModelAndView("/accounting/journalEntryList2", journalEntryService.viewAll(criteria, JournalEntryComboGridViewQuery.class));
	}

	@RequestMapping("/journalentrypreadd.htm")
	public ModelAndView preadd() throws ServiceException
	{
		return new ModelAndView("/accounting/journalEntryAdd", journalEntryService.preadd());
	}

	@RequestMapping("/journalentryadminpreadd.htm")
	public ModelAndView preadminadd() throws ServiceException
	{
		FastMap<String, Object> map = journalEntryService.preadd();
		map.put("flag", true);

		return new ModelAndView("/accounting/journalEntryAdd", map);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("/journalentryadd.htm")
	public ModelAndView add(@ModelAttribute("journalEntry_add") JournalEntry journalEntry, SessionStatus status, @RequestParam(value = "flag", required = false) String flag, @RequestParam(value = "indexs", required = false) String[] indexes,
			@RequestParam(value = "text", required = false) String[] texts, @RequestParam("accounts") String[] accounts, @RequestParam("amounts") String[] amounts, @RequestParam("postingTypes") String[] postingTypes,
			@RequestParam("notes") String[] notes) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			FastSet.recycle((FastSet) journalEntry.getDetails());

			if (flag != null && Boolean.valueOf(flag))
				journalEntry.setAccountingPeriod(journalEntryService.loadIgnorePeriode(journalEntry.getOrganization().getId(), journalEntry.getEntryDate()));

			if (indexes != null && texts != null && indexes.length == texts.length)
			{
				for (int idx = 0; idx < indexes.length; idx++)
				{
					if (SiriusValidator.validateParam(texts[idx]))
					{
						JournalEntryIndex journalEntryIndex = new JournalEntryIndex();
						journalEntryIndex.setContent(texts[idx]);
						journalEntryIndex.setIndexType(IndexType.newInstance(indexes[idx]));
						journalEntryIndex.setJournalEntry(journalEntry);

						journalEntry.getIndexes().add(journalEntryIndex);
					}
				}
			}

			if (accounts.length == amounts.length && accounts.length == postingTypes.length)
			{
				for (int idx = 0; idx < accounts.length; idx++)
				{
					JournalEntryDetail detail = new JournalEntryDetail();
					detail.setAccount(GLAccount.newInstance(accounts[idx]));
					detail.setJournalEntry(journalEntry);
					detail.setPostingType(GLPostingType.valueOf(postingTypes[idx]));
					detail.setNote(StringHelper.get(notes, idx));
					detail.setTransactionDate(journalEntry.getEntryDate());
					detail.setAmount(DecimalHelper.toSaveDecimal(amounts[idx]));

					journalEntry.getDetails().add(detail);
				}
			}

			journalEntry.setEntrySourceType(EntrySourceType.STANDARD);

			journalEntryService.add(journalEntry);

			status.setComplete();
		} catch (Exception e)
		{
			e.printStackTrace();

			response.statusError();
			response.setMessage(e.getMessage());
		}

		return response;
	}

	@RequestMapping("/journalentrypreedit.htm")
	public ModelAndView preedit(@RequestParam("id") String id)
	{
		return new ModelAndView("/accounting/journalEntryUpdate", journalEntryService.preedit(id));
	}

	@RequestMapping("/journalentrypreview.htm")
	public ModelAndView preview(@RequestParam("id") String id)
	{
		return new ModelAndView("/accounting/journalEntryView", journalEntryService.preedit(id));
	}

	@RequestMapping("/journalentryedit.htm")
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

	@RequestMapping("/journalentryprecopy.htm")
	public ModelAndView precopy(@RequestParam(value = "id", required = false) String id) throws ServiceException
	{
		if (!SiriusValidator.validateParamWithZeroPosibility(id))
			return ViewHelper.redirectTo("journalentrypreadd.htm");

		return new ModelAndView("/accounting/journalEntryCopy", journalEntryService.precopy(id));
	}

	@RequestMapping("/journalentrycopy.htm")
	public ModelAndView copy(@ModelAttribute("journalEntry_add") JournalEntry journalEntry, SessionStatus status, @RequestParam(value = "indexs", required = false) String[] indexes, @RequestParam(value = "text", required = false) String[] texts,
			@RequestParam("accounts") String[] accounts, @RequestParam("amounts") String[] amounts, @RequestParam("postingTypes") String[] postingTypes, @RequestParam("notes") String[] notes) throws ServiceException
	{
		journalEntry.setEntrySourceType(EntrySourceType.STANDARD);
		journalEntryService.add(journalEntry);

		status.setComplete();

		return ViewHelper.redirectTo("journalentryview.htm");
	}

	@RequestMapping("/journalentryprereverse.htm")
	public ModelAndView prereverse(@RequestParam(value = "id", required = false) String id) throws ServiceException
	{
		if (!SiriusValidator.validateParamWithZeroPosibility(id))
			return ViewHelper.redirectTo("journalentrypreadd.htm");

		return new ModelAndView("/accounting/journalEntryReverse", journalEntryService.prereverse(id));
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("/journalentryreverse.htm")
	public ModelAndView reverse(@ModelAttribute("journalEntry_add") JournalEntry journalEntry, SessionStatus status, @RequestParam(value = "flag", required = false) String flag, @RequestParam(value = "indexs", required = false) String[] indexes,
			@RequestParam(value = "text", required = false) String[] texts, @RequestParam("accounts") String[] accounts, @RequestParam("amounts") String[] amounts, @RequestParam("postingTypes") String[] postingTypes,
			@RequestParam("notes") String[] notes) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			FastSet.recycle((FastSet) journalEntry.getDetails());

			if (flag != null && Boolean.valueOf(flag))
				journalEntry.setAccountingPeriod(journalEntryService.loadIgnorePeriode(journalEntry.getOrganization().getId(), journalEntry.getEntryDate()));

			if (indexes != null && texts != null && indexes.length == texts.length)
			{
				for (int idx = 0; idx < indexes.length; idx++)
				{
					if (SiriusValidator.validateParam(texts[idx]))
					{
						JournalEntryIndex journalEntryIndex = new JournalEntryIndex();
						journalEntryIndex.setContent(texts[idx]);
						journalEntryIndex.setIndexType(IndexType.newInstance(indexes[idx]));
						journalEntryIndex.setJournalEntry(journalEntry);

						journalEntry.getIndexes().add(journalEntryIndex);
					}
				}
			}

			if (accounts.length == amounts.length && accounts.length == postingTypes.length)
			{
				for (int idx = 0; idx < accounts.length; idx++)
				{
					JournalEntryDetail detail = new JournalEntryDetail();
					detail.setAccount(GLAccount.newInstance(accounts[idx]));
					detail.setJournalEntry(journalEntry);
					detail.setPostingType(GLPostingType.valueOf(postingTypes[idx]));
					detail.setNote(StringHelper.get(notes, idx));
					detail.setTransactionDate(journalEntry.getEntryDate());
					detail.setAmount(DecimalHelper.toSaveDecimal(amounts[idx]));

					journalEntry.getDetails().add(detail);
				}
			}

			journalEntryService.add(journalEntry);

			status.setComplete();
		} catch (Exception e)
		{
			e.printStackTrace();

			response.statusError();
			response.setMessage(e.getMessage());
		}

		return response;
	}

	@RequestMapping("/journalentrydelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws ServiceException
	{
		if (SiriusValidator.validateParamWithZeroPosibility(id))
			journalEntryService.delete(journalEntryService.load(id));

		return ViewHelper.redirectTo("journalentryview.htm");
	}

	@RequestMapping("/journalentryunpost.htm")
	public ModelAndView unpost(@RequestParam("id") Long id) throws ServiceException
	{
		journalEntryService.unpost(id);
		return ViewHelper.redirectTo("journalentryview.htm");
	}

	@RequestMapping("/journalentryrecode.htm")
	public ModelAndView recode(HttpServletRequest request) throws Exception
	{
		journalEntryService.recode(criteriaFactory.create(request, JournalEntryFilterCriteria.class));

		return ViewHelper.redirectTo("/page/journalentryview.htm");
	}
}
