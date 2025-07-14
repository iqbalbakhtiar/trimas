package com.siriuserp.sdk.dm;

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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import com.siriuserp.production.dm.ProductionOrderDetailBarcode;

import javolution.util.FastSet;
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
@Table(name = "barcode_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BarcodeGroup extends Model
{
	private static final long serialVersionUID = -3784809459364279072L;

	@Column(name = "code")
	private String code;

	@Column(name = "date")
	private Date date;

	@Column(name = "quantity")
	private BigDecimal quantity = BigDecimal.ZERO;

	@Column(name = "note")
	private String note;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_party_organization")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party organization;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_facility")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Facility facility;

	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private BarcodeGroupType barcodeGroupType = BarcodeGroupType.PRODUCTION;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private BarcodeStatus status = BarcodeStatus.CREATED;

	@Column(name = "active")
	@Type(type = "yes_no")
	private boolean active = false;

	@OneToOne(mappedBy = "barcodeGroup", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private ProductionOrderDetailBarcode productionOrderDetailBarcode;
	
	@OneToMany(mappedBy = "barcodeGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("id ASC")
	private Set<Barcode> barcodes = new FastSet<Barcode>();

	@Override
	public String getAuditCode()
	{
		return this.id + "," + this.code;
	}
}
