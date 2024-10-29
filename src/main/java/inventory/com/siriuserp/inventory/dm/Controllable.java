/**
 * 
 */
package com.siriuserp.inventory.dm;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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

import com.siriuserp.sdk.dm.Lot;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.utility.DecimalHelper;

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
@Table(name = "controllable")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Controllable extends Model implements Inventoriable
{
	private static final long serialVersionUID = -9070327782563600657L;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_stockable")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    protected Stockable stockable;
	
	@OneToMany(mappedBy = "controllable", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("id ASC")
	protected Set<ProductInOutTransaction> inOuts = new FastSet<ProductInOutTransaction>();
	
	public abstract BigDecimal getUnitPrice();
	
	public abstract Lot getOriginLot();
	
	public BigDecimal getCogs()
    {
    	return getInOuts().stream().map(inout -> inout.getPrice().multiply(inout.getReceipted())).collect(DecimalHelper.sum());
    }
}
