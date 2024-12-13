/**
 * 
 */
package com.siriuserp.sales.dm;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.siriuserp.inventory.dm.InventoryType;
import com.siriuserp.inventory.dm.Product;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Grid;
import com.siriuserp.sdk.dm.Lot;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.ReserveBridge;
import com.siriuserp.sdk.dm.Tag;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Betsu Brahmana Restu
 * Sirius Indonesia, PT
 * betsu@siriuserp.com
 *
 */

@Getter
@Setter
@Entity
@Table(name="delivery_order_realization_reserve_bridge")
public class DeliveryOrderRealizationReserveBridge extends ReserveBridge {

	private static final long serialVersionUID = 1299936089799302368L;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_realization_item")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private DeliveryOrderRealizationItem realizationItem;

	@Override
	public Date getDate() {
		return getRealizationItem().getDate();
	}

	@Override
	public Product getProduct() {
		return getRealizationItem().getProduct();
	}

	@Override
	public Container getContainer() {
		return getRealizationItem().getContainer();
	}

	@Override
	public Grid getGrid() {
		return getRealizationItem().getGrid();
	}
	
	@Override
	public Party getOrganization() {
		return getRealizationItem().getOrganization();
	}

	@Override
	public Lot getLot() {
		return new Lot();
	}
	
	@Override
	public Tag getTag() {
		Tag tag = new Tag();
		tag.setInventoryType(InventoryType.SHRINK);

        return tag;
	}
}
