/**
 * Mar 24, 2009 11:10:46 AM
 * com.siriuserp.sdk.dm
 * FixedAssetDepreciationDetail.java
 */
package com.siriuserp.accounting.dm;

import java.math.BigDecimal;
import java.util.Date;

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

import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Month;

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
@Table(name = "fixed_asset_depreciation_detail")
public class FixedAssetDepreciationDetail extends Model
{
	private static final long serialVersionUID = 7151761199902509197L;

	@Column(name = "date")
	private Date date;

	@Column(name = "amount")
	private BigDecimal amount;

	@Enumerated(EnumType.STRING)
	@Column(name = "month")
	private Month month;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_fixed_asset")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private FixedAsset fixedAsset;

	@Override
	public String getAuditCode()
	{
		return this.id + ",";
	}
}
