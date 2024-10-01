package com.siriuserp.sdk.dm;

import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import javolution.util.FastMap;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="credit_term")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class CreditTerm extends Model implements JSONSupport {
	
	private static final long serialVersionUID = 5301647587342591660L;

	@Column(name="term")
    private Integer term;

    @Column(name="valid_from")
    private Date validFrom;
    
    @Column(name="valid_to")
    private Date validTo;
    
    @Column(name = "active")
	@Type(type = "yes_no")
	protected boolean active = true;
    
    @Column(name = "note")
	private String note;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_party_relationship")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private PartyRelationship partyRelationship;

	@Override
	public String getAuditCode() {
		return this.id+","+this.term;
	}
	
	@Override
	public Map<String, Object> val() {
		Map<String, Object> map = new FastMap<String, Object>();

		map.put("id", getId());
		map.put("term", getTerm());
		
		return map;
	}
}
