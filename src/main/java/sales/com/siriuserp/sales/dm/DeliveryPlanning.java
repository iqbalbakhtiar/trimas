/**
 * File Name  : DeliveryPlanning.java
 * Created On : Mar 6, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.dm;

import java.util.Date;
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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Party;

import javolution.util.FastSet;
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
@Table(name = "delivery_planning")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DeliveryPlanning extends Model
{
	private static final long serialVersionUID = -3586240240745724687L;

	@Column(name = "code")
	private String code;

	@Column(name = "date")
	private Date date;

	@Column(name = "sequence_counter")
	private int sequenceCounter;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_sales_order")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private SalesOrder salesOrder;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_person_approver")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party approver;

	@OneToMany(mappedBy = "deliveryPlanning", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("id")
	private Set<DeliveryPlanningSequence> sequences = new FastSet<DeliveryPlanningSequence>();

	@Override
	public String getAuditCode()
	{
		return this.id + "," + this.code;
	}
}
