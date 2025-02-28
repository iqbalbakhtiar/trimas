package com.siriuserp.sales.dm;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.siriuserp.sdk.dm.Approvable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "sales_order_approvable_bridge")
public class SalesOrderApprovableBridge extends Approvable {
	
	private static final long serialVersionUID = 2412332120016135956L;
	
	@OneToOne(mappedBy = "approvable", fetch = FetchType.LAZY)
//	@JoinColumn(name="fk_sales_order")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private SalesOrder salesOrder;
	
	@Override
	public Long getNormalizedID() {
		
		return getSalesOrder().getId();
	}
	
	@Override
	public String getReviewID() {
		
		return String.valueOf(getSalesOrder().getId());
	}

}
