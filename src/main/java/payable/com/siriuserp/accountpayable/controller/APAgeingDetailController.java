package com.siriuserp.accountpayable.controller;

import com.siriuserp.accountpayable.criteria.APAgeingFilterCriteria;
import com.siriuserp.accountpayable.service.APAgeingService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Party;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Agung Dodi Perdana
 * @author Rama Almer Felix
 * Sirius Indonesia, PT
 * www.siriuserp.com
 * Version 1.5
 */

@Controller
@SessionAttributes(value = "criteria", types = APAgeingFilterCriteria.class)
@DefaultRedirect(url = "apageingdetailpre.htm")
public class APAgeingDetailController extends ControllerBase {
    @Autowired
    private APAgeingService service;

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request)
    {
        initBinderFactory.initBinder(binder, Party.class);
    }

    @RequestMapping("/apageingdetailpre.htm")
    public ModelAndView pre()
    {
        return new ModelAndView("payable-report/apAgeingDetailReportAdd", service.pre());
    }

    @RequestMapping("/apageingdetailview.htm")
    public ModelAndView view(@ModelAttribute("criteria") APAgeingFilterCriteria criteria)
    {
        return new ModelAndView("payable-report/apAgeingDetailReportList", service.viewdetail(service.createMonth(criteria)));
    }
}

