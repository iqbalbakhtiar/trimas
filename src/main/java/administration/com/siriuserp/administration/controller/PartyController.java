/**
 * Nov 3, 2008 9:45:13 AM
 * com.siriuserp.administration.controller
 * PartyController.java
 */
package com.siriuserp.administration.controller;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.administration.criteria.PartyFilterCriteria;
import com.siriuserp.administration.query.PartyGridViewQuery;
import com.siriuserp.administration.service.PartyService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.view.ViewHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = { "party_add", "party_edit" }, types = Party.class)
@DefaultRedirect(url = "partyview.htm")
public class PartyController extends ControllerBase
{
	@Autowired
	private PartyService partyService;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
	}

	@RequestMapping("/partyview.htm")
	public ModelAndView view(HttpServletRequest request) throws ServiceException
	{
		return new ModelAndView("/party/partyList", partyService.view(criteriaFactory.create(request, PartyFilterCriteria.class), PartyGridViewQuery.class));
	}

	@RequestMapping("/partypreadd.htm")
	public ModelAndView preadd()
	{
		return new ModelAndView("/party/partyAdd", partyService.preadd());
	}

	@RequestMapping("/partyadd.htm")
	public ModelAndView add(@ModelAttribute("party_add") Party party, BindingResult result, SessionStatus status, @RequestParam(value = "type", required = false) String type, @RequestParam(value = "file", required = false) MultipartFile file)
			throws Exception
	{
		Long id = null;

		partyService.add(party, file);
		id = party.getId();

		status.setComplete();

		if (id == null)
			return ViewHelper.redirectTo("partyview.htm");

		return ViewHelper.redirectTo("partypreedit.htm?id=" + id);
	}

	@RequestMapping("/partypreedit.htm")
	public ModelAndView preedit(@RequestParam("id") String id) throws ServiceException
	{
		return new ModelAndView("/party/partyUpdate", partyService.preedit(id));
	}

	@RequestMapping("/partyedit.htm")
	public ModelAndView edit(@ModelAttribute("party_edit") Party party, BindingResult result, SessionStatus status, @RequestParam(value = "file", required = false) MultipartFile file) throws Exception
	{
		partyService.edit(party, file);
		status.setComplete();

		return ViewHelper.redirectTo("partyview.htm");
	}

	@RequestMapping("/partydelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws ServiceException
	{
		partyService.delete(partyService.load(id));
		return ViewHelper.redirectTo("partyview.htm");
	}

	@RequestMapping("/partyshowimage.htm")
	public ModelAndView showimage(@RequestParam("id") Long id) throws ServiceException
	{
		return new ModelAndView("image-shower", "model", partyService.load(id));
	}

	@RequestMapping("/partyisexistjsonview.htm")
	public ModelAndView isexist(@RequestParam("firstName") String firstName, @RequestParam(value = "middleName", required = false) String middleName, @RequestParam(value = "lastName", required = false) String lastName)
	{
		JSONResponse response = new JSONResponse();

		try
		{
			response.store("exist", partyService.exist(firstName, middleName, lastName));
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}
}
