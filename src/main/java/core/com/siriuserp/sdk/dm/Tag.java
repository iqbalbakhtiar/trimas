/**
 * 
 */
package com.siriuserp.sdk.dm;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.siriuserp.inventory.dm.InventoryType;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ferdinand
 */

@Getter
@Setter
@Embeddable
public class Tag 
{
	@Enumerated(EnumType.STRING)
	@Column(name = "inventory_type")
	private InventoryType inventoryType = InventoryType.STOCK;
}