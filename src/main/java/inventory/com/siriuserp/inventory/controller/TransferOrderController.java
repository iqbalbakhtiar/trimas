package com.siriuserp.inventory.controller;

import com.siriuserp.inventory.criteria.TransferOrderFilterCriteria;
import com.siriuserp.inventory.dm.Product;
import com.siriuserp.inventory.dm.TransferOrder;
import com.siriuserp.inventory.dm.TransferType;
import com.siriuserp.inventory.form.InventoryForm;
import com.siriuserp.inventory.query.TransferOrderGridViewQuery;
import com.siriuserp.inventory.service.TransferOrderService;
import com.siriuserp.sdk.annotation.DefaultRedirect;
import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Grid;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.JSONResponse;
import com.siriuserp.sdk.springmvc.ResponseStatus;
import com.siriuserp.sdk.utility.FormHelper;
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

import javax.servlet.http.HttpServletRequest;

/**
 * @author ferdinand
 */

@Controller
@SessionAttributes(value = { "transfer_add", "transfer_edit" }, types = { TransferOrder.class, InventoryForm.class })
@DefaultRedirect(url = "transferorderview.htm")
public class TransferOrderController extends ControllerBase
{
    @Autowired
    private TransferOrderService service;

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request)
    {
        binder.registerCustomEditor(Grid.class, modelEditor.forClass(Grid.class));
        binder.registerCustomEditor(Party.class, modelEditor.forClass(Party.class));
        binder.registerCustomEditor(Product.class, modelEditor.forClass(Product.class));
        binder.registerCustomEditor(Facility.class, modelEditor.forClass(Facility.class));
        binder.registerCustomEditor(Container.class, modelEditor.forClass(Container.class));
        binder.registerCustomEditor(TransferType.class, enumEditor.forClass(TransferType.class));
    }

    @RequestMapping("/transferorderview.htm")
    public ModelAndView view(HttpServletRequest request) throws ServiceException
    {
        return new ModelAndView("/inventory/warehouse-management/transferOrderList", service.view(criteriaFactory.create(request, TransferOrderFilterCriteria.class), TransferOrderGridViewQuery.class));
    }

    @RequestMapping("/transferorderpreadd.htm")
    public ModelAndView preadd() throws ServiceException
    {
        return new ModelAndView("/inventory/warehouse-management/transferOrderAdd", service.preadd());
    }

    @RequestMapping("/transferorderadd.htm")
    public ModelAndView add(@ModelAttribute("transfer_add") InventoryForm form, SessionStatus status) throws Exception
    {
        JSONResponse response = new JSONResponse();

        try
        {
            service.add(FormHelper.create(TransferOrder.class, form));
            status.setComplete();

            response.store("id", form.getTransferOrder().getId());
        }
        catch (Exception e)
        {
            response.setStatus(ResponseStatus.ERROR);
            response.setMessage(e.getLocalizedMessage());
            e.printStackTrace();
        }

        return response;
    }

    @RequestMapping("/transferorderpreedit.htm")
    public ModelAndView preedit(@RequestParam("id") Long id) throws ServiceException
    {
        return new ModelAndView("/inventory/warehouse-management/transferOrderUpdate", service.preedit(id));
    }

    @RequestMapping("/transferorderedit.htm")
    public ModelAndView edit(@ModelAttribute("transfer_edit") TransferOrder transferOrder, SessionStatus status) throws ServiceException
    {
        JSONResponse response = new JSONResponse();

        try
        {
            service.edit(transferOrder);
            status.setComplete();

            response.store("id", transferOrder.getId());
        }
        catch (Exception e)
        {
            response.setStatus(ResponseStatus.ERROR);
            response.setMessage(e.getLocalizedMessage());
        }

        return response;
    }
}
