/**
 * 
 */
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

/**
 * @author
 * Betsu Brahmana Restu
 * PT. Sirius Indonesia
 * betsu@siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
@SuppressWarnings({"rawtypes","unchecked"})
public class InventoryItemTagUtil
{
	@Autowired
	private DataWarehouseDao dataWarehouseDao;

	private static final int IN = 0;
	private static final int OUT = 1;

	public void in(Class inventory, Inventoriable inventoriable) throws Exception {
		invent(inventory, inventoriable, IN);
	}

	public List<InventoryControl> out(Class inventory, Inventoriable inventoriable) throws Exception {
		return invent(inventory, inventoriable, OUT);
	}
	
	private List<Inventory> inventories(Class inventory, Inventoriable inventoriable)
	{
		return loads(inventory, inventoriable.getProduct().getId(), inventoriable.getGrid().getId(), inventoriable.getContainer().getId(), 
			inventoriable.getLot(), inventoriable.getTag(), inventoriable.getOrderType());
	}

	private InventoryItem load(Class inventory, Long product, Long grid, Long container, Lot lot, Tag tag)
	{
		return (InventoryItem)dataWarehouseDao.loadInventory(inventory, product, grid, container, lot, tag);
	}
	
	private List<Inventory> loads(Class inventory, Long product, Long grid, Long container, Lot lot, Tag tag, OrderType orderType)
	{
		return dataWarehouseDao.loadInventories(inventory, product, grid, container, lot, tag, orderType);
	}
	
//	private List<Inventory> revs(Class inventory, Long product, Long grid, Long container, Lot lot, Tag tag, OrderType orderType)
//	{
//		return dataWarehouseDao.loadReserves(inventory, product, grid, container, lot, tag, orderType);
//	}

	public void trin(Class inventory, Inventoriable inventoriable) throws Exception {
		transfer(inventory, inventoriable, IN);
	}
	
	public void trout(Class inventory, Inventoriable inventoriable) throws Exception {
		transfer(inventory, inventoriable, OUT);
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
	
	public void trin(Class inventory, Set<InventoryControl> controlls, Inventoriable inventoriable) throws Exception {
		BigDecimal buffer = inventoriable.getQuantity();
		BigDecimal in = BigDecimal.ZERO;
		
		for(InventoryControl inventoryControl : controlls)
			if(SiriusValidator.gz(inventoryControl.getBuffer()))
			{
				InventoryControl control = dataWarehouseDao.load(InventoryControl.class, inventoryControl.getId());
				
				if (buffer.compareTo(BigDecimal.ZERO) <= 0)
					break;
	
				if (control.getBuffer().compareTo(buffer) >= 0)
				{
					in = buffer;
					control.setBuffer(control.getBuffer().subtract(buffer));
	
					buffer = BigDecimal.ZERO;
				} 
				else
				{
					in = control.getBuffer();
					buffer = buffer.subtract(control.getQuantity());
	
					control.setBuffer(BigDecimal.ZERO);
				}
	
				dataWarehouseDao.update(control);
				
				Inventory inventoryItem = load(inventory, control.getProduct().getId(), inventoriable.getSourceGrid().getId(),
					inventoriable.getSourceContainer().getId(), control.getLot(), control.getTag());
				inventoryItem.setOnTransfer(inventoryItem.getOnTransfer().subtract(in));
				
				if (inventoryItem.getOnTransfer().compareTo(BigDecimal.ZERO) < 0)
					inventoryItem.setOnTransfer(BigDecimal.ZERO);
				
				dataWarehouseDao.update(inventoryItem);
			}
	}
	
	public void trout(Class inventory, Set<InventoryControl> controlls, Inventoriable inventoriable) throws Exception {
		BigDecimal buffer = inventoriable.getQuantity();
		BigDecimal out = BigDecimal.ZERO;
		
		for(InventoryControl control : controlls)
		{
			if (buffer.compareTo(BigDecimal.ZERO) <= 0)
				break;
	
			if (control.getQuantity().compareTo(buffer) >= 0)
			{
				out = buffer;
				control.setBuffer(control.getBuffer().add(buffer));
	
				buffer = BigDecimal.ZERO;
			} 
			else
			{
				buffer = buffer.subtract(control.getQuantity());
	
				control.setBuffer(control.getQuantity());
				
				out = control.getBuffer();
			}
			
			dataWarehouseDao.update(control);
			
			Inventory inventoryItem = load(inventory, inventoriable.getProduct().getId(), inventoriable.getSourceGrid().getId(), 
				inventoriable.getSourceContainer().getId(), inventoriable.getLot(), inventoriable.getOriginTag());

			inventoryItem.setOnTransfer(inventoryItem.getOnTransfer().add(out));
			
			if (inventoryItem.getOnTransfer().compareTo(BigDecimal.ZERO) < 0)
				inventoryItem.setOnTransfer(BigDecimal.ZERO);

			dataWarehouseDao.update(inventoryItem);
		}
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
					
					if(invent.getTag() != null)
					{
						control.getTag().setInventoryType(invent.getTag().getInventoryType());
					}

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

//	public void reserve(Class inventory, Reservable reservable) throws Exception {
//		Assert.notNull(reservable, "Reservable invalid");
//		Assert.notNull(reservable.getProduct(), "Product can't be empty");
//		Assert.notNull(reservable.getOrganization(), "Organization can't be empty");
//		Assert.notNull(reservable.getReserveBridge(), "Bridge can't be empty");
//
//		if (!reservable.isMinus())
//		{
//			List<Inventory> invents = revs(inventory, reservable.getProduct().getId(),  
//				reservable.getGrid() !=null ? reservable.getGrid().getId() : null, reservable.getContainer() != null ? reservable.getContainer().getId() : null, 
//					reservable.getReserveBridge().getOriginLot(), reservable.getOriginTag(), reservable.getOrderType());
//			
//			Assert.notEmpty(invents, "Reserved Controll can't be empty");
//			
//			BigDecimal buffer = reservable.getQuantity().abs();
//			
//			for (Inventory reserve : invents)
//			{
//				if (buffer.compareTo(BigDecimal.ZERO) <= 0)
//					break;
//	
//				ReserveControl control = new ReserveControl();
//				control.setReserveBridge(reservable.getReserveBridge());
//	
//				if(reserve.getLot() != null)
//				{
//					control.setCode(SiriusValidator.validateParam(reserve.getLot().getCode()) 
//						? reserve.getLot().getCode().toUpperCase() : reserve.getLot().getCode());
//					control.setSerial(SiriusValidator.validateParam(reserve.getLot().getSerial()) 
//						? reserve.getLot().getSerial().toUpperCase() : reserve.getLot().getSerial());
//						
//					control.setInfo(reserve.getLot().getInfo());
//				}
//				
//				if(reserve.getTag() != null)
//				{
//					control.setInventoryReference(reserve.getTag().getInventoryReference());
//					control.setInventoryType(reserve.getTag().getInventoryType());
//				}
//				
//				BigDecimal available = reserve.getOnHand().subtract(reserve.getReserved());
//				
//				if (available.compareTo(buffer) >= 0)
//				{
//					control.setBuffer(buffer);
//					
//					reserve.setOnHand(reserve.getOnHand().subtract(buffer));
//	
//					buffer = BigDecimal.ZERO;
//				} 
//				else
//				{
//					control.setBuffer(available);
//					
//					buffer = buffer.subtract(available);
//	
//					reserve.setOnHand(BigDecimal.ZERO);
//				}
//	
//				control.setQuantity(control.getBuffer());
//	
//				dataWarehouseDao.update(reserve);
//	
//				reservable.getReserveBridge().getReserveControls().add(control);
//			}
//		}
//		
//		Assert.notNull(reservable.getReserveBridge().getGrid(), "Grid can't be empty");
//		Assert.notNull(reservable.getReserveBridge().getContainer(), "Container can't be empty");
//
//		Inventory inventoryItem = load(inventory, reservable.getProduct().getId(), 
//			reservable.getReserveBridge().getGrid().getId(), reservable.getReserveBridge().getContainer().getId(), reservable.getReserveBridge().getLot(), reservable.getReserveBridge().getTag());
//		
//		if(inventoryItem == null) {
//			inventoryItem = create(inventory, reservable.getReserveBridge());
//			
//			dataWarehouseDao.add(inventoryItem);
//		}
//
//		if (!reservable.isMinus())
//			inventoryItem.setOnHand(inventoryItem.getOnHand().add(reservable.getQuantity()));
//		
//		inventoryItem.setReserved(inventoryItem.getReserved().add(reservable.getQuantity()));
//
//		dataWarehouseDao.update(inventoryItem);
//	}

//	public void unreserve(Class inventory, Reservable reservable) throws Exception {
//		BigDecimal buffer = reservable.getQuantity();
//		BigDecimal out = BigDecimal.ZERO;
//		
//		for(ReserveControl control : reservable.getReserveBridge().getReserveControls())
//			if(SiriusValidator.gz(control.getBuffer()))
//			{
//				if (buffer.compareTo(BigDecimal.ZERO) <= 0)
//					break;
//	
//				if (control.getBuffer().compareTo(buffer) >= 0)
//				{
//					out = buffer;
//					control.setBuffer(control.getBuffer().subtract(buffer));
//	
//					buffer = BigDecimal.ZERO;
//				} 
//				else
//				{
//					out = control.getBuffer();
//					buffer = buffer.subtract(control.getQuantity());
//	
//					control.setBuffer(BigDecimal.ZERO);
//				}
//				
//				dataWarehouseDao.update(control);
//				
//				Inventory inventoryItem = load(inventory, control.getProduct().getId(), control.getReserveBridge().getGrid().getId(), control.getContainer().getId(), control.getLot(), control.getTag());
//				inventoryItem.setOnHand(inventoryItem.getOnHand().add(out));
//	
//				dataWarehouseDao.update(inventoryItem);
//			}
//
//		Inventory inventoryItem = load(inventory, reservable.getProduct().getId(), 
//			reservable.getReserveBridge().getGrid().getId(), reservable.getReserveBridge().getContainer().getId(), reservable.getReserveBridge().getLot(), reservable.getReserveBridge().getTag());
//		inventoryItem.setReserved(inventoryItem.getReserved().subtract(reservable.getQuantity()));
//		
//		if (!reservable.isMinus())
//			inventoryItem.setOnHand(inventoryItem.getOnHand().subtract(reservable.getQuantity()));
//
//		dataWarehouseDao.update(inventoryItem);
//	}

//	public void revin(Class inventory, Set<ReserveControl> controlls, Inventoriable inventoriable) throws Exception {
//		reserved(inventory, controlls, inventoriable, IN);
//	}
//	
//	public void revout(Class inventory, Set<ReserveControl> controlls, Inventoriable inventoriable) throws Exception {
//		reserved(inventory, controlls, inventoriable, OUT);
//	}
//
//	private void reserved(Class inventory, Set<ReserveControl> controlls, Inventoriable inventoriable, int type) throws Exception {
//		Inventory inventoryItem = (Inventory) load(inventory, inventoriable.getProduct().getId(), inventoriable.getGrid().getId(),
//			inventoriable.getContainer().getId(), inventoriable.getLot(), inventoriable.getTag());
//		
//		Assert.notNull(inventoryItem,"Product doesnot exist,Maybe u can do Stock Adjustment/Transfer Order/Goods Receipt first!");
//		
//		if(type == IN) {
//			BigDecimal buffer = inventoriable.getQuantity();
//
//			for(ReserveControl control : controlls)
//			{
//				if (buffer.compareTo(BigDecimal.ZERO) <= 0)
//					break;
//
//				if (control.getQuantity().compareTo(buffer) >= 0)
//				{
//					control.setBuffer(control.getBuffer().add(buffer));
//
//					buffer = BigDecimal.ZERO;
//				} 
//				else
//				{
//					buffer = buffer.subtract(control.getQuantity());
//
//					control.setBuffer(control.getQuantity());
//				}
//			}
//			
//			inventoryItem.setReserved(inventoryItem.getReserved().add(inventoriable.getQuantity()));
//		}
//		
//		if(type == OUT)  {
//			BigDecimal buffer = inventoriable.getQuantity();
//
//			for(ReserveControl control : controlls)
//				if(SiriusValidator.gz(control.getBuffer()))
//				{
//					if (buffer.compareTo(BigDecimal.ZERO) <= 0)
//						break;
//		
//					if (control.getBuffer().compareTo(buffer) >= 0)
//					{
//						control.setBuffer(control.getBuffer().subtract(buffer));
//		
//						buffer = BigDecimal.ZERO;
//					} 
//					else
//					{
//						buffer = buffer.subtract(control.getQuantity());
//		
//						control.setBuffer(BigDecimal.ZERO);
//					}
//				}
//			
//			inventoryItem.setReserved(inventoryItem.getReserved().subtract(inventoriable.getQuantity()));
//		}
//
//		dataWarehouseDao.update(inventoryItem);
//	}
	
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
			
			inventoryItem.getLot().setInfo(inventoriable.getLot().getInfo());
		}
		
		if(inventoriable.getTag() != null)
		{
			inventoryItem.getTag().setInventoryType(inventoriable.getTag().getInventoryType());
		}

		return inventoryItem;
	}
	
//	public <T> T  init(Class<T> tClass) throws Exception {
//		ReserveBridge bridge = (ReserveBridge)Class.forName(tClass.getCanonicalName()).getDeclaredConstructor().newInstance();
//		
//		return (T)bridge;
//	}
//	
//	public void position(Class inventory, Positionable inventoriable) throws Exception {
//		Inventory inventoryItem = (Inventory) load(inventory, inventoriable.getProduct().getId(), inventoriable.getGrid().getId(),
//			inventoriable.getSourceContainer().getId(), inventoriable.getExtLot(), inventoriable.getTag());
//
//		if (inventoryItem == null)
//			inventoryItem = (Inventory) load(inventory, inventoriable.getProduct().getId(), inventoriable.getGrid().getId(),
//				inventoriable.getDestinationContainer().getId(), inventoriable.getExtLot(), inventoriable.getTag());
//
//		Assert.notNull(inventoryItem, "Product doesnot exist,Maybe u can do Stock Adjustment/Transfer Order/Goods Receipt first!");
//		
//		inventoryItem.setPosition(inventoriable.getPosition());
//
//		dataWarehouseDao.update(inventoryItem);
//	}
}
