/**
 * Mar 24, 2010 9:23:01 AM
 * com.siriuserp.administration.controller
 * PartyPopupController.java
 */
package com.siriuserp.administration.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.administration.criteria.PartyFilterCriteria;
import com.siriuserp.administration.query.PartyPopupGridViewQuery;
import com.siriuserp.administration.query.PartyRelationPopupGridViewQuery;
import com.siriuserp.administration.service.PartyService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 * Version 1.5
 */

@Controller
public class PartyPopupController extends ControllerBase
{
	@Autowired
	private PartyService service;

	@RequestMapping("/popuppartyview.htm")
	public ModelAndView show(HttpServletRequest request, @RequestParam("target") String target) throws ServiceException
	{
		FastMap<String, Object> map = service.view(criteriaFactory.createPopup(request, PartyFilterCriteria.class), PartyPopupGridViewQuery.class);
		map.put("target", target);

		return new ModelAndView("/party/partyPopup", map);
	}

	@RequestMapping("/popuppartyrelationview.htm")
	public ModelAndView showrelation(HttpServletRequest request, @RequestParam("target") String target) throws ServiceException
	{
		FastMap<String, Object> map = service.view(criteriaFactory.createPopup(request, PartyFilterCriteria.class), PartyRelationPopupGridViewQuery.class);
		map.put("target", target);

		return new ModelAndView("/party/partyRelationPopup", map);
	}
}
