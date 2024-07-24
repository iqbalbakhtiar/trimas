/**
 * Nov 20, 2008 5:47:22 PM
 * com.siriuserp.tools.controller
 * ModuleDetailController.java
 */
package com.siriuserp.tools.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.sdk.dm.ModuleDetail;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.ResponseStatus;
import com.siriuserp.sdk.springmvc.view.ViewHelper;
import com.siriuserp.tools.service.ModuleDetailService;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Controller
@SessionAttributes(value="moduleDetail",types=ModuleDetail.class)
public class ModuleDetailController
{
    @Autowired
    private ModuleDetailService moduleDetailService;
    
    @RequestMapping("/moduledetailpreadd.htm")
    public ModelAndView preadd(@RequestParam("parent")String parent)
    {
        return new ModelAndView("moduleDetailAdd",moduleDetailService.preadd(parent));
    }
    
    @RequestMapping("/moduledetailadd.htm")
    public ModelAndView add(@ModelAttribute("moduleDetail")ModuleDetail moduleDetail,BindingResult result,SessionStatus status) throws ServiceException
    {
    	JSONResponse response = new JSONResponse();
    	
    	try
    	{
	        moduleDetailService.add(moduleDetail);
	        status.setComplete();
	        response.store("value", "page/modulepreedit.htm?id="+moduleDetail.getModule().getId());
		} 
		catch (Exception e)
		{
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
		
	    return response;
    }
    
    @RequestMapping("/moduledetailpreedit.htm")
    public ModelAndView preedit(@RequestParam("id")String id)
    {
        return new ModelAndView("moduleDetailUpdate",moduleDetailService.preedit(id));
    }
    
    @RequestMapping("/moduledetailedit.htm")
    public ModelAndView edit(@ModelAttribute("moduleDetail")ModuleDetail moduleDetail,BindingResult result,SessionStatus status) throws ServiceException
    {
    	JSONResponse response = new JSONResponse();
    	
    	try
    	{
	        moduleDetailService.edit(moduleDetail);
	        status.setComplete();
	        response.store("value", "page/modulepreedit.htm?id="+moduleDetail.getModule().getId());
    	} 
		catch (Exception e)
		{
			response.setStatus(ResponseStatus.ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
		
	    return response;
    }
    
    @RequestMapping("/moduledetaildelete.htm")
    public ModelAndView delete(@RequestParam("id")String id) throws ServiceException
    {
        moduleDetailService.delete(moduleDetailService.load(id));
        return ViewHelper.redirectTo("modulegroupview.htm");
    }
}
