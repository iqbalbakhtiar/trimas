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

import com.siriuserp.administration.service.PlafonService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.PartyRelationship;
import com.siriuserp.sdk.dm.Plafon;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.view.ViewHelper;

@Controller
@SessionAttributes(value = { "plafon_add", "plafon_edit" }, types = Plafon.class)
@DefaultRedirect(url = "plafonview.htm")
public class PlafonController extends ControllerBase {
	
	@Autowired
	private PlafonService service;
	
	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(PartyRelationship.class, modelEditor.forClass(PartyRelationship.class));
	}
	
	@RequestMapping("/plafonpreadd.htm") // Long Party maybe not used
	public ModelAndView preadd(@RequestParam("party") Long party, @RequestParam(value = "relationshipId", required = false) Long relationshipId, @RequestParam(value = "uri", required = false) String uri) {
		ModelAndView view = new ModelAndView("/administration/plafonAdd", service.preadd(relationshipId));
		view.addObject("redirectURL", uri);
		view.addObject("relationshipId", relationshipId);

		return view;
	}
	
	@RequestMapping("/plafonadd.htm")
	public ModelAndView add(@ModelAttribute("plafon_add") Plafon plafon, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try {
			service.add(plafon);
			status.setComplete();
		} catch (Exception e) {
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}
		
		return response;
	}
	
	@RequestMapping("/plafonpreedit.htm") // Relationship ID maybe not used
	public ModelAndView preedit(@RequestParam("id") Long id, @RequestParam(value = "relationshipId", required = false) Long relationshipId, @RequestParam(value = "uri", required = false) String uri)
	{
		ModelAndView view = new ModelAndView("/administration/plafonUpdate", service.preedit(id));
		view.addObject("redirectURL", uri);
		view.addObject("relationshipId", relationshipId);

		return view;
	}
	
	@RequestMapping("/plafontedit.htm")
	public ModelAndView edit(@ModelAttribute("plafon_edit") Plafon plafon, SessionStatus status)
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(plafon);
			status.setComplete();
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}
	
	@RequestMapping("/plafondelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id, @RequestParam("party") Long party, @RequestParam(value = "relationshipId", required = false) Long relationshipId, @RequestParam(value = "uri", required = false) String uri) throws Exception
	{
		service.delete(id);

		if (StringUtils.isNotEmpty(uri))
			return ViewHelper.redirectTo(uri + "?id=" + relationshipId);

		return ViewHelper.redirectTo("partypreedit.htm?id=" + party);
	}
}
