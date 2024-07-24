/**
 * May 4, 2009 5:20:04 PM
 * com.siriuserp.popup.controller
 * GridPopupController.java
 */
package com.siriuserp.administration.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.administration.criteria.GridFilterCriteria;
import com.siriuserp.administration.query.GridPopupGridViewQuery;
import com.siriuserp.administration.service.GridService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
public class GridPopupController extends ControllerBase
{
	@Autowired
	private GridService service;

	@RequestMapping("/popupgridview.htm")
	public ModelAndView view(HttpServletRequest request, @RequestParam("target") String target) throws ServiceException
	{
		ModelAndView view = new ModelAndView("gridPopup");
		view.addAllObjects(service.view(criteriaFactory.createPopup(request, GridFilterCriteria.class), GridPopupGridViewQuery.class));
		view.addObject("target", target);

		return view;
	}
}
