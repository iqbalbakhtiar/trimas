package com.siriuserp.sdk.dm;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import javolution.util.FastSet;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="approval_decision")
public class ApprovalDecision extends Model {

	private static final long serialVersionUID = 3105683189453698174L;
	
	@Column(name="remark",length=255)
    private String remark;
	
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_party_forward_to")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Party forwardTo;
	
	@Enumerated(EnumType.STRING)
    @Column(name="decision_status")
    private ApprovalDecisionStatus approvalDecisionStatus = ApprovalDecisionStatus.REQUESTED;
	
	@OneToMany(mappedBy = "approvalDecision", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("id")
	private Set<ApprovalHistory> histories = new FastSet<ApprovalHistory>();

	@Override
	public String getAuditCode() {
		return id + "" + remark;
	}

}
