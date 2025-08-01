/**
 * File Name  : WorkOrder.java
 * Created On : Jul 30, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.production.dm;

import java.util.Date;
import java.util.HashSet;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import com.siriuserp.inventory.dm.GoodsIssue;
import com.siriuserp.inventory.dm.GoodsReceipt;
import com.siriuserp.inventory.dm.Warehouseable;
import com.siriuserp.sales.dm.ApprovableType;
import com.siriuserp.sdk.dm.ApprovableBridge;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.Tax;

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
@Table(name = "work_order")
public class WorkOrder extends Model implements Warehouseable, ApprovableBridge
{
	private static final long serialVersionUID = 4083537968297497975L;

	@Column(name = "code")
	private String code;

	@Column(name = "date")
	private Date date;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "finish_date")
	private Date finishDate;

	@Column(name = "work_start")
	private String workStart;

	@Column(name = "work_end")
	private String workEnd;

	@Column(name = "note")
	private String note;

	@Column(name = "production_status")
	@Enumerated(EnumType.STRING)
	private ProductionStatus productionStatus = ProductionStatus.OPEN;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_party_organization")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party organization;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_facility")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Facility facility;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_party_operator")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party operator;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_party_approver")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party approver;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_work_order_approvable_bridge")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private WorkOrderApprovableBridge approvable;

	@OneToMany(mappedBy = "workOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	private Set<WorkOrderItem> items = new FastSet<WorkOrderItem>();

	@OneToMany(mappedBy = "workOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@Where(clause = "conversion_type = 'CONVERT'")
	private Set<WorkOrderItem> convertItems = new FastSet<WorkOrderItem>();

	@OneToMany(mappedBy = "workOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@Where(clause = "conversion_type = 'RESULT'")
	private Set<WorkOrderItem> resultItems = new FastSet<WorkOrderItem>();

	@Override
	public ApprovableType getApprovableType()
	{
		return ApprovableType.WORK_ORDER;
	}

	@Override
	public Set<String> getInterceptorNames()
	{
		Set<String> interceptors = new FastSet<String>();
		interceptors.add("workOrderApprovableInterceptor");

		return interceptors;
	}

	@Override
	public String getUri()
	{
		return "workorderpreedit.htm";
	}

	@Override
	public Set<WorkOrderItem> getIssueables()
	{
		return getConvertItems();
	}

	@Override
	public Set<WorkOrderItem> getReceiptables()
	{
		return getResultItems();
	}

	@Override
	public Set<GoodsIssue> getIssueds()
	{
		HashSet<GoodsIssue> issueds = new HashSet<GoodsIssue>();

		for (WorkOrderItem item : getConvertItems())
			issueds.addAll(item.getIssueds());

		return issueds;
	}

	@Override
	public Set<GoodsReceipt> getReceipts()
	{
		HashSet<GoodsReceipt> receipts = new HashSet<GoodsReceipt>();

		for (WorkOrderItem item : getResultItems())
			receipts.addAll(item.getReceipts());

		return receipts;
	}

	@Override
	public Party getParty()
	{
		return getOrganization();
	}

	@Override
	public Tax getTax()
	{
		return Tax.newInstance("2", "Exempt");
	}

	@Override
	public Currency getCurrency()
	{
		return Currency.newInstance("1");
	}

	@Override
	public String getRef()
	{
		return getOperator().getFullName();
	}

	public boolean isDeleteable()
	{
		if (getProductionStatus().equals(ProductionStatus.OPEN))
			return true;

		return false;
	}

	@Override
	public String getAuditCode()
	{
		return this.getId() + "," + this.getCode();
	}
}
