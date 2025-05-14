package com.siriuserp.inventory.controller;

import com.siriuserp.inventory.criteria.InventoryLedgerFilterCriteria;
import com.siriuserp.inventory.dm.Product;
import com.siriuserp.inventory.service.MutationReportService;
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
@SessionAttributes(value = "criteria", types = InventoryLedgerFilterCriteria.class)
public class MutationReportController extends ControllerBase {

    @Autowired
    private MutationReportService service;

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request)
    {
        initBinderFactory.initBinder(binder, Product.class, Month.class);
    }

    @RequestMapping("/mutationreportpre.htm")
    public ModelAndView pre() throws ServiceException
    {
        return new ModelAndView("inventory-report/mutationReportAdd", service.pre());
    }

    @RequestMapping("/mutationreportview.htm")
    public ModelAndView view(HttpServletRequest request) throws ServiceException
    {
        return new ModelAndView("inventory-report/mutationReportList", service.view(service.createMonth((InventoryLedgerFilterCriteria) criteriaFactory.createReport(request, InventoryLedgerFilterCriteria.class))));
    }
}
