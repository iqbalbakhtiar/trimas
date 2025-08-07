package com.siriuserp.administration.controller;

import com.siriuserp.administration.criteria.PartyFilterCriteria;
import com.siriuserp.administration.query.SalesPersonGridViewQuery;
import com.siriuserp.administration.service.SalesPersonService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.ResponseStatus;
import com.siriuserp.sdk.springmvc.view.ViewHelper;
import javolution.util.FastMap;
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

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = { "person_add", "person_edit" }, types = Party.class)
@DefaultRedirect(url = "salespersonview.htm")
public class SalesPersonController extends ControllerBase
{
	@Autowired
	private SalesPersonService service;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
	}

	@RequestMapping("/salespersonview.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("/administration/salesPersonList", service.view(criteriaFactory.create(request, PartyFilterCriteria.class), SalesPersonGridViewQuery.class));
	}

	@RequestMapping("/salespersonpreadd.htm")
	public ModelAndView preadd()
	{
		return new ModelAndView("/administration/salesPersonAdd", service.preadd());
	}

	@RequestMapping("/salespersonadd.htm")
	public ModelAndView add(@ModelAttribute("person_add") Party person, BindingResult result, SessionStatus status, @RequestParam(value = "personId", required = false) Long personId,
			@RequestParam(value = "file", required = false) MultipartFile file) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			FastMap<String, Object> map = service.add(person, personId, file);
			status.setComplete();

			response.store("data", map);
		} catch (Exception e)
		{
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping("/salespersonpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("/administration/salesPersonUpdate", service.preedit(id));
	}

	@RequestMapping("/salespersonedit.htm")
	public ModelAndView edit(@ModelAttribute("person_edit") Party person, BindingResult result, SessionStatus status, @RequestParam("relationshipId") Long relationshipId, @RequestParam(value = "active", required = false) Boolean active,
			@RequestParam(value = "note", required = false) String note, @RequestParam(value = "file", required = false) MultipartFile file) throws Exception
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(person, relationshipId, active, note, file);
			status.setComplete();

			response.store("id", relationshipId);
		} catch (Exception e)
		{
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping("/salespersondelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws Exception
	{
		service.delete(id);
		return ViewHelper.redirectTo("salesPersonView.htm");
	}

	@RequestMapping("/salespersonshowimage.htm")
	public ModelAndView showimage(@RequestParam("id") Long id) throws Exception
	{
		return new ModelAndView("image-shower", "model", service.load(id));
	}
}
