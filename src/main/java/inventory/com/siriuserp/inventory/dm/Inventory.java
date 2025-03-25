/**
 * 
 */
package com.siriuserp.inventory.dm;

import java.math.BigDecimal;

import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Grid;
import com.siriuserp.sdk.dm.Lot;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.Tag;

/**
 * @author ferdinand
 */

public interface Inventory 
{
	public Product getProduct();
	public Container getContainer();
	public Party getOrganization();
	
	public BigDecimal getOnHand();
	public BigDecimal getReserved();
	public BigDecimal getOnTransfer();
	
	public Lot getLot();
	public Tag getTag();
	
	public void setOnHand(BigDecimal onHand);
	public void setReserved(BigDecimal quantity);
	public void setOnTransfer(BigDecimal onTransfer);
	public void setProduct(Product product);
	public void setContainer(Container container);
	public void setGrid(Grid grid);
	public void setOrganization(Party organization);
}
