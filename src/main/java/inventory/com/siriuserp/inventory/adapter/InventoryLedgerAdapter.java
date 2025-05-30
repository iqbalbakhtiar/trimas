package com.siriuserp.inventory.adapter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import com.siriuserp.sdk.dm.Grid;

import javolution.util.FastList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Betsu Brahmana Restu
 * Sirius Indonesia, PT
 * betsu@siriuserp.com
 * 
 * Version 1.5
 */
@Setter
@Getter
@AllArgsConstructor
public class InventoryLedgerAdapter
{
	private BigDecimal quantity = BigDecimal.ZERO;
	private BigDecimal in = BigDecimal.ZERO;
	private BigDecimal out = BigDecimal.ZERO;
	private BigDecimal reserved = BigDecimal.ZERO;
	private BigDecimal cogs = BigDecimal.ZERO;

	private String productCode;
	private String productName;
	private String containerName;
	private String facilityName;
	private String gridName;

	private String uom;
	private String note;
	
	private Long facilityId;
	private Long productId;
	private Long containerId;

	private Grid grid;
	
	private String description;
	private String reference;
	
	private Date date;

	private Long referenceId;
	private Long descriptionId;

	private String referenceUri;
	private String referenceCode;
	private String descriptionUri;

	private String referenceType;
	private String descriptionType;

	public Grid getGrid()
	{
		return grid;
	}

	public void setGrid(Grid grid)
	{
		this.grid = grid;
	}

	private FastList<InventoryLedgerAdapter> adapters = new FastList<InventoryLedgerAdapter>();

	public InventoryLedgerAdapter()
	{
	}

	//Inventory Ledger Summary Production
	public InventoryLedgerAdapter(BigDecimal quantity, BigDecimal in, BigDecimal out, String facilityName, String gridName, String containerName, String productCode, String productName, String uom, Long facilityId, Grid grid)
	{
		this.quantity = quantity;
		this.in = in;
		this.out = out;

		this.facilityName = facilityName;
		this.gridName = gridName;
		this.containerName = containerName;
		this.productCode = productCode;
		this.productName = productName;
		this.uom = uom;

		this.facilityId = facilityId;
		this.grid = grid;
	}
	
	//Inventory Ledger Detail & Reference
	public InventoryLedgerAdapter(BigDecimal in, BigDecimal out, Date date, String description, String reference, Long descriptionId, Long referenceId, String descriptionType, String referenceType, String descriptionUri, String referenceUri,
			String note)
	{
		this.in = in;
		this.out = out;
		this.date = date;

		this.description = description;
		this.reference = reference;

		this.descriptionId = descriptionId;
		this.referenceId = referenceId;

		this.descriptionType = descriptionType;
		this.referenceType = referenceType;

		this.descriptionUri = descriptionUri;
		this.referenceUri = referenceUri;
		this.note = note;
	}
	
	//Inventory Ledger Detail Opening
	public InventoryLedgerAdapter(BigDecimal in, BigDecimal out, String facilityName, String contaninerName, String productCode, String productName, Long facilityId, Long containerId, Long productId,
			BigDecimal reserved, Grid grid)
	{
		this.in = in;
		this.out = out;

		this.facilityName = facilityName;
		this.containerName = contaninerName;
		this.productCode = productCode;
		this.productName = productName;

		this.facilityId = facilityId;
		this.containerId = containerId;
		this.productId = productId;
		this.reserved = reserved;
		this.grid = grid;
	}

	/**
	 * Used in {@link com.siriuserp.inventory.query.MutationReportQuery}
	 */
	public InventoryLedgerAdapter(BigDecimal in, BigDecimal out, BigDecimal cogs, String productCode, String productName) {
		this.in = in;
		this.out = out;
		this.cogs = cogs;
		this.productCode = productCode;
		this.productName = productName;
	}
	
	//For OnHandQuantityByDateReportQuery
	public InventoryLedgerAdapter(BigDecimal quantity, String productName, String containerName, Date date) {
		this.quantity = quantity;
		this.productName = productName;
		this.containerName = containerName;
		this.date = date;
	}

	public BigDecimal getBalance()
	{
		return getIn().subtract(getOut());
	}

	public BigDecimal getSum()
	{
		return getQuantity().add(getIn()).subtract(getOut());
	}
	
	public BigDecimal getBale()
	{
		return getQuantity().divide(BigDecimal.valueOf(181.44), 2, RoundingMode.HALF_UP);
	}
}
