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
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="payment_method")
public class PaymentMethod extends Model implements JSONSupport {
	
	private static final long serialVersionUID = 7505071622844744361L;
	
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_party")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Party party;
    
    @Column(name="payment_type")
    @Enumerated(EnumType.STRING)
    private PaymentMethodType paymentType = PaymentMethodType.CASH;
    
    @Column(name="payment_collecting_type")
    @Enumerated(EnumType.STRING)
    private PaymentCollectingType paymentCollectingType = PaymentCollectingType.DIRECT_TRANSFER;
    
	@OneToMany(mappedBy = "paymentMethod", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("id")
    private Set<PaymentSchedule> paymentSchedules = new FastSet<PaymentSchedule>();

	@Override
	public String getAuditCode() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
