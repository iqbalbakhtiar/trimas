package com.siriuserp.sales.dm;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.siriuserp.sdk.dm.SalesReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "sales_order_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SalesOrderItem extends SalesReference {
	
	private static final long serialVersionUID = -2172781865197634218L;
	
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_sales_order")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private SalesOrder salesOrder;

	@Override
	public String getAuditCode() {
		return getId() + "," + getReferenceCode();
	}

}
