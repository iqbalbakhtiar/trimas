package com.siriuserp.production.dm;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

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
@Table(name = "production_cost_center_group")
public class ProductionCostCenterGroup extends Model 
{
	private static final long serialVersionUID = -1680097186095693716L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_cost_center_group")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private CostCenterGroup costCenterGroup;
	
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
