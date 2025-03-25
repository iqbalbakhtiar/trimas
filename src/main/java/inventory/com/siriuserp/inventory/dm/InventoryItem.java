/**
 * 
 */
package com.siriuserp.inventory.dm;

import java.math.BigDecimal;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Grid;
import com.siriuserp.sdk.dm.JSONSupport;
import com.siriuserp.sdk.dm.Lot;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.Tag;

import javolution.util.FastMap;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ferdinand
 */

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "inventory_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InventoryItem extends Model implements Inventory, JSONSupport
{
	private static final long serialVersionUID = -3300521009881633911L;

	@Formula("(on_hand_quantity - reserved)")
	private BigDecimal availableSale;

	@Formula("(on_hand_quantity + on_transfer)")
	private BigDecimal total;

	@Embedded
	private Lot lot = new Lot();

	@Embedded
	private Tag tag = new Tag();

	@Column(name = "on_hand_quantity")
	private BigDecimal onHand = BigDecimal.ZERO;

	@Column(name = "on_transfer")
	private BigDecimal onTransfer = BigDecimal.ZERO;

	@Column(name = "reserved")
	private BigDecimal reserved = BigDecimal.ZERO;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_product")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_party_organization")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party organization;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_container")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Container container;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_grid")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Grid grid;

	@Override
	public Map<String, Object> val()
	{
		Map<String, Object> map = new FastMap<String, Object>();
		map.put("id", getId());
		map.put("product", getProduct());
		map.put("grid", getGrid());
		map.put("container", getContainer());
		map.put("onHand", getOnHand());
		map.put("available", getAvailableSale());
		map.put("serial", getLot() == null ? "" : getLot().getSerial());
		map.put("lotCode", getLot() == null ? "" : getLot().getCode());

		return map;
	}

	@Override
	public String getAuditCode()
	{
		return getId() + "";
	}
}
