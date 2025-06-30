/**
 * Sep 20, 2006 11:47:20 AM
 * net.konsep.sirius.model
 * Container.java
 */
package com.siriuserp.production.dm;

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

import com.siriuserp.inventory.dm.UnitOfMeasure;
import com.siriuserp.sdk.dm.JSONSupport;
import com.siriuserp.sdk.dm.Model;

import javolution.util.FastSet;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Agung Dodi Perdana          
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Entity
@Getter
@Setter
@Table(name = "cost_center_group")
public class CostCenterGroup extends Model implements JSONSupport
{
	private static final long serialVersionUID = 6859754333552997006L;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_unit_of_measure")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private UnitOfMeasure unitOfMeasure;

	@Column(name = "note")
	private String note;
	
	@OneToMany(mappedBy = "costCenterGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Fetch(FetchMode.SELECT)
    @Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
    @OrderBy("id")
    private Set<CostCenterGroupItem> items = new FastSet<CostCenterGroupItem>();
	
	@OneToMany(mappedBy = "costCenterGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Fetch(FetchMode.SELECT)
    @Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	private Set<ProductionCostCenterGroup> productionCostCenterGroups = new FastSet<ProductionCostCenterGroup>();

	@Override
	public String getAuditCode() {
		return id + ',' + code;
	}

}
