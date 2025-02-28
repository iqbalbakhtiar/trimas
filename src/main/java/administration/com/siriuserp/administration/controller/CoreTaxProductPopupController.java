package com.siriuserp.administration.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.administration.criteria.CoreTaxFilterCriteria;
import com.siriuserp.administration.query.CoreTaxAdditionalInfoGridViewQuery;
import com.siriuserp.administration.service.CoreTaxProductService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;

import javolution.util.FastMap;

/**
 * @author ferdinand
 */

@Controller
public class CoreTaxProductPopupController extends ControllerBase 
{
	@Autowired
	private CoreTaxProductService service;
	
	@RequestMapping("/popupcoretaxaddinfoview.htm")
    public ModelAndView addView(HttpServletRequest request, @RequestParam("target") String target) throws ServiceException
    {
		FastMap<String,Object> map = service.view(criteriaFactory.createPopup(request, CoreTaxFilterCriteria.class), CoreTaxAdditionalInfoGridViewQuery.class);
    	map.put("target", target);
        
        return new ModelAndView("coreTaxAdditionalInfoPopup", map);
    }
}
