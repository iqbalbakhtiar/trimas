package com.siriuserp.production.dm;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.siriuserp.inventory.dm.Product;
import com.siriuserp.sdk.dm.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ferdinand
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "production_order_item")
public class ProductionOrderItem extends Model
{
	private static final long serialVersionUID = -4370267147655401289L;
	
	@Column(name = "quantity")
	private BigDecimal quantity = BigDecimal.ZERO;
	
	@Column(name = "cogs_weight")
	private BigDecimal cogsWeight = BigDecimal.ZERO;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "material_type")
	private MaterialType materialType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_product")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Product product;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_production_order")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private ProductionOrder productionOrder;

	@Override
	public String getAuditCode() {
		return this.id+"";
	}
}
