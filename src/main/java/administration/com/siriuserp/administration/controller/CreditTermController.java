package com.siriuserp.administration.controller;

import com.siriuserp.sdk.exceptions.ServiceException;
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

import com.siriuserp.administration.service.CreditTermService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.CreditTerm;
import com.siriuserp.sdk.dm.PartyRelationship;
import com.siriuserp.sdk.springmvc.JSONResponse;

import java.util.Date;

@Controller
@SessionAttributes(value = { "creditTerm_add", "creditTerm_edit" }, types = CreditTerm.class)
@DefaultRedirect(url = "CreditTermview.htm")
public class CreditTermController extends ControllerBase{
	
	@Autowired
	private CreditTermService service;
	
	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(PartyRelationship.class, modelEditor.forClass(PartyRelationship.class));
	}
	
	@RequestMapping("/credittermpreadd.htm") // Long Party maybe not used
	public ModelAndView preadd(@RequestParam("party") Long party, @RequestParam(value = "relationshipId", required = false) Long relationshipId, @RequestParam(value = "uri", required = false) String uri) {
		ModelAndView view = new ModelAndView("/administration/creditTermAdd", service.preadd(relationshipId));
		view.addObject("redirectURL", uri);
		view.addObject("relationshipId", relationshipId);

		return view;
	}
	
	@RequestMapping("/credittermadd.htm")
	public ModelAndView add(@ModelAttribute("creditTerm_add") CreditTerm creditTerm, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try {
			service.add(creditTerm);
			status.setComplete();
		} catch (Exception e) {
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}
		
		return response;
	}

	@RequestMapping("/popupcredittermjson.htm")
	public ModelAndView getByPartyAndDate(
						@RequestParam("id") Long partyId,
						@RequestParam("org") Long orgId,
						@RequestParam("type") Long type,
						@RequestParam("date") Date date) throws ServiceException {

		JSONResponse response = new JSONResponse();

		try {
			response.store("creditTerm", service.getByPartyOrgTypeDate(partyId, orgId, type, date));
		} catch (Exception e) {
			response.statusError();
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}
}
