package com.siriuserp.inventory.service;

import com.siriuserp.inventory.dm.Issueable;
import com.siriuserp.inventory.dm.Receiptable;
import com.siriuserp.sdk.annotation.AutomaticSibling;
import com.siriuserp.sdk.exceptions.ServiceException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author
 * Betsu Brahmana Restu
 * PT. Sirius Indonesia
 * betsu@siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class AutoWarehouseService
{
    @AutomaticSibling(roles="DelInventorySiblingRole")
    public void autoIssue(Issueable warehouse) throws ServiceException {
    }

    @AutomaticSibling(roles="AddInventorySiblingRole")
    public void autoReceipt(Receiptable warehouse) throws ServiceException {
    }

    @AutomaticSibling(roles="DelReverseInventorySiblingRole")
    public void removeIssue(Issueable warehouse) throws ServiceException {
    }

    @AutomaticSibling(roles="AddReverseInventorySiblingRole")
    public void removeReceipt(Receiptable warehouse) throws ServiceException {
    }
}
