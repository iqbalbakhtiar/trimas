package com.siriuserp.accounting.dm;

import java.util.Date;
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

import com.siriuserp.sdk.dm.Level;
import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Month;
import com.siriuserp.sdk.dm.Party;

import javolution.util.FastSet;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Getter
@Setter
@Entity
@Table(name = "accounting_period")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AccountingPeriod extends Model
{
	private static final long serialVersionUID = 415047434867522803L;

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@Column(name = "code", nullable = false, unique = true)
	private String code;

	@Column(name = "note")
	private String note;

	@Column(name = "sequence", nullable = false)
	private Long sequence;

	@Column(name = "period_year")
	private Integer year;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "period_status")
	private PeriodStatus status;

	@Enumerated(EnumType.STRING)
	@Column(name = "period_level")
	private Level level;

	@Enumerated(EnumType.STRING)
	@Column(name = "period_month")
	private Month month;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_parent")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private AccountingPeriod parent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_organization")
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@Fetch(FetchMode.SELECT)
	private Party organization;

	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@OrderBy("startDate ASC,name ASC")
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	private Set<AccountingPeriod> childs = new FastSet<AccountingPeriod>();

	@Override
	public String getAuditCode()
	{
		return this.id + "," + this.code;
	}
}
