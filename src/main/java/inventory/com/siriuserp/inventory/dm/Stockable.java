/**
 * 
 */
package com.siriuserp.inventory.dm;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Model;

import javolution.util.FastSet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ferdinand
 */

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "stockable")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Stockable	extends Model 
{
	private static final long serialVersionUID = -4738379529901322180L;
	
	@OneToMany(mappedBy = "stockable", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Fetch(FetchMode.SELECT)
    @Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("id ASC")
	protected Set<StockControl> stockControls = new FastSet<StockControl>();
	
	@OneToMany(mappedBy = "stockable", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("id ASC")
	protected Set<InventoryControl> inventoryControls = new FastSet<InventoryControl>();
	
	public abstract WarehouseTransactionItem getWarehouseTransactionItem();
	public abstract Container getSourceContainer();
	public abstract ProductCategory getProductCategory();
	public abstract Product getProduct();
}
