package com.siriuserp.production.dm;

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
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import com.siriuserp.sdk.dm.Model;

import javolution.util.FastSet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ferdinand
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "production_order_detail")
public class ProductionOrderDetail extends Model 
{
	private static final long serialVersionUID = 6339035064313033812L;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "date")
	private Date date;
	
	@Column(name = "finish_date")
	private Date finishDate;
	
	@Column(name = "note")
	private String note;
	
	@Column(name = "description")
	private String description;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private ProductionOrderStatus status = ProductionOrderStatus.ON_GOING;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_production_order")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private ProductionOrder productionOrder;
	
	@OneToMany(mappedBy = "productionOrderDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("id ASC")
	private Set<ProductionOrderDetailItem> items = new FastSet<ProductionOrderDetailItem>();
	
	@OneToMany(mappedBy = "productionOrderDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("id ASC")
	private Set<ProductionDetailCostCenterGroup> productionDetailCostCenterGroups = new FastSet<ProductionDetailCostCenterGroup>();
	
	@OneToMany(mappedBy = "productionOrderDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("id ASC")
	private Set<WorkIssue> workIssues = new FastSet<WorkIssue>();
	
	@OneToMany(mappedBy = "productionOrderDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("id ASC")
	private Set<ProductionOrderDetailMaterialRequest> productionOrderDetailMaterialRequests = new FastSet<ProductionOrderDetailMaterialRequest>();
	
	@OneToMany(mappedBy = "productionOrderDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("id ASC")
	private Set<ProductionOrderDetailBarcode> barcodes = new FastSet<ProductionOrderDetailBarcode>();

	@Override
	public String getAuditCode() {
		return this.id+","+this.code;
	}
}
