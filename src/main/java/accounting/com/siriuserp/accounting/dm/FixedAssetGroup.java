/**
 * Dec 19, 2007 11:34:23 AM
 * net.konsep.sirius.model
 * FixetAssetGroup.java
 */
package com.siriuserp.accounting.dm;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastSet;
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
@Table(name = "fixed_asset_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FixedAssetGroup extends Model
{
	private static final long serialVersionUID = 2797517081205164039L;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "note")
	private String note;

	@Column(name = "depreciate_asset_immediately")
	private Boolean depreciateAssetImmediately;

	@Column(name = "useful_life", nullable = true, length = 5)
	private BigDecimal usefulLife;

	@Column(name = "depreciate_asset_before_date", nullable = true, length = 5)
	private BigDecimal depreciateAssetDate;

	@Column(name = "declining_balance_rate", nullable = true)
	private BigDecimal decliningBalanceRate;

	@Column(name = "depreciation_method", nullable = false)
	@Enumerated(EnumType.STRING)
	private DepreciationMethod depreciationMethod;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_organization")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party organization;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_accounting_schema")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private AccountingSchema accountingSchema;

	@OneToMany(mappedBy = "fixedAssetGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	private Set<FixedAsset> assets = new FastSet<FixedAsset>();

	@OneToMany(mappedBy = "fixedAssetGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	private Set<FixedAssetClosingInformation> closingInformations = new FastSet<FixedAssetClosingInformation>();

	@Override
	public String getAuditCode()
	{
		return this.id + "," + this.name;
	}

	public static final FixedAssetGroup newInstance(String id)
	{
		if (SiriusValidator.validateParamWithZeroPosibility(id))
		{
			FixedAssetGroup fixedAssetGroup = new FixedAssetGroup();
			fixedAssetGroup.setId(Long.valueOf(id));

			return fixedAssetGroup;
		}

		return null;
	}
}
