package com.siriuserp.sales.service;

import com.siriuserp.sales.dm.*;
import com.siriuserp.sales.form.DeliveryOrderForm;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.*;
import javolution.util.FastMap;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(rollbackFor = Exception.class)
public class DeliveryOrderRealizationService extends Service {

    @Autowired
    private GenericDao genericDao;

    @Autowired
    private CodeSequenceDao codeSequenceDao;

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends AbstractGridViewQuery> queryclass) throws Exception {
        FastMap<String, Object> map = new FastMap<String, Object>();
        map.put("dors", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));
        map.put("filterCriteria", filterCriteria);

        return map;
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preadd2(Long id) {
		DeliveryOrder deliveryOrder = genericDao.load(DeliveryOrder.class, id);
		DeliveryOrderForm form = new DeliveryOrderForm();

		for (DeliveryOrderItem doItem: deliveryOrder.getItems()) {
            Item item = new Item();
            item.setSalesReferenceItem(doItem.getSalesReferenceItem());

			form.getItems().add(item);
		}

		form.setDeliveryOrder(deliveryOrder);

        FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("dor_form", form);

		return map;
	}

    @AuditTrails(className = DeliveryOrderRealization.class, actionType = AuditTrailsActionType.CREATE)
    public FastMap<String, Object> add(DeliveryOrderRealization dor) throws Exception {
        DeliveryOrderForm form = (DeliveryOrderForm) dor.getForm();

        // Set DOR Value
        dor.setCode(GeneratorHelper.instance().generate(TableType.DELIVERY_ORDER_REALIZATION, codeSequenceDao));
        dor.setOrganization(form.getDeliveryOrder().getOrganization());
        dor.setFacility(form.getDeliveryOrder().getFacility());
        dor.setCustomer(form.getDeliveryOrder().getCustomer());

        genericDao.add(dor);

        for (Item item: form.getItems()) {
            // Set DOR Item
            DeliveryOrderRealizationItem dorItem = new DeliveryOrderRealizationItem();
            dorItem.setDeliveryOrderRealization(dor);
            dorItem.setDeliveryOrderItem(item.getSalesReferenceItem().getDeliveryOrderItem());
            dorItem.setAccepted(item.getAccepted());
            dorItem.setShrinkage(item.getShrinkage());

            // Set Warehouse Reference Value
            dorItem.setReferenceId(dor.getId());
            dorItem.setReferenceCode(dor.getCode());
            dorItem.setDate(dor.getDate());
            dorItem.setReferenceFrom(dor.getFacility().getName());
            dorItem.setReferenceTo(dor.getCustomer().getFullName());
            dorItem.setOrganization(dor.getOrganization());
            dorItem.setParty(dor.getCustomer());
            dorItem.setFacilitySource(dor.getFacility());
            dorItem.setProduct(item.getSalesReferenceItem().getProduct());
            dorItem.setTax(item.getSalesReferenceItem().getTax());
            dorItem.setCurrency(item.getSalesReferenceItem().getMoney().getCurrency());
            dorItem.setNote(item.getNote());

            genericDao.add(dorItem);
        }

        // Update DO Status
        form.getDeliveryOrder().setStatus(SOStatus.DELIVERED);
        form.getDeliveryOrder().setUpdatedBy(getPerson());
        form.getDeliveryOrder().setUpdatedDate(DateHelper.now());
        genericDao.update(form.getDeliveryOrder());

        FastMap<String, Object> map = new FastMap<String, Object>();
        map.put("id", dor.getId());

        return map;
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public FastMap<String,Object> preedit(Long id) throws Exception {
        FastMap<String, Object> map = new FastMap<String, Object>();
        DeliveryOrderRealization deliveryOrderRealization = genericDao.load(DeliveryOrderRealization.class, id);
        DeliveryOrderForm form = FormHelper.bind(DeliveryOrderForm.class, deliveryOrderRealization);

        form.setDeliveryOrderRealization(deliveryOrderRealization);
        // Get Shipping Address from one of DO Item's
        for (DeliveryOrderRealizationItem item: deliveryOrderRealization.getItems()){
            if (item.getDeliveryOrderItem().getSalesReferenceItem().getShippingAddress() != null) {
                form.setShippingAddress(item.getDeliveryOrderItem().getSalesReferenceItem().getShippingAddress());
                break;
            }
        }
        // Get Delivery Order from one of DOR Items
        for (DeliveryOrderRealizationItem dorItem: deliveryOrderRealization.getItems()) {
            if (dorItem.getDeliveryOrderItem().getDeliveryOrder() != null) {
                form.setDeliveryOrder(dorItem.getDeliveryOrderItem().getDeliveryOrder());
                break;
            }
        }

        map.put("dor_form", form);

        return map;
    }

    @AuditTrails(className = DeliveryOrderRealization.class, actionType = AuditTrailsActionType.UPDATE)
    public void edit(DeliveryOrderForm form) throws Exception {
        form.getDeliveryOrderRealization().setUpdatedBy(getPerson());
        form.getDeliveryOrderRealization().setUpdatedDate(DateHelper.now());

        genericDao.update(form.getDeliveryOrderRealization()); // Update DOR Directly from JSP
    }
}
