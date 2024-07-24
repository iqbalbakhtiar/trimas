/**
 * Mar 17, 2009 10:05:08 AM
 * com.siriuserp.popup
 * GeographicPopupController.java
 */
package com.siriuserp.administration.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.administration.criteria.GeographicFilterCriteria;
import com.siriuserp.administration.query.GeographicGridViewQuery;
import com.siriuserp.administration.service.GeographicService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Controller
public class GeographicPopupController extends ControllerBase
{
    @Autowired
    private GeographicService geographicService;
    
    @RequestMapping("popupgeographicview.htm")
    public ModelAndView view(HttpServletRequest request,@RequestParam("target")String target) throws ServiceException
    {
        FastMap<String,Object> map = geographicService.view(criteriaFactory.createPopup(request,GeographicFilterCriteria.class),GeographicGridViewQuery.class);
        map.put("target",target);
        
        return new ModelAndView("geographicPopup",map);
    }
}
