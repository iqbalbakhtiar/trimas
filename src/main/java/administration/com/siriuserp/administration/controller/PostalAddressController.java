/**
 * Oct 31, 2008 11:04:17 AM
 * com.siriuserp.administration.controller
 * PostalAddressController.java
 */
package com.siriuserp.administration.controller;

import org.apache.commons.lang.StringUtils;
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

import com.siriuserp.administration.dm.Geographic;
import com.siriuserp.administration.form.PartyForm;
import com.siriuserp.administration.service.PostalAddressService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.AddressType;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.PostalAddress;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.ResponseStatus;
import com.siriuserp.sdk.springmvc.view.ViewHelper;
import com.siriuserp.sdk.utility.FormHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = { "postalAddress_add", "postalAddress_edit" }, types = { PostalAddress.class, PartyForm.class })
public class PostalAddressController extends ControllerBase
{
	@Autowired
	private PostalAddressService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(Geographic.class, modelEditor.forClass(Geographic.class));
		binder.registerCustomEditor(AddressType.class, enumEditor.forClass(AddressType.class));
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
	}

	@RequestMapping("/postaladdresspreadd.htm")
	public ModelAndView preadd(@RequestParam("party") Long party, @RequestParam(value = "relationshipId", required = false) Long relationshipId, @RequestParam(value = "uri", required = false) String uri)
	{
		ModelAndView view = new ModelAndView("/administration/postalAddressAdd", service.preadd(party));
		view.addObject("redirectURL", uri);
		view.addObject("relationshipId", relationshipId);

		return view;
	}

	@RequestMapping("/postaladdressadd.htm")
	public ModelAndView add(@ModelAttribute("postalAddress_add") PartyForm partyForm, BindingResult result, SessionStatus status) throws Exception
	{
		JSONResponse response = new JSONResponse();
		try
		{
			service.add(FormHelper.create(PostalAddress.class, partyForm));
			status.setComplete();

			response.store("id", partyForm.getParty().getId());
		} catch (Exception e)
		{
			e.printStackTrace();
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}

	@RequestMapping("/postaladdresspreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id, @RequestParam(value = "relationshipId", required = false) Long relationshipId, @RequestParam(value = "uri", required = false) String uri)
	{
		ModelAndView view = new ModelAndView("/administration/postalAddressUpdate", service.preedit(id));
		view.addObject("redirectURL", uri);
		view.addObject("relationshipId", relationshipId);

		return view;
	}

	@RequestMapping("/postaladdressedit.htm")
	public ModelAndView edit(@ModelAttribute("postalAddress_edit") PostalAddress postalAddress, SessionStatus status, @RequestParam(value = "types", required = false) AddressType[] types) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(postalAddress, types);
			status.setComplete();

			response.store("id", postalAddress.getParty().getId());
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getLocalizedMessage());
		}

		return response;
	}

	@RequestMapping("/postaladdressdelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id, @RequestParam(value = "party", required = false) Long party, @RequestParam(value = "relationshipId", required = false) Long relationshipId,
			@RequestParam(value = "uri", required = false) String uri) throws ServiceException
	{
		service.delete(service.load(id));

		if (StringUtils.isNotEmpty(uri))
			return ViewHelper.redirectTo(uri + "?id=" + relationshipId);

		return ViewHelper.redirectTo("partypreedit.htm?id=" + party);
	}

	@RequestMapping("/postaladdresdefaultcheck.htm")
	public ModelAndView checkByDefault(@RequestParam("selected") String selected, @RequestParam("party") Long party) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			response.store("selected", service.checkByDefault(selected, party));
		} catch (Exception e)
		{
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}
	
	@RequestMapping("/popuppostaladdressjson.htm")
	public ModelAndView view(@RequestParam("id") Long id) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			response.store("postalAddress", service.load(id));
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}
}
