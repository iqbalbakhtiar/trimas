/**
 * File Name  : PurchaseRequisitionItem.java
 * Created On : Feb 20, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.procurement.dm;

import java.math.BigDecimal;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import com.siriuserp.inventory.dm.Product;
import com.siriuserp.sdk.dm.Model;

import javolution.util.FastMap;
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
@Table(name = "purchase_requisition_item")
public class PurchaseRequisitionItem extends Model
{
	private static final long serialVersionUID = -4159294848407363329L;

	@Column(name = "quantity")
	private BigDecimal quantity = BigDecimal.ZERO;

	@Column(name = "unit_price")
	private BigDecimal unitPrice = BigDecimal.ZERO;

	@Column(name = "note")
	private String note;

	@Column(name = "available")
	@Type(type = "yes_no")
	private boolean available = Boolean.TRUE;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_product")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_purchase_requisition")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private PurchaseRequisition purchaseRequisition;

	@OneToOne(mappedBy = "requisitionItem", fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private PurchaseOrderItem purchaseOrderItem;

	@Override
	public Map<String, Object> val()
	{
		Map<String, Object> map = new FastMap<String, Object>();
		map.put("id", getId());
		map.put("product", getProduct());
		map.put("quantity",getQuantity());

		return map;
	}

	@Override
	public String getAuditCode()
	{
		return this.id.toString();
	}
}
