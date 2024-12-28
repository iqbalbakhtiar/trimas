/**
 * Mar 20, 2009 3:38:24 PM
 * com.siriuserp.popup
 * FixedAssetPopupController.java
 */
package com.siriuserp.accounting.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.accounting.criteria.FixedAssetFilterCriteria;
import com.siriuserp.accounting.query.FixedAssetPopupGridViewQuery;
import com.siriuserp.accounting.service.FixedAssetService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Deprecated
@Controller
public class FixedAssetPopupController extends ControllerBase
{
    @Autowired
    private FixedAssetService fixedAssetService;
   
    @RequestMapping("/popupfixedassetview.htm")
    public ModelAndView view(HttpServletRequest request,@RequestParam("target")String target,@RequestParam("index")String index) throws ServiceException
    {
        FastMap<String,Object> map = fixedAssetService.view(criteriaFactory.createPopup(request,FixedAssetFilterCriteria.class),FixedAssetPopupGridViewQuery.class);
        map.put("target",target);
        map.put("index",index);
        
        return new ModelAndView("fixedAssetPopup",map);
    }
    
    @RequestMapping("/popupfixedassetreportview.htm")
    public ModelAndView open(HttpServletRequest request,@RequestParam("target")String target) throws ServiceException
    {
        FastMap<String,Object> map = fixedAssetService.view(criteriaFactory.createPopup(request,FixedAssetFilterCriteria.class),FixedAssetPopupGridViewQuery.class);
        map.put("target",target);
        
        return new ModelAndView("fixedAssetReportPopup",map);
    }
}
