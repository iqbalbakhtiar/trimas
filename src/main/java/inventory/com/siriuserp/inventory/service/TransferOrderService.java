package com.siriuserp.inventory.service;

import com.siriuserp.inventory.criteria.TransferOrderFilterCriteria;
import com.siriuserp.inventory.dm.TransferOrder;
import com.siriuserp.inventory.dm.TransferOrderItem;
import com.siriuserp.inventory.dm.TransferType;
import com.siriuserp.inventory.dm.WarehouseTransactionType;
import com.siriuserp.inventory.form.InventoryForm;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;
import com.siriuserp.sdk.utility.ReferenceItemHelper;
import com.siriuserp.sdk.utility.SiriusValidator;
import javolution.util.FastMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * @author ferdinand
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class TransferOrderService
{
    @Autowired
    private GenericDao genericDao;

    @Autowired
    private CodeSequenceDao codeSequenceDao;

    @Autowired
    private AutoWarehouseService warehouseService;

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws ServiceException
    {
        FastMap<String, Object> map = new FastMap<String, Object>();

        TransferOrderFilterCriteria criteria = (TransferOrderFilterCriteria) filterCriteria;

        map.put("filterCriteria", filterCriteria);
        map.put("orders", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));
        map.put("org", SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()) ? genericDao.load(Party.class, criteria.getOrganization()) : null);

        return map;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @InjectParty(keyName = "transfer_add")
    public Map<String, Object> preadd() throws ServiceException
    {
        Map<String, Object> map = new FastMap<String, Object>();

        map.put("transfer_add", new InventoryForm());
        map.put("currencies", genericDao.loadAll(Currency.class));
        map.put("transferTypes", TransferType.values());

        return map;
    }

    @AuditTrails(className = TransferOrder.class, actionType = AuditTrailsActionType.CREATE)
    public void add(TransferOrder transferOrder) throws Exception
    {
        Assert.notEmpty(transferOrder.getForm().getItems(), "Empty item transaction, please recheck !");

        transferOrder.setCode(GeneratorHelper.instance().generate(TableType.TRANSFER_ORDER, codeSequenceDao));

        for (Item item : transferOrder.getForm().getItems())
            if (SiriusValidator.gz(item.getQuantity()))
            {
                TransferOrderItem transferOrderItem = new TransferOrderItem();
                transferOrderItem.setProduct(item.getProduct());
                transferOrderItem.setQuantity(item.getQuantity());
                transferOrderItem.setTransferOrder(transferOrder);
                transferOrderItem.getLot().setSerial(item.getSerial());
                transferOrderItem.getLot().setCode(item.getLotCode());
                transferOrderItem.setFacilitySource(transferOrder.getSource());
                transferOrderItem.setFacilityDestination(transferOrder.getDestination());
                transferOrderItem.setTransactionItem(ReferenceItemHelper.init(genericDao, item.getQuantity(), WarehouseTransactionType.INTERNAL, transferOrderItem));

                transferOrderItem.setSourceGrid(item.getSource().getGrid());
                transferOrderItem.setSourceContainer(item.getSource());

                transferOrderItem.setDestinationGrid(item.getContainer().getGrid());
                transferOrderItem.setDestinationContainer(item.getContainer());

                transferOrder.getItems().add(transferOrderItem);
            }

        genericDao.add(transferOrder);

        //Auto Issue
        warehouseService.autoIssue(transferOrder);

        //Auto Receipt
        warehouseService.autoReceipt(transferOrder);
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> preedit(Long id) throws ServiceException
    {
        FastMap<String, Object> map = new FastMap<String, Object>();
        map.put("transfer_edit", genericDao.load(TransferOrder.class, id));

        return map;
    }

    @AuditTrails(className = TransferOrder.class, actionType = AuditTrailsActionType.UPDATE)
    public void edit(TransferOrder transferOrder) throws ServiceException
    {
        genericDao.update(transferOrder);
    }
}
