package com.siriuserp.tools.controller;

import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Approvable;
import com.siriuserp.sdk.dm.ApprovalDecisionStatus;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.tools.criteria.ApprovableFilterCriteria;
import com.siriuserp.tools.query.ApprovableGridViewQuery;
import com.siriuserp.tools.service.ApprovableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@DefaultRedirect(url="approvalview.htm")
@SessionAttributes(value = {"approval_edit" }, types = Approvable.class)
public class ApprovalController extends ControllerBase {

    @Autowired
    private ApprovableService service;

    @InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
		binder.registerCustomEditor(ApprovalDecisionStatus.class, enumEditor.forClass(ApprovalDecisionStatus.class));
	}

    @RequestMapping("/approvalview.htm")
    public ModelAndView view(HttpServletRequest request)throws Exception {
        return new ModelAndView("/tools/approvableList", service.view(criteriaFactory.create(request, ApprovableFilterCriteria.class), ApprovableGridViewQuery.class));
    }
}
