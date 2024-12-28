/**
 * Mar 5, 2009 4:33:36 PM
 * com.siriuserp.sdk.dm
 * ProductCategoryAccountingSchema.java
 */
package com.siriuserp.accounting.dm;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import com.siriuserp.inventory.dm.ProductCategory;
import com.siriuserp.sdk.dm.Model;

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
@Table(name = "product_category_accounting_schema")
public class ProductCategoryAccountingSchema extends Model
{
	private static final long serialVersionUID = -4781613659605643703L;

	@Column(name = "note")
	private String note;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_accounting_schema")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private AccountingSchema accountingSchema;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_prduct_category")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private ProductCategory category;

	@OneToMany(mappedBy = "schema", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("id asc")
	private Set<ProductCategoryClosingAccount> accounts = new FastSet<ProductCategoryClosingAccount>();

	@Override
	public String getAuditCode()
	{
		return this.id + "," + this.category.getCode();
	}
}
