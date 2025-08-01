/**
 * File Name  : WorkOrderApprovableBridge.java
 * Created On : Jul 31, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.production.dm;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.siriuserp.sdk.dm.Approvable;

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
@Table(name = "work_order_approvable_bridge")
public class WorkOrderApprovableBridge extends Approvable
{
	private static final long serialVersionUID = -8019292381130566782L;

	@OneToOne(mappedBy = "approvable", fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private WorkOrder workOrder;

	@Override
	public Long getNormalizedID()
	{
		return getWorkOrder().getId();
	}

	@Override
	public String getReviewID()
	{
		return String.valueOf(getWorkOrder().getId());
	}
}
