/**
 * File Name  : InventoryItemAdapter.java
 * Created On : Mar 24, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.inventory.adapter;

import java.math.BigDecimal;
import java.util.Map;

import com.siriuserp.inventory.dm.InventoryItem;
import com.siriuserp.inventory.dm.Product;
import com.siriuserp.sdk.adapter.AbstractUIAdapter;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.JSONSupport;
import com.siriuserp.sdk.dm.Lot;

import javolution.util.FastMap;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Getter
@Setter
public class InventoryItemAdapter extends AbstractUIAdapter implements JSONSupport
{
	private static final long serialVersionUID = 3212054855193905787L;

	private InventoryItem inventoryItem;
	private Product product;
	private Container container;
	private Facility facility;
	private BigDecimal availableSale;
	private BigDecimal onTransfer;

	private boolean enabled = false;
	private Lot lot = new Lot();

	public InventoryItemAdapter(Product product, Lot lot, BigDecimal availableSale, Container container)
	{
		this.product = product;
		this.lot = lot;
		this.availableSale = availableSale;
		this.container = container;
	}

	@Override
	public Map<String, Object> val()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("product", getProduct());
		map.put("container", getContainer());
		map.put("available", getAvailableSale());

		return map;
	}
}
