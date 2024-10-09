package com.siriuserp.sdk.dm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonValue;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.siriuserp.sales.dm.ApprovableType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "planable")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Approvable extends Model implements JSONSupport, Siblingable {

	private static final long serialVersionUID = -2213268727549776837L;
	
	@Column(name="code")
    protected String code;

    @Column(name="date")
    protected Date date;

    @Column(name="uri")
    protected String uri;
    
    @Enumerated(EnumType.STRING)
    @Column(name="approvable_type")
    protected ApprovableType approvableType;
    
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_party_organization")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    protected Party organization;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_facility")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	protected Facility facility;
	
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_party_approver")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    protected Party approver;
	
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_approval_decision")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    protected ApprovalDecision approvalDecision;

	@Override
	public String getAuditCode() {
		return id + "," + code;
	}
	
	public Long getNormalizedID()
    {
        return getId();
    }
    
    public String getReviewID()
    {
    	return String.valueOf(getId());
    }

	public String getSubmit()
    {
		int idx = getUri().length();
		
		StringBuilder builder = new StringBuilder();
		builder.append(getUri().substring(0, idx-11));
		builder.append(getUri().substring(getUri().length()-11, getUri().length()).replace("pre", ""));
		
		return builder.toString();
    }
	
	@JsonValue
	public Map<String, Object> val()
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", getId());
		map.put("code", getCode());
		
		return map;
	}
}
