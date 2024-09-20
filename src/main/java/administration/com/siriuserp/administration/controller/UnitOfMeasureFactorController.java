/**
 * Feb 22, 2011 11:19:36 AM
 * com.siriuserp.administration.controller
 * UnitOfMeasureFactorController.java
 */
package com.siriuserp.administration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.administration.service.UnitOfMeasureFactorService;
import com.siriuserp.inventory.dm.UnitOfMeasure;
import com.siriuserp.inventory.dm.UnitofMeasureFactor;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.view.ViewHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia,PT
 * Sinch version 1.5
 */
@Controller
@SessionAttributes(value={"unit_of_measure_factor_add","unit_of_measure_factor_edit"},types={UnitofMeasureFactor.class})
public class UnitOfMeasureFactorController extends ControllerBase
{
    @Autowired
    private UnitOfMeasureFactorService service;
    
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) 
    {
        binder.registerCustomEditor(UnitOfMeasure.class,modelEditor.forClass(UnitOfMeasure.class));
    }
    
    @RequestMapping("/unitofmeasurefactorpreadd.htm")
    public ModelAndView preadd(@RequestParam("id")Long id)
    {
        return new ModelAndView("/inventory/item-management/unitOfMeasureFactorAdd",service.preadd(id));
    }
    
    @RequestMapping("/unitofmeasurefactoradd.htm")
    public ModelAndView add(@ModelAttribute("unit_of_measure_factor_add")UnitofMeasureFactor factor,SessionStatus status)
    {
        JSONResponse response = new JSONResponse();

        try
        {
            service.add(factor);
            status.setComplete();
        }
        catch (Exception e)
        {
            response.statusError();
            response.setMessage(e.getLocalizedMessage());
        }
        
        return response;
    }
    
    @RequestMapping("/unitofmeasurefactorpreedit.htm")
    public ModelAndView preedit(@RequestParam("id")Long id)
    {
        return new ModelAndView("/inventory/item-management/unitOfMeasureFactorUpdate",service.preedit(id));
    }
    
    @RequestMapping("/unitofmeasurefactoredit.htm")
    public ModelAndView edit(@ModelAttribute("unit_of_measure_factor_edit")UnitofMeasureFactor factor,SessionStatus status)
    {
        JSONResponse response = new JSONResponse();
        
        try
        {
            service.edit(factor);
            status.setComplete();
        }
        catch (Exception e)
        {
            response.statusError();
            response.setMessage(e.getLocalizedMessage());
        }
        
        return response;
    }
    
    @RequestMapping("/unitofmeasurefactordelete.htm")
    public ModelAndView delete(@RequestParam("id")Long id) throws ServiceException
    {
        UnitofMeasureFactor factor = service.load(id);
        
        service.delete(factor);
        return ViewHelper.redirectTo("uompreedit.htm?id="+factor.getFrom().getId());
    }
}
