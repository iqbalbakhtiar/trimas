/**
 * Dec 21, 2007 4:56:53 PM
 * com.siriuserp.accounting.controller
 * FixedAssetController.java
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

import com.siriuserp.accounting.criteria.FixedAssetFilterCriteria;
import com.siriuserp.accounting.criteria.FixedAssetGroupFilterCriteria;
import com.siriuserp.accounting.dm.BankAccount;
import com.siriuserp.accounting.dm.DepreciationMethod;
import com.siriuserp.accounting.dm.FixedAsset;
import com.siriuserp.accounting.dm.Preasset;
import com.siriuserp.accounting.query.FixedAssetGridViewQuery;
import com.siriuserp.accounting.query.FixedAssetGroupGridViewQuery;
import com.siriuserp.accounting.service.FixedAssetGroupService;
import com.siriuserp.accounting.service.FixedAssetService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.ExchangeType;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.editor.GenericEnumEditor;
import com.siriuserp.sdk.springmvc.view.ViewHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Controller
@SessionAttributes(value = { "fixedAsset_add", "fixedAsset_edit" }, types = FixedAsset.class)
@DefaultRedirect(url = "fixedassetview.htm")
public class FixedAssetController extends ControllerBase
{
	@Autowired
	private FixedAssetService fixedAssetService;

	@Autowired
	private FixedAssetGroupService fixedAssetGroupService;

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(DepreciationMethod.class, new GenericEnumEditor(DepreciationMethod.class));
		binder.registerCustomEditor(BankAccount.class, modelEditor.forClass(BankAccount.class));
		binder.registerCustomEditor(Currency.class, modelEditor.forClass(Currency.class));
		binder.registerCustomEditor(ExchangeType.class, new GenericEnumEditor(ExchangeType.class));
		binder.registerCustomEditor(Preasset.class, modelEditor.forClass(Preasset.class));
		binder.registerCustomEditor(FixedAsset.class, modelEditor.forClass(FixedAsset.class));
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
	}

	@RequestMapping("/fixedassetview.htm")
	public ModelAndView view(HttpServletRequest request) throws ServiceException
	{
		FixedAssetFilterCriteria criteria = (FixedAssetFilterCriteria) criteriaFactory.create(request, FixedAssetFilterCriteria.class);
		criteria.setDispose(false);

		return new ModelAndView("/accounting/assets/fixedAssetList", fixedAssetService.view(criteria, FixedAssetGridViewQuery.class));

	}

	@RequestMapping("/fixedassetpreadd1.htm")
	public ModelAndView preadd(HttpServletRequest request) throws ServiceException
	{
		return new ModelAndView("/accounting/assets/fixedAssetAdd1", fixedAssetGroupService.view(criteriaFactory.create(request, FixedAssetGroupFilterCriteria.class), FixedAssetGroupGridViewQuery.class));
	}

	@RequestMapping("/fixedassetpreadd2.htm")
	public ModelAndView preadd2(@RequestParam("group") String group)
	{
		return new ModelAndView("/accounting/assets/fixedAssetAdd2", fixedAssetService.preadd(group));
	}

	@RequestMapping("/fixedassetadd.htm")
	public ModelAndView add(HttpServletRequest request, @ModelAttribute("fixedAsset_add") FixedAsset asset, BindingResult result, SessionStatus status) throws ServiceException
	{
		fixedAssetService.add(asset);
		status.setComplete();
		return ViewHelper.redirectTo("fixedassetview.htm");
	}

	@RequestMapping("/fixedassetpreedit.htm")
	public ModelAndView preedit(@RequestParam("id") Long id)
	{
		return new ModelAndView("/accounting/assets/fixedAssetUpdate", fixedAssetService.preedit(id));
	}

	@RequestMapping("/fixedassetedit.htm")
	public ModelAndView edit(@ModelAttribute("fixedAsset_edit") FixedAsset asset, BindingResult result, SessionStatus status) throws ServiceException
	{
		fixedAssetService.edit(asset);
		status.setComplete();

		if (asset.isDisposed())
			return ViewHelper.redirectTo("fixedassetdisposeview.htm");

		return ViewHelper.redirectTo("fixedassetview.htm");
	}

	@RequestMapping("/fixedassetdelete.htm")
	public ModelAndView delete(@RequestParam("id") String id) throws ServiceException
	{
		fixedAssetService.delete(fixedAssetService.load(Long.valueOf(id)));
		return ViewHelper.redirectTo("fixedassetview.htm");
	}

	@RequestMapping("/fixedassetdisposeview.htm")
	public ModelAndView viewdispose(HttpServletRequest request) throws ServiceException
	{
		FixedAssetFilterCriteria criteria = (FixedAssetFilterCriteria) criteriaFactory.create(request, FixedAssetFilterCriteria.class);
		criteria.setDispose(true);

		return new ModelAndView("/accounting/assets/fixedAssetDisposalList", fixedAssetService.view(criteria, FixedAssetGridViewQuery.class));
	}

	@RequestMapping("/fixedassetundisposeview.htm")
	public ModelAndView viewundispose(HttpServletRequest request) throws ServiceException
	{
		FixedAssetFilterCriteria criteria = (FixedAssetFilterCriteria) criteriaFactory.create(request, FixedAssetFilterCriteria.class);
		criteria.setDispose(false);

		return new ModelAndView("/accounting/assets/fixedAssetDisposalAdd1", fixedAssetService.view(criteria, FixedAssetGridViewQuery.class));
	}

	@RequestMapping("/fixedassetpredispose.htm")
	public ModelAndView preeditdispose2(@RequestParam("id") String id) throws ServiceException
	{
		return new ModelAndView("/accounting/assets/fixedAssetDisposalAdd2", fixedAssetService.predispose(id));
	}

	@RequestMapping("/fixedassetdisposepreedit.htm")
	public ModelAndView editdispose(@RequestParam("id") Long id) throws ServiceException
	{
		return new ModelAndView("/accounting/assets/fixedAssetDisposalUpdate", fixedAssetService.preedit(id));
	}
}
