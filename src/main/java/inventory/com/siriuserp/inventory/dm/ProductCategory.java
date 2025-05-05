package com.siriuserp.inventory.dm;

import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import com.siriuserp.sdk.dm.Model;

import javolution.util.FastMap;
import javolution.util.FastSet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "product_category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductCategory extends Model
{
	private static final long serialVersionUID = 4602039144012340889L;

	@Column(name = "code", unique = true)
	private String code;

	@Column(name = "name", unique = true)
	private String name;

	@Column(name = "note")
	private String note;

	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private ProductCategoryType type;

	@Enumerated(EnumType.STRING)
	@Column(name = "category_type")
	private CategoryType categoryType = CategoryType.MATERIAL;

	@OneToMany(mappedBy = "productCategory", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	private Set<Product> products = new FastSet<Product>();

	public boolean isDelete()
	{
		return this.products == null || this.products.isEmpty();
	}

	@Override
	public Map<String, Object> val()
	{
		Map<String, Object> map = new FastMap<String, Object>();
		map.put("id", getId());
		map.put("name", getName());

		return map;
	}

	@Override
	public String getAuditCode()
	{
		return this.id + "," + this.name;
	}
}
