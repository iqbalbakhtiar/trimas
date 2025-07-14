package com.siriuserp.production.dm;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.siriuserp.inventory.dm.Product;
import com.siriuserp.inventory.dm.WarehouseReferenceItem;
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
@Table(name = "production_order_detail_item")
public class ProductionOrderDetailItem extends Model 
{
	private static final long serialVersionUID = -23669419674662749L;

	@Column(name = "quantity")
	private BigDecimal quantity = BigDecimal.ZERO;
	
	@Column(name = "usage_quantity")
	private BigDecimal usageQuantity = BigDecimal.ZERO;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "material_type")
	private MaterialType materialType;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "material_source")
	private MaterialSource materialSource;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_product")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Product product;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_reference_item")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	protected WarehouseReferenceItem referenceItem;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_production_order_detail")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private ProductionOrderDetail productionOrderDetail;
	
	@Override
	public String getAuditCode() {
		return this.id+"";
	}
}
