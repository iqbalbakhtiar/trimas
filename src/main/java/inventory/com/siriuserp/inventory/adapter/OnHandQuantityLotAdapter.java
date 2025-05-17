/**
 * File Name  : OnHandQuantityLotAdapter.java
 * Created On : May 17, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.inventory.adapter;

import java.math.BigDecimal;

import com.siriuserp.inventory.dm.Product;
import com.siriuserp.sdk.adapter.AbstractUIAdapter;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Lot;
import com.siriuserp.sdk.dm.Party;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Getter
@Setter
@NoArgsConstructor
public class OnHandQuantityLotAdapter extends AbstractUIAdapter
{
	private static final long serialVersionUID = -661336239975459836L;

	private Party organization;
	private Facility facility;
	private Container container;
	private Product product;
	private Lot lot;

	private BigDecimal onHand = BigDecimal.ZERO;
	private BigDecimal reserved = BigDecimal.ZERO;
	private BigDecimal onTransfer = BigDecimal.ZERO;

	public OnHandQuantityLotAdapter(Product product, Lot lot, BigDecimal onHand, BigDecimal onTransfer, BigDecimal reserved)
	{
		this.product = product;
		this.lot = lot;
		this.onHand = onHand;
		this.onTransfer = onTransfer;
		this.reserved = reserved;
	}

	public OnHandQuantityLotAdapter(Party organization, Container container, Product product, Lot lot, BigDecimal onHand, BigDecimal onTransfer, BigDecimal reserved)
	{
		this.organization = organization;
		this.container = container;
		this.product = product;
		this.lot = lot;
		this.onHand = onHand;
		this.onTransfer = onTransfer;
		this.reserved = reserved;
	}

	public BigDecimal getAvailableSale()
	{
		return getOnHand().subtract(getReserved());
	}

	public BigDecimal getTotal()
	{
		return getOnHand().add(getOnTransfer());
	}
}
