/**
 * Sep 20, 2006 11:47:20 AM
 * net.konsep.sirius.model
 * Container.java
 */
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

import com.siriuserp.sdk.dm.JSONSupport;
import com.siriuserp.sdk.dm.Model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Agung Dodi Perdana          
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Entity
@Getter
@Setter
@Table(name = "cost_center_group_item")
public class CostCenterGroupItem extends Model implements JSONSupport
{
	private static final long serialVersionUID = 6859754333552997006L;

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_cost_center")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private CostCenter costCenter;

	@Column(name = "unit_cost")
	private BigDecimal unitCost = BigDecimal.ZERO;
	 
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_cost_center_group")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private CostCenterGroup costCenterGroup;

	@Override
	public String getAuditCode() {
		return id + ',' + costCenter.getCode();
	}

}
