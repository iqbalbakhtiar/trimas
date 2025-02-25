/**
 * Dec 19, 2007 11:58:18 AM
 * net.konsep.sirius.model
 * FixedAsset.java
 */
package com.siriuserp.accounting.dm;

import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.siriuserp.procurement.dm.PurchaseOrderApprovableBridge;
import com.siriuserp.sdk.dm.Model;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import com.siriuserp.inventory.dm.Product;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.ExchangeType;
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
@Table(name = "fixed_asset")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FixedAsset extends Model implements Postingable
{
	private static final long serialVersionUID = -4822692060473909440L;

	@Column(name = "code", nullable = false, unique = true, length = 50)
	private String code;

	@Column(name = "name", nullable = false, unique = true, length = 100)
	private String name;

	@Column(name = "note", length = 255)
	private String note;

	@Column(name = "disposed")
	@Type(type = "yes_no")
	private boolean disposed;

	@Column(name = "useful_life", nullable = true, length = 5)
	private BigDecimal usefulLife = BigDecimal.ZERO;

	@Column(name = "max_life", nullable = true, length = 5)
	private BigDecimal maxlife = BigDecimal.ZERO;

	@Column(name = "acquisition_date", nullable = false)
	private Date acquisitionDate;

	@Column(name = "salvage_value")
	private BigDecimal salvageValue;

	@Column(name = "acquisition_value")
	private BigDecimal acquisitionValue;

	@Column(name = "declining_balance_rate")
	private BigDecimal decliningBalanceRate;

	@Column(name = "dispose_amount")
	private BigDecimal disposeAmount;

	@Column(name = "dispose_date")
	private Date disposeDate;

	@Column(name = "last_deprectiation_period")
	private Long lastDeprectiation;

	@Column(name = "rate")
	private BigDecimal rate = BigDecimal.ONE;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_currency")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Currency currency;

	@Enumerated(EnumType.STRING)
	@Column(name = "exchange_type")
	private ExchangeType exchangeType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_bank_account")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private BankAccount bankAccount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_product")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_journal_entry")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private JournalEntry journalEntry;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_journal_entry_dispose")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private JournalEntry disposeJournal;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_fixedAsset_group")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private FixedAssetGroup fixedAssetGroup;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_preasset")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Preasset preasset;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_straight_line")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private StraightLineDepreciationInformation straightLine;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_declining_information")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private DecliningInformation decliningInformation;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_purchase_order_approvable_bridge")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private PurchaseOrderApprovableBridge approvable;

	@OneToMany(mappedBy = "fixedAsset", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("date ASC")
	private Set<FixedAssetDepreciationDetail> depreciations = new FastSet<FixedAssetDepreciationDetail>();

	public static final FixedAsset newInstance(String id)
	{
		if (SiriusValidator.validateParam(id))
		{
			FixedAsset asset = new FixedAsset();
			asset.setId(Long.valueOf(id));

			return asset;
		}

		return null;
	}

	@Override
	public Party getOrganization()
	{
		return this.fixedAssetGroup.getOrganization();
	}

	@Override
	public Date getDate()
	{
		return new Date();
	}

	@Override
	public String getAuditCode()
	{
		return this.id + "," + this.code;
	}
}
