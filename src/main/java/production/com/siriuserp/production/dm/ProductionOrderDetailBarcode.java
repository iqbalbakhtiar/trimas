package com.siriuserp.production.dm;

import java.math.BigDecimal;
import java.util.Date;

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

import com.siriuserp.inventory.dm.Product;
import com.siriuserp.sdk.dm.BarcodeGroup;
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
@Table(name = "production_order_detail_barcode")
public class ProductionOrderDetailBarcode extends Model 
{
	private static final long serialVersionUID = 3738157444969813593L;

	@Column(name = "code")
	private String code;
	
	@Column(name = "date")
	private Date date;
	
	@Column(name = "note")
	private String note;
	
	@Column(name = "cone_mark")
	private String coneMark;
	
	@Column(name = "pic")
	private String pic;
	
	@Column(name = "quantity")
	private BigDecimal quantity = BigDecimal.ZERO;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_product")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Product product;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_barcode_group")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private BarcodeGroup barcodeGroup;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_production_order_detail")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private ProductionOrderDetail productionOrderDetail;
	
	@Override
	public String getAuditCode() {
		return id + ',' + code;
	}
}
