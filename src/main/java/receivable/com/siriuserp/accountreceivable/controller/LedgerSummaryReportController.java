package com.siriuserp.accountreceivable.controller;

import com.siriuserp.accountreceivable.criteria.LedgerSummaryReportFilterCriteria;
import com.siriuserp.accountreceivable.service.LedgerSummaryReportService;
import com.siriuserp.sdk.base.ControllerBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@SessionAttributes(value="ledgerCriteria",types= LedgerSummaryReportFilterCriteria.class)
@Controller
public class LedgerSummaryReportController extends ControllerBase {
    @Autowired
    private LedgerSummaryReportService service;

    @RequestMapping("/ledgersummaryreportprepare.htm")
    public ModelAndView prepare()
    {
        return new ModelAndView("/accounting/report/arLedgerSummaryReportAdd",service.pre());
    }

    @RequestMapping("/ledgersummaryreportview.htm")
    public ModelAndView view2(@ModelAttribute("ledgerCriteria")LedgerSummaryReportFilterCriteria criteria) throws Exception {
        return new ModelAndView("/accounting/report/arLedgerSummaryReportList",service.view(service.createMonth(criteria)));
    }
}
