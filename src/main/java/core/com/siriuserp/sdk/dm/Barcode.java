package com.siriuserp.sdk.dm;

import java.math.BigDecimal;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.siriuserp.inventory.dm.Product;

import javolution.util.FastMap;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Ferdinand
 * @author Rama Almer Felix
 *  Sirius Indonesia, PT
 *  www.siriuserp.com
 */

@Getter
@Setter
@Entity
@Table(name = "barcode")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Barcode extends Model implements JSONSupport
{
	private static final long serialVersionUID = 433030378080324431L;

	@Column(name = "code")
	private String code;

	@Column(name = "lot_code")
	private String lotCode;
	
	@Column(name = "cone_mark")
	private String coneMark;

	@Column(name = "quantity")
	private BigDecimal quantity = BigDecimal.ZERO;

	@Column(name = "quantity_real")
	private BigDecimal quantityReal = BigDecimal.ZERO;
	
	@Column(name = "quantity_cone")
	private BigDecimal quantityCone = BigDecimal.ZERO;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_barcode_group")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private BarcodeGroup barcodeGroup;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_product")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Product product;

	@Transient
	private Long referenceId;

	@Override
	public Map<String, Object> val()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("id", getId());
		map.put("code", getCode());
		map.put("quantity", getQuantity());
		map.put("uomSymbol", getProduct().getUnitOfMeasure().getMeasureId());

		return map;
	}

	@Override
	public String getAuditCode()
	{
		return this.id + "," + this.code;
	}
}
