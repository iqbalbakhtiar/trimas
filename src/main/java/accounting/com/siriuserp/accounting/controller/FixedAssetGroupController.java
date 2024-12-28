/**
 * Dec 19, 2007 11:56:07 AM
 * com.siriuserp.accounting.controller
 * FixedAssetGroupController.java
 */
package com.siriuserp.accounting.controller;

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
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.accounting.criteria.FixedAssetGroupFilterCriteria;
import com.siriuserp.accounting.dm.AccountingSchema;
import com.siriuserp.accounting.dm.FixedAssetGroup;
import com.siriuserp.accounting.dm.GLAccount;
import com.siriuserp.accounting.query.FixedAssetGroupGridViewQuery;
import com.siriuserp.accounting.service.FixedAssetGroupService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.view.ViewHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = "fixedAssetGroup", types = FixedAssetGroup.class)
@DefaultRedirect(url = "fixedassetgroupview.htm")
public class FixedAssetGroupController extends ControllerBase
{
	@Autowired
	private FixedAssetGroupService fixedAssetGroupService;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(AccountingSchema.class, modelEditor.forClass(AccountingSchema.class));
		binder.registerCustomEditor(GLAccount.class, modelEditor.forClass(GLAccount.class));
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
		binder.registerCustomEditor(FixedAssetGroup.class, modelEditor.forClass(FixedAssetGroup.class));
	}

	@RequestMapping("/fixedassetgroupview.htm")
	public ModelAndView view(HttpServletRequest request) throws ServiceException
	{
		return new ModelAndView("/accounting/assets/fixedAssetGroupList", fixedAssetGroupService.view(criteriaFactory.create(request, FixedAssetGroupFilterCriteria.class), FixedAssetGroupGridViewQuery.class));
	}

	@RequestMapping("/fixedassetgrouppreadd.htm")
	public ModelAndView preadd(HttpServletRequest request)
	{
		return new ModelAndView("/accounting/assets/fixedAssetGroupAdd", fixedAssetGroupService.preadd());
	}

	@RequestMapping("/fixedassetgroupadd.htm")
	public ModelAndView add(@ModelAttribute("fixedAssetGroup") FixedAssetGroup fixedAssetGroup, BindingResult result, SessionStatus status) throws ServiceException
	{
		fixedAssetGroupService.add(fixedAssetGroup);
		status.setComplete();

		return ViewHelper.redirectTo("fixedassetgroupview.htm");
	}

	@RequestMapping("/fixedassetgrouppreedit.htm")
	public ModelAndView preedit(@RequestParam("id") String id)
	{
		return new ModelAndView("/accounting/assets/fixedAssetGroupUpdate", fixedAssetGroupService.preedit(id));
	}

	@RequestMapping("/fixedassetgroupedit.htm")
	public ModelAndView edit(@ModelAttribute("fixedAssetGroup") FixedAssetGroup fixedAssetGroup, BindingResult result, SessionStatus status) throws ServiceException
	{
		fixedAssetGroupService.edit(fixedAssetGroup);
		status.setComplete();

		return ViewHelper.redirectTo("fixedassetgroupview.htm");
	}

	@RequestMapping("/fixedassetgroupdelete.htm")
	public ModelAndView delete(@RequestParam("id") String id) throws ServiceException
	{
		fixedAssetGroupService.delete(fixedAssetGroupService.load(Long.valueOf(id)));
		return ViewHelper.redirectTo("fixedassetgroupview.htm");
	}
}
