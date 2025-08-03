package com.siriuserp.inventory.dm;

import java.math.BigDecimal;
import java.util.Map;
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
import org.hibernate.annotations.Where;

import com.siriuserp.sdk.dm.Model;

import javolution.util.FastMap;
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
public class Product extends Model
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
	private boolean base = true;

	@Column(name = "enabled", length = 1)
	@Type(type = "yes_no")
	private boolean enabled = true;

	@Column(name = "serial", length = 1)
	@Type(type = "yes_no")
	private boolean serial = false;

	@Column(name = "saleable", length = 1)
	@Type(type = "yes_no")
	private boolean saleable = false;

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_brand")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Brand brand;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_product_parent")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Product parent;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@Where(clause = "product_standard_price_type = 'SELLING'")
	@OrderBy("dateFrom DESC")
	private Set<ProductStandardPrice> sellingPrices = new FastSet<ProductStandardPrice>();

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@Where(clause = "product_standard_price_type = 'BUYING'")
	@OrderBy("dateFrom DESC")
	private Set<ProductStandardPrice> buyingPrices = new FastSet<ProductStandardPrice>();

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	private Set<InventoryItem> items = new FastSet<InventoryItem>();

	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("id ASC")
	private Set<Product> variants = new FastSet<Product>();

	public boolean isDeleteable()
	{
		if (!isEnabled())
			return false;

		if (!items.isEmpty())
			return false;

		return true;
	}

	@Override
	public Map<String, Object> val()
	{
		Map<String, Object> map = new FastMap<String, Object>();
		map.put("productId", getId());
		map.put("productName", getName());
		map.put("productCode", getCode());
		map.put("barcode", getBarcode());
		map.put("isSerial", isSerial());
		map.put("parent", getParent());
		map.put("factoryCode", getFactoryCode());
		map.put("qtyToBase", getQtyToBase());
		map.put("productCategory", getProductCategory());
		map.put("unitOfMeasure", getUnitOfMeasure());

		return map;
	}

	@Override
	public String getAuditCode()
	{
		return this.id + "," + this.code;
	}
}
