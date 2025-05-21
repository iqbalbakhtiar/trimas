package com.siriuserp.inventory.controller;

import com.siriuserp.inventory.criteria.WasteReportFilterCriteria;
import com.siriuserp.inventory.dm.Product;
import com.siriuserp.inventory.service.WasteReportService;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Month;
import com.siriuserp.sdk.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rama Almer Felix
 */

@Controller
@SessionAttributes(value = "criteria", types = WasteReportFilterCriteria.class)
public class WasteReportController extends ControllerBase {
    @Autowired
    private WasteReportService service;

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request)
    {
        initBinderFactory.initBinder(binder, Product.class, Month.class);
    }

    @RequestMapping("/wastereportpre.htm")
    public ModelAndView pre() throws ServiceException
    {
        return new ModelAndView("inventory-report/wasteReportAdd", service.pre());
    }

    @RequestMapping("/wastereportview.htm")
    public ModelAndView view(HttpServletRequest request) throws ServiceException
    {
        return new ModelAndView("inventory-report/wasteReportList", service.view(service.createMonth((WasteReportFilterCriteria) criteriaFactory.createReport(request, WasteReportFilterCriteria.class))));
    }
}
