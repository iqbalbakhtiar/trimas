package com.siriuserp.accounting.controller;

import com.siriuserp.accounting.criteria.LedgerSummaryReportFilterCriteria;
import com.siriuserp.accounting.service.LedgerDetailReportService;
import com.siriuserp.sdk.base.ControllerBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Rama Almer Felix
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@SessionAttributes(value = "ledgerCriteria", types = LedgerSummaryReportFilterCriteria.class)
@Controller
public class LedgerDetailReportController extends ControllerBase {
    @Autowired
    private LedgerDetailReportService service;

    @RequestMapping("/ledgerdetailreportprepare.htm")
    public ModelAndView prepare()
    {
        return new ModelAndView("/accounting/report/arLedgerDetailReportAdd", service.pre());
    }

    @RequestMapping("/ledgerdetailreportview.htm")
    public ModelAndView view(@ModelAttribute("ledgerCriteria") LedgerSummaryReportFilterCriteria criteria) throws Exception {
        return new ModelAndView("/accounting/report/arLedgerDetailReportList", service.view(service.createMonth(criteria)));
    }
}
