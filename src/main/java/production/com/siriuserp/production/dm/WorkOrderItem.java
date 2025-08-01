/**
 * File Name  : WorkOrderItem.java
 * Created On : Jul 30, 2025
 * Email	  : iqbal@siriuserp.com
 */
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

import com.siriuserp.inventory.dm.ConversionType;
import com.siriuserp.inventory.dm.WarehouseReferenceItem;
import com.siriuserp.inventory.dm.WarehouseTransaction;
import com.siriuserp.inventory.dm.WarehouseTransactionSource;
import com.siriuserp.sdk.dm.Container;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Getter
@Setter
@Entity
@Table(name = "work_order_item")
public class WorkOrderItem extends WarehouseReferenceItem
{
	private static final long serialVersionUID = 5740764911693785047L;

	@Column(name = "quantity")
	private BigDecimal quantity = BigDecimal.ZERO;

	@Enumerated(EnumType.STRING)
	@Column(name = "conversion_type")
	private ConversionType conversionType = ConversionType.CONVERT;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_container")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Container container;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_work_order")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private WorkOrder workOrder;

	@Override
	public WarehouseTransaction getWarehouseTransaction()
	{
		return getWorkOrder();
	}

	@Override
	public WarehouseTransactionSource getTransactionSource()
	{
		return WarehouseTransactionSource.WORK_ORDER;
	}

	@Override
	public String getAuditCode()
	{
		return this.getId().toString();
	}
}
