/**
 * Apr 27, 2009 10:36:33 AM
 * com.siriuserp.administration.controller
 * GridController.java
 */
package com.siriuserp.administration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.administration.service.GridService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.dm.Grid;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.view.ViewHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = "grid", types = Grid.class)
@DefaultRedirect(url = "facilityview.htm")
public class GridController
{
	@Autowired
	private GridService service;

	@RequestMapping("/gridpreadd.htm")
	public ModelAndView preadd(@RequestParam("id") String id) throws ServiceException
	{
		return new ModelAndView("/administration/gridAdd", service.preadd(id));
	}

	@RequestMapping("/gridadd.htm")
	public ModelAndView add(@ModelAttribute("grid") Grid grid, SessionStatus status) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.add(grid);
			status.setComplete();

			response.store("id", grid.getFacility().getId());
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getMessage());
		}

		return response;
	}

	@RequestMapping("/gridpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") String id) throws ServiceException
	{
		return new ModelAndView("/administration/gridUpdate", service.preedit(id));
	}

	@RequestMapping("/gridedit.htm")
	public ModelAndView edit(@ModelAttribute("grid") Grid grid, SessionStatus status) throws ServiceException
	{
		JSONResponse response = new JSONResponse();

		try
		{
			service.edit(grid);
			status.setComplete();

			response.store("id", grid.getFacility().getId());
		} catch (Exception e)
		{
			response.statusError();
			response.setMessage(e.getMessage());
		}

		return response;
	}

	@RequestMapping("/griddelete.htm")
	public ModelAndView delete(@RequestParam("id") String id) throws ServiceException
	{
		Grid grid = service.load(id);
		service.delete(grid);

		return ViewHelper.redirectTo("facilitypreedit.htm?id=" + grid.getFacility().getId());
	}
}
