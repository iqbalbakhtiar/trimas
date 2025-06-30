package com.siriuserp.production.dm;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.siriuserp.sdk.dm.Model;

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
@Table(name = "work_issue")
public class WorkIssue extends Model 
{
	private static final long serialVersionUID = -5368982980875527679L;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "issue")
	private String issue;
	
	@Column(name = "solution")
	private String solution;
	
	@Column(name = "reported_date")
	private Date reportedDate;
	
	@Column(name = "resolved_date")
	private Date resolvedDate;
	
	@Column(name = "reported_time")
	private String reportedTime;
	
	@Column(name = "resolved_time")
	private String resolvedTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_production_order_detail")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private ProductionOrderDetail productionOrderDetail;
	
	@Override
	public String getAuditCode() {
		return this.id+","+this.code;
	}
}
