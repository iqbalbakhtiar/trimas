/**
 * File Name  : FundApplication.java
 * Created On : Jul 12, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountpayable.dm;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
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

import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Money;
import com.siriuserp.sdk.dm.Party;

import javolution.util.FastSet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "fund_application")
public class FundApplication extends Model
{
	private static final long serialVersionUID = -926121726868351387L;

	@Column(name = "code")
	private String code;

	@Column(name = "date")
	private Date date;

	@Column(name = "note")
	private String note;

	@Embedded
	private Money money = new Money();

	@Column(name = "fund_application_status")
	@Enumerated(EnumType.STRING)
	protected FundApplicationStatus status = FundApplicationStatus.NEW;

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

	@OneToMany(mappedBy = "fundApplication", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OrderBy("id")
	private Set<FundApplicationItem> items = new FastSet<FundApplicationItem>();

	@Override
	public String getAuditCode()
	{
		return this.getId() + "," + this.getCode();
	}
}
