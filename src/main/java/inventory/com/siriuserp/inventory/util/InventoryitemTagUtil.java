package com.siriuserp.inventory.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.siriuserp.administration.util.LotHelper;
import com.siriuserp.inventory.dao.DataWarehouseDao;
import com.siriuserp.inventory.dm.Inventoriable;
import com.siriuserp.inventory.dm.Inventory;
import com.siriuserp.inventory.dm.InventoryControl;
import com.siriuserp.inventory.dm.InventoryItem;
import com.siriuserp.sdk.db.OrderType;
import com.siriuserp.sdk.dm.Lot;
import com.siriuserp.sdk.dm.Tag;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastList;

@Component
@Transactional(rollbackFor = Exception.class)
public class InventoryitemTagUtil {
    @Autowired
    private DataWarehouseDao dataWarehouseDao;

    private static final int IN = 0;
    private static final int OUT = 1;

    public void in(Class inventory, Inventoriable inventoriable) throws Exception {
        invent(inventory, inventoriable, IN);
    }

    private List<InventoryControl> invent(Class inventory, Inventoriable inventoriable, int type) throws Exception {
        FastList<InventoryControl> controls = new FastList<InventoryControl>();

        Assert.notNull(inventoriable, "Inventory invalid");
        Assert.notNull(inventoriable.getProduct(), "Product can't be empty");
        Assert.notNull(inventoriable.getGrid(), "Grid can't be empty");
        Assert.notNull(inventoriable.getContainer(), "Container can't be empty");
        Assert.notNull(inventoriable.getOrganization(), "Organization can't be empty");

        switch (type)
        {
            case IN:
                Inventory inventoryItem = load(InventoryItem.class, inventoriable.getProduct().getId(), inventoriable.getGrid().getId(), inventoriable.getContainer().getId(),
                        inventoriable.getLot(), inventoriable.getTag());

                if (inventoryItem != null)
                {
                    inventoryItem.setOnHand(inventoryItem.getOnHand().add(inventoriable.getQuantity()));

                    dataWarehouseDao.update(inventoryItem);
                }
                else
                {
                    inventoryItem = create(inventory, inventoriable);
                    inventoryItem.setOnHand(inventoryItem.getOnHand().add(inventoriable.getQuantity()));

                    dataWarehouseDao.add(inventoryItem);
                }
                break;

            case OUT:
                List<Inventory> invents = inventories(inventory, inventoriable);

                Assert.notEmpty(invents, inventoriable.getProduct().getName()+" ["+inventoriable.getContainer().getName()+"]"+ "["+LotHelper.getCompare(inventoriable.getLot())+"]"
                        + "doesnot exist, Stock Adjustment/Transfer Order/Goods Receipt first !");

                BigDecimal buffer = inventoriable.getQuantity().abs();

                for (Inventory invent : invents)
                {
                    if (buffer.compareTo(BigDecimal.ZERO) <= 0)
                        break;

                    InventoryControl control = new InventoryControl();

                    if(invent.getLot() != null)
                    {
                        control.setCode(SiriusValidator.validateParam(invent.getLot().getCode())
                                ? invent.getLot().getCode().toUpperCase() : invent.getLot().getCode());
                        control.setSerial(SiriusValidator.validateParam(invent.getLot().getSerial())
                                ? invent.getLot().getSerial().toUpperCase() : invent.getLot().getSerial());

                        control.setInfo(invent.getLot().getInfo());
                    }

//                    if(invent.getTag() != null)
//                    {
//                        control.setInventoryReference(invent.getTag().getInventoryReference());
//                        control.setInventoryType(invent.getTag().getInventoryType());
//                    }

                    if (invent.getOnHand().compareTo(buffer) >= 0)
                    {
                        control.setBuffer(buffer);

                        invent.setOnHand(invent.getOnHand().subtract(buffer));

                        buffer = BigDecimal.ZERO;
                    }
                    else
                    {
                        control.setBuffer(invent.getOnHand());

                        buffer = buffer.subtract(invent.getOnHand());

                        invent.setOnHand(BigDecimal.ZERO);
                    }

                    control.setQuantity(control.getBuffer());

                    controls.add(control);

                    dataWarehouseDao.update(invent);
                }

                if (buffer.compareTo(BigDecimal.ZERO) > 0)
                    throw new ServiceException(inventoriable.getProduct().getName()+" ["+inventoriable.getContainer().getName()+"] "
                            + "["+LotHelper.getCompare(inventoriable.getLot())+"]" + "On hand quantity need to be added["+buffer+"]");
                break;
        }

        return controls;
    }

