/**
 * Mar 29, 2007 4:26:09 PM
 * net.konsep.sirius.presentation.administration
 * ActivityHistoryController.java
 */
package com.siriuserp.tools.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.tools.criteria.AuditTrailsFilterCriteria;
import com.siriuserp.tools.query.AuditTrailsGridViewQuery;
import com.siriuserp.tools.service.AuditTrailService;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Controller
public class AuditTrailsController extends ControllerBase
{
    @Autowired
    private AuditTrailService auditTrailService;

    @RequestMapping("/activityHistoryView.htm")
    public ModelAndView view(HttpServletRequest request) throws ServiceException
    {
        return new ModelAndView("activityHistoryList",auditTrailService.view(criteriaFactory.create(request,AuditTrailsFilterCriteria.class),AuditTrailsGridViewQuery.class));
    }
}
