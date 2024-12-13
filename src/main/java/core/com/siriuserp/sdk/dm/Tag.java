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
	
	public static final synchronized Tag stock() {
		Tag tag = new Tag();
		tag.setInventoryType(InventoryType.STOCK);

        return tag;
	}
	
	public static final synchronized Tag shrink() {
		Tag tag = new Tag();
		tag.setInventoryType(InventoryType.SHRINK);

        return tag;
	}
}