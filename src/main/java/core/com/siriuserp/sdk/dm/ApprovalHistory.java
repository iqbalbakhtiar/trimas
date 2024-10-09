package com.siriuserp.sdk.dm;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="approval_history")
public class ApprovalHistory extends Model {
	
	private static final long serialVersionUID = 1622318820325995981L;
	
	@Column(name="remark")
    private String remark;
	
	@Column(name="sequence")
    private Integer sequence = Integer.valueOf(0);
	
	@Column(name="date_time")
    private Timestamp dateTime;
	
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_party_approver")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Party approver;
	
	@Enumerated(EnumType.STRING)
    @Column(name="decision_status")
    private ApprovalDecisionStatus approvalDecisionStatus = ApprovalDecisionStatus.REQUESTED;
	
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_approval_decision")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private ApprovalDecision approvalDecision;

	@Override
	public String getAuditCode() {
		return id + "," + remark;
	}

}
