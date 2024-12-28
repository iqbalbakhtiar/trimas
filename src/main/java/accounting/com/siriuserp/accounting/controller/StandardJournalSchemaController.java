/**
 * Nov 12, 2008 2:44:24 PM
 * com.siriuserp.accounting.controller
 * JournalSchemaController.java
 */
package com.siriuserp.accounting.controller;

import java.util.HashSet;
import java.util.Set;

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

import com.siriuserp.accounting.criteria.StandardJournalSchemaFilterCriteria;
import com.siriuserp.accounting.dm.ChartOfAccount;
import com.siriuserp.accounting.dm.GLAccount;
import com.siriuserp.accounting.dm.GLPostingType;
import com.siriuserp.accounting.dm.JournalSchemaAccount;
import com.siriuserp.accounting.dm.JournalSchemaType;
import com.siriuserp.accounting.dm.StandardJournalSchema;
import com.siriuserp.accounting.service.StandardJournalSchemaService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.editor.GenericEnumEditor;
import com.siriuserp.sdk.springmvc.view.ViewHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = { "journalSchema_add", "journalSchema_edit" }, types = StandardJournalSchema.class)
@DefaultRedirect(url = "standardjournalschemaview.htm")
public class StandardJournalSchemaController extends ControllerBase
{
	@Autowired
	private StandardJournalSchemaService standardJournalSchemaService;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
		binder.registerCustomEditor(ChartOfAccount.class, modelEditor.forClass(ChartOfAccount.class));
		binder.registerCustomEditor(GLAccount.class, modelEditor.forClass(GLAccount.class));
		binder.registerCustomEditor(JournalSchemaType.class, new GenericEnumEditor(JournalSchemaType.class));
	}

	@RequestMapping("/standardjournalschemaview.htm")
	public ModelAndView view(HttpServletRequest request) throws ServiceException
	{
		return new ModelAndView("/accounting/journalSchemaList", standardJournalSchemaService.view(criteriaFactory.create(request, StandardJournalSchemaFilterCriteria.class)));
	}

	@RequestMapping("/standardjournalschemapreadd.htm")
	public ModelAndView preadd()
	{
		return new ModelAndView("/accounting/journalSchemaAdd", standardJournalSchemaService.preadd());
	}

	@RequestMapping("/standardjournalschemaadd.htm")
	public ModelAndView add(@ModelAttribute("journalSchema_add") StandardJournalSchema journalSchema, BindingResult result, SessionStatus status, @RequestParam("accounts-debet") String[] debets, @RequestParam("accounts-credit") String[] credits,
			@RequestParam("mandatorys-debet") String[] dmandatorys, @RequestParam("mandatorys-credit") String[] cmandatorys) throws ServiceException
	{
		journalSchema.getAccounts().clear();

		for (int idx = 0; idx < debets.length; idx++)
		{
			JournalSchemaAccount account = new JournalSchemaAccount();
			account.setAccount(GLAccount.newInstance(debets[idx]));
			account.setMandatory(Boolean.valueOf(dmandatorys[idx]));
			account.setPostingType(GLPostingType.DEBET);

			journalSchema.getAccounts().add(account);
		}

		for (int idx = 0; idx < credits.length; idx++)
		{
			JournalSchemaAccount account = new JournalSchemaAccount();
			account.setAccount(GLAccount.newInstance(credits[idx]));
			account.setMandatory(Boolean.valueOf(cmandatorys[idx]));
			account.setPostingType(GLPostingType.CREDIT);

			journalSchema.getAccounts().add(account);
		}

		standardJournalSchemaService.add(journalSchema);
		status.setComplete();

		return ViewHelper.redirectTo("standardjournalschemaview.htm");
	}

	@RequestMapping("/standardjournalschemaaddnew.htm")
	public ModelAndView addnew(@ModelAttribute("journalSchema_add") StandardJournalSchema journalSchema, BindingResult result, SessionStatus status, @RequestParam("accounts-debet") String[] debets, @RequestParam("accounts-credit") String[] credits,
			@RequestParam("mandatorys-debet") String[] dmandatorys, @RequestParam("mandatorys-credit") String[] cmandatorys) throws ServiceException
	{
		journalSchema.getAccounts().clear();

		for (int idx = 0; idx < debets.length; idx++)
		{
			JournalSchemaAccount account = new JournalSchemaAccount();
			account.setAccount(GLAccount.newInstance(debets[idx]));
			account.setMandatory(Boolean.valueOf(dmandatorys[idx]));
			account.setPostingType(GLPostingType.DEBET);

			journalSchema.getAccounts().add(account);
		}

		for (int idx = 0; idx < credits.length; idx++)
		{
			JournalSchemaAccount account = new JournalSchemaAccount();
			account.setAccount(GLAccount.newInstance(credits[idx]));
			account.setMandatory(Boolean.valueOf(cmandatorys[idx]));
			account.setPostingType(GLPostingType.CREDIT);

			journalSchema.getAccounts().add(account);
		}

		standardJournalSchemaService.add(journalSchema);
		status.setComplete();

		return ViewHelper.redirectTo("standardjournalschemapreadd.htm");
	}

	@RequestMapping("/standardjournalschemapreedit.htm")
	public ModelAndView preedit(@RequestParam("id") String id)
	{
		return new ModelAndView("/accounting/journalSchemaUpdate", standardJournalSchemaService.preedit(id));
	}

	@RequestMapping("/standardjournalschemaedit.htm")
	public ModelAndView edit(@ModelAttribute("journalSchema_edit") StandardJournalSchema journalSchema, BindingResult result, SessionStatus status, @RequestParam(value = "accounts-debet", required = false) String[] debets,
			@RequestParam(value = "accounts-credit", required = false) String[] credits, @RequestParam(value = "mandatorys-debet", required = false) String[] dmandatorys,
			@RequestParam(value = "mandatorys-credit", required = false) String[] cmandatorys) throws ServiceException
	{
		Set<JournalSchemaAccount> accounts = new HashSet<JournalSchemaAccount>();

		if (debets != null && dmandatorys != null)
		{
			for (int idx = 0; idx < debets.length; idx++)
			{
				JournalSchemaAccount account = new JournalSchemaAccount();
				account.setAccount(GLAccount.newInstance(debets[idx]));
				account.setMandatory(Boolean.valueOf(dmandatorys[idx]));
				account.setPostingType(GLPostingType.DEBET);

				accounts.add(account);
			}
		}

		if (credits != null && cmandatorys != null)
		{
			for (int idx = 0; idx < credits.length; idx++)
			{
				JournalSchemaAccount account = new JournalSchemaAccount();
				account.setAccount(GLAccount.newInstance(credits[idx]));
				account.setMandatory(Boolean.valueOf(cmandatorys[idx]));
				account.setPostingType(GLPostingType.CREDIT);

				accounts.add(account);
			}
		}

		standardJournalSchemaService.edit(journalSchema, accounts);
		status.setComplete();

		return ViewHelper.redirectTo("standardjournalschemaview.htm");
	}

	@RequestMapping("/standardjournalschemadelete.htm")
	public ModelAndView delete(@RequestParam("id") String id) throws ServiceException
	{
		standardJournalSchemaService.delete(standardJournalSchemaService.load(id));
		return ViewHelper.redirectTo("standardjournalschemaview.htm");
	}
}
