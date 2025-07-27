/**
 * Apr 28, 2009 11:22:28 AM
 * com.siriuserp.sdk.dm
 * Price.java
 */
package com.siriuserp.inventory.dm;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
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

import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Money;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Getter
@Setter
@Entity
@Table(name = "product_standard_price")
public class ProductStandardPrice extends Model
{
	private static final long serialVersionUID = -5638986143659871786L;

	@Embedded
	private Money money = new Money();

	@Column(name = "discount", scale = 2)
	private BigDecimal discount = BigDecimal.ZERO;

	@Column(name = "date_from")
	private Date dateFrom;

	@Column(name = "date_to")
	private Date dateTo;

	@Enumerated(EnumType.STRING)
	@Column(name = "product_standard_price_type")
	private ProductStandardPriceType priceType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_product")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Product product;

	@Override
	public String getAuditCode()
	{
		return this.id + ",";
	}
}