    private InventoryItem load(Class inventory, Long product, Long grid, Long container, Lot lot, Tag tag)
    {
        return (InventoryItem)dataWarehouseDao.loadInventory(inventory, product, grid, container, lot, tag);
    }

    private Inventory create(Class inventory, Inventoriable inventoriable) throws Exception {
        Inventory inventoryItem = (Inventory) Class.forName(inventory.getCanonicalName()).getDeclaredConstructor().newInstance();
        inventoryItem.setProduct(inventoriable.getProduct());
        inventoryItem.setContainer(inventoriable.getContainer());

        inventoryItem.setGrid(inventoriable.getGrid());
        inventoryItem.setOrganization(inventoriable.getOrganization());

        if(inventoriable.getLot() != null)
        {
            inventoryItem.getLot().setCode(SiriusValidator.validateParam(inventoriable.getLot().getCode())
                    ? inventoriable.getLot().getCode().toUpperCase() : inventoriable.getLot().getCode());
            inventoryItem.getLot().setSerial(SiriusValidator.validateParam(inventoriable.getLot().getSerial())
                    ? inventoriable.getLot().getSerial().toUpperCase() : inventoriable.getLot().getSerial());

//            inventoryItem.getLot().setExpDate(inventoriable.getLot().getExpDate());
            inventoryItem.getLot().setInfo(inventoriable.getLot().getInfo());
        }

        if(inventoriable.getTag() != null)
        {
//            inventoryItem.getTag().setInventoryReference(inventoriable.getTag().getInventoryReference());
            inventoryItem.getTag().setInventoryType(inventoriable.getTag().getInventoryType());
        }

        return inventoryItem;
    }

    private List<Inventory> inventories(Class inventory, Inventoriable inventoriable)
    {
        return loads(inventory, inventoriable.getProduct().getId(), inventoriable.getGrid().getId(), inventoriable.getContainer().getId(),
                inventoriable.getLot(), inventoriable.getTag(), inventoriable.getOrderType());
    }

    private List<Inventory> loads(Class inventory, Long product, Long grid, Long container, Lot lot, Tag tag, OrderType orderType)
    {
        return dataWarehouseDao.loadInventories(inventory, product, grid, container, lot, tag, orderType);
    }

    public void trin(Class inventory, Set<InventoryControl> internalInventories, Inventoriable inventoriable) throws Exception {
        transfer(inventory, inventoriable, IN);
    }

    private void transfer(Class inventory, Inventoriable inventoriable, int type) throws Exception {
        Inventory inventoryItem = load(inventory, inventoriable.getProduct().getId(), inventoriable.getSourceGrid().getId(),
                inventoriable.getSourceContainer().getId(), inventoriable.getLot(), inventoriable.getTag());

        switch (type)
        {
            case IN:
                inventoryItem.setOnTransfer(inventoryItem.getOnTransfer().subtract(inventoriable.getQuantity()));
                break;

            case OUT:
                inventoryItem.setOnTransfer(inventoryItem.getOnTransfer().add(inventoriable.getQuantity()));
                break;
        }

        if (inventoryItem.getOnTransfer().compareTo(BigDecimal.ZERO) < 0)
            inventoryItem.setOnTransfer(BigDecimal.ZERO);

        dataWarehouseDao.update(inventoryItem);
    }
}
