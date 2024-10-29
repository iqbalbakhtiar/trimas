/**
 * 
 */
package com.siriuserp.inventory.dm;

import java.math.BigDecimal;
import java.util.Date;

import com.siriuserp.sdk.db.OrderType;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Grid;
import com.siriuserp.sdk.dm.Lot;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.Tag;

/**
 * @author
 * Betsu Brahmana Restu
 * PT. Sirius Indonesia
 * betsu@siriuserp.com
 */
public interface Inventoriable 
{
	public Date getDate();
	public Product getProduct();
	public BigDecimal getQuantity();
	public Party getOrganization();
	
	public Container getContainer();
	public Grid getGrid();
	
	public Container getSourceContainer();
	public Grid getSourceGrid();
	
	public Container getDestinationContainer();
	public Grid getDestinationGrid();
	
	public Lot getLot();
	public Tag getTag();
	
	default public Lot getOriginLot() {
		return getLot();
	}
	
	default public Tag getOriginTag() {
		return getTag();
	}
	
	default public OrderType getOrderType() {
		return OrderType.ASC;
	}
}
