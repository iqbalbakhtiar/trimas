/**
 * Jan 3, 2008 11:37:41 AM
 * com.siriuserp.dashboard.controller
 * QuickSearchController.java
 */
package com.siriuserp.dashboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.dashboard.service.QuickSearchService;
import com.siriuserp.sdk.springmvc.view.ViewHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
public class QuickSearchController
{
	@Autowired
	private QuickSearchService service;

	@RequestMapping("/goto.htm")
	public ModelAndView goTo(@RequestParam("code") String code) throws Exception
	{
		return ViewHelper.redirectTo(service.search(code));
	}
}
