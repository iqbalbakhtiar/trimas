/**
 * Feb 18, 2009 2:20:34 PM
 * com.siriuserp.popup
 * FixedAssetGroupPopupController.java
 */
package com.siriuserp.accounting.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.accounting.criteria.FixedAssetGroupFilterCriteria;
import com.siriuserp.accounting.query.FixedAssetGroupPopupGridViewQuery;
import com.siriuserp.accounting.service.FixedAssetGroupService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Controller
public class FixedAssetGroupPopupController extends ControllerBase
{
    @Autowired
    private FixedAssetGroupService service;
    
    @RequestMapping("/popupfixedassetgroupview.htm")
    public ModelAndView view(HttpServletRequest request,@RequestParam(value="target",required=false)String target) throws ServiceException
    {
        FastMap<String,Object> map = service.view(criteriaFactory.createPopup(request,FixedAssetGroupFilterCriteria.class),FixedAssetGroupPopupGridViewQuery.class);
        
        if(SiriusValidator.validateParam(target))
            map.put("target",target);
        else
            map.put("target","group");
        
        return new ModelAndView("fixedAssetGroupPopup",map);
    }
}
