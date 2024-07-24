package com.siriuserp.administration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.administration.service.GeographicsRemoteService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.springmvc.JSONResponse;

/**
 * @author Muhammad Khairullah
 * Sirius Indonesia,PT
 * www.siriuserp.com
 */

@Controller
public class GeographicsRemoteController extends ControllerBase
{
	@Autowired
	private GeographicsRemoteService service;

	@RequestMapping("geographicsremote.getcountry.json")
	public ModelAndView getCountry() throws Exception
	{
		return new JSONResponse(service.getCountry());
	}

	@RequestMapping("geographicsremote.getprovince.json")
	public ModelAndView getProvince(@RequestParam("id") Long country) throws Exception
	{
		return new JSONResponse(service.getProvince(country));
	}

	@RequestMapping("geographicsremote.getcity.json")
	public ModelAndView getCity(@RequestParam("id") Long province) throws Exception
	{
		return new JSONResponse(service.getCity(province));
	}

	@RequestMapping("geographicsremote.checkavailableregion.json")
	public ModelAndView checkAvailableRegion(@RequestParam("id") Long city) throws Exception
	{
		return new JSONResponse(service.checkAvailableRegion(city));
	}

	@RequestMapping("geographicsremote.getregion.json")
	public ModelAndView getRegion(@RequestParam("id") Long city) throws Exception
	{
		return new JSONResponse(service.getRegion(city));
	}

	@RequestMapping("geographicsremote.getsubdistrict.json")
	public ModelAndView getSubdisctrict(@RequestParam("id") Long region) throws Exception
	{
		return new JSONResponse(service.getSubdistrict(region));
	}
}
