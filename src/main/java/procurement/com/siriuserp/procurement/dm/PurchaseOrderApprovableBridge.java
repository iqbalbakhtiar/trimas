package com.siriuserp.procurement.dm;

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
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "purchase_order_approvable_bridge")
public class PurchaseOrderApprovableBridge extends Approvable {
    private static final long serialVersionUID = -4271647961828286034L;

    @OneToOne(mappedBy = "approvable", fetch = FetchType.LAZY)
//	@JoinColumn(name="fk_sales_order")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private PurchaseOrder purchaseOrder;

    @Override
	public Long getNormalizedID() {

		return getPurchaseOrder().getId();
	}

	@Override
	public String getReviewID() {

		return String.valueOf(getPurchaseOrder().getId());
	}
}
