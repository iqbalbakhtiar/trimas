package com.siriuserp.production.dm;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.siriuserp.inventory.dm.UnitOfMeasure;
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
@Table(name = "production_detail_cost_center_group")
public class ProductionDetailCostCenterGroup extends Model 
{
	private static final long serialVersionUID = -1680097186095693716L;

	@Column(name = "work_allocation")
	private BigDecimal workAllocation = BigDecimal.ZERO;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_unit_of_measure")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private UnitOfMeasure unitOfMeasure;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_cost_center_group")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private CostCenterGroup costCenterGroup;
	
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
