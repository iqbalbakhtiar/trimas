/**
 * Apr 27, 2009 3:08:58 PM
 * com.siriuserp.inventory.adapter
 * OnhandQuantityUIAdapter.java
 */
package com.siriuserp.inventory.adapter;

import java.math.BigDecimal;
import java.util.Map;

import com.siriuserp.inventory.dm.Product;
import com.siriuserp.sdk.adapter.AbstractUIAdapter;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Grid;
import com.siriuserp.sdk.dm.JSONSupport;
import com.siriuserp.sdk.dm.Lot;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.Tag;

import javolution.util.FastList;
import javolution.util.FastMap;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Getter
@Setter
@NoArgsConstructor
public class OnhandQuantityUIAdapter extends AbstractUIAdapter implements JSONSupport
{
	private static final long serialVersionUID = 2209652964146662894L;

	private Party organization;
	private Facility facility;
	private Grid grid;
	private Container container;
	private Product product;
	private Tag tag;
	private Lot lot;

	private BigDecimal onHand = BigDecimal.ZERO;
	private BigDecimal reserved = BigDecimal.ZERO;
	private BigDecimal onTransfer = BigDecimal.ZERO;

	private FastList<OnhandQuantityUIAdapter> details = new FastList<OnhandQuantityUIAdapter>();

	public OnhandQuantityUIAdapter(Product product, BigDecimal onHand, BigDecimal onTransfer, BigDecimal reserved)
	{
		this.product = product;
		this.onHand = onHand;
		this.onTransfer = onTransfer;
		this.reserved = reserved;
	}

	public OnhandQuantityUIAdapter(Container container, Lot lot, Product product, BigDecimal onHand, BigDecimal onTransfer, BigDecimal reserved, Party organization)
	{
		this.container = container;
		this.lot = lot;
		this.product = product;
		this.onHand = onHand;
		this.onTransfer = onTransfer;
		this.reserved = reserved;
		this.organization = organization;
	}

	public BigDecimal getAvailableSale()
	{
		return getOnHand().subtract(getReserved());
	}

	public BigDecimal getTotal()
	{
		return getOnHand().add(getOnTransfer());
	}

	@Override
	public Map<String, Object> val()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("organization", getOrganization());
		map.put("product", getProduct());
		map.put("container", getContainer());
		map.put("grid", getGrid());
		map.put("tag", getTag());
		map.put("lot", getLot());
		map.put("onhand", getOnHand());
		map.put("ontransfer", getOnTransfer());
		map.put("available", getAvailableSale());

		return map;
	}
}
