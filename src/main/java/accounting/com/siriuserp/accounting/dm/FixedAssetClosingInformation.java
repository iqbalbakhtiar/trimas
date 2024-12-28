/**
 * Jan 24, 2008 4:37:32 PM
 * net.konsep.sirius.model
 * FixedAssetClosingInformation.java
 */
package com.siriuserp.accounting.dm;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.siriuserp.sdk.dm.Model;

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
@Table(name = "fixed_asset_closing_information")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FixedAssetClosingInformation extends Model implements Serializable
{
	private static final long serialVersionUID = -8928868267965357610L;

	@Column(name = "note")
	private String note;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_closing_account")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private ClosingAccount closingAccount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_fixed_asset_group")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private FixedAssetGroup fixedAssetGroup;

	@Override
	public String getAuditCode()
	{
		return this.id + "";
	}
}
