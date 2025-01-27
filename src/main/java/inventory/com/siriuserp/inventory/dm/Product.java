package com.siriuserp.inventory.dm;

import java.math.BigDecimal;
import java.util.Set;

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

import com.siriuserp.sdk.dm.JSONSupport;
import com.siriuserp.sdk.dm.Model;

import javolution.util.FastSet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Rama Almer Felix
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product extends Model implements JSONSupport
{
	private static final long serialVersionUID = -3594353574616790831L;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "barcode")
	private String barcode;

	@Column(name = "packaging_code")
	private String packagingCode;

	@Column(name = "factory_code")
	private String factoryCode;

	@Column(name = "base", length = 1)
	@Type(type = "yes_no")
	private boolean base = false;

	@Column(name = "enabled", length = 1)
	@Type(type = "yes_no")
	private boolean enabled = true;

	@Column(name = "serial", length = 1)
	@Type(type = "yes_no")
	private boolean serial = false;

	@Column(name = "note", length = 255)
	private String note;

	@Column(name = "picture")
	private String picture;

	@Column(name = "qty_to_base")
	private BigDecimal qtyToBase = BigDecimal.ONE;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_unit_of_measure")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private UnitOfMeasure unitOfMeasure;

	@Enumerated(EnumType.STRING)
	@Column(name = "product_type")
	private ProductType productType = ProductType.GOODS;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_product_category")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private ProductCategory productCategory;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	private Set<InventoryItem> items = new FastSet<InventoryItem>();

	public boolean isDeleteable()
	{
		if (!isEnabled())
			return false;

		if (!items.isEmpty())
			return false;

		return true;
	}

	@Override
	public String getAuditCode()
	{
		return this.id + "," + this.code;
	}
}
