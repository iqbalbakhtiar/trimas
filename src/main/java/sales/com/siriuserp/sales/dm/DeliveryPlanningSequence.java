/**
 * File Name  : DeliveryPlanningSequence.java
 * Created On : Mar 6, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.dm;

import java.util.Date;
import java.util.HashSet;
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

import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.PostalAddress;
import com.siriuserp.sdk.dm.Tax;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Getter
@Setter
@Entity
@Table(name = "delivery_planning_sequence")
public class DeliveryPlanningSequence extends Model
{
	private static final long serialVersionUID = 3216421037060073044L;

	@Column(name = "no")
	private Long no;

	@Column(name = "date")
	private Date date;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_tax")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Tax tax;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_facility")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Facility facility;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_postal_address")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private PostalAddress postalAddress;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_delivery_planning")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private DeliveryPlanning deliveryPlanning;

	@OneToMany(mappedBy = "deliveryPlanningSequence", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@OrderBy("salesOrderItem")
	private Set<DeliveryPlanningSequenceItem> sequenceItems = new HashSet<DeliveryPlanningSequenceItem>();

	public Set<DeliveryOrder> getDeliveryOrders()
	{
		Set<DeliveryOrder> deliveryOrders = new HashSet<DeliveryOrder>();

		for (DeliveryPlanningSequenceItem item : getSequenceItems())
		{
			if (item.getDeliveryOrderReferenceItem() != null && item.getDeliveryOrderReferenceItem().getDeliveryOrderItem() != null)
				deliveryOrders.add(item.getDeliveryOrderReferenceItem().getDeliveryOrderItem().getDeliveryOrder());
		}

		return deliveryOrders;
	}

	@Override
	public String getAuditCode()
	{
		return this.getId().toString();
	}
}
