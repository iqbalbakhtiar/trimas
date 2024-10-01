package com.siriuserp.administration.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.accounting.dm.BankAccount;
import com.siriuserp.administration.service.PartyBankAccountService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PartyBankAccount;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.view.ViewHelper;

@Controller
@SessionAttributes(value = { "partyBankAccount_add", "partyBankAccount_edit" }, types = PartyBankAccount.class)
public class PartyBankAccountController extends ControllerBase {

	@Autowired
	private PartyBankAccountService service;
	
	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
		binder.registerCustomEditor(BankAccount.class, modelEditor.forClass(BankAccount.class));
	}
	
	@RequestMapping("/partybankaccountpreadd.htm")
	public ModelAndView preadd(@RequestParam("party") Long party, @RequestParam(value = "relationshipId", required = false) Long relationshipId, @RequestParam(value = "uri", required = false) String uri) {
		ModelAndView view = new ModelAndView("/administration/partyBankAccountAdd", service.preadd(party));
		view.addObject("redirectURL", uri);
		view.addObject("relationshipId", relationshipId);

		return view;
	}
	
	@RequestMapping("/partybankaccountadd.htm")
	public ModelAndView add(@ModelAttribute("partyBankAccount_add") PartyBankAccount partyBankAccount, SessionStatus status)
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(partyBankAccount);
			status.setComplete();

			response.store("id", partyBankAccount.getParty().getId());
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}
	
	@RequestMapping("/partybankaccountpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id, @RequestParam(value = "relationshipId", required = false) Long relationshipId, @RequestParam(value = "uri", required = false) String uri)
	{
		ModelAndView view = new ModelAndView("/administration/partyBankAccountUpdate", service.preedit(id));
		view.addObject("redirectURL", uri);
		view.addObject("relationshipId", relationshipId);

		return view;
	}
	
	@RequestMapping("/partybankaccountedit.htm")
	public ModelAndView edit(@ModelAttribute("partyBankAccount_edit") PartyBankAccount partyBankAccount, SessionStatus status)
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(partyBankAccount);
			status.setComplete();
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}
	
	@RequestMapping("/partybankaccountdelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id, @RequestParam("party") Long party, @RequestParam(value = "relationshipId", required = false) Long relationshipId, @RequestParam(value = "uri", required = false) String uri) throws Exception
	{
		service.delete(id);

		if (StringUtils.isNotEmpty(uri))
			return ViewHelper.redirectTo(uri + "?id=" + relationshipId);

		return ViewHelper.redirectTo("partypreedit.htm?id=" + party);
	}
}
