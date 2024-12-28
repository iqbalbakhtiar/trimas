package com.siriuserp.accounting.dm;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import com.siriuserp.sdk.dm.Model;

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
@Table(name = "chart_of_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ChartOfAccount extends Model
{
	private static final long serialVersionUID = 7601506566284602484L;

	@Column(name = "code", nullable = false, unique = true, length = 50)
	private String code;

	@Column(name = "name", nullable = false, unique = true, length = 100)
	private String name;

	@Column(name = "note", length = 255)
	private String note;

	@OneToMany(mappedBy = "coa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@OrderBy("code")
	@Where(clause = "fk_gl_parent is null")
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	private Set<GLAccount> acounts = new FastSet<GLAccount>();

	@OneToMany(mappedBy = "chartOfAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	private Set<AccountingSchema> schemas = new FastSet<AccountingSchema>();

	@Override
	public String getAuditCode()
	{
		return this.code + "," + this.name;
	}
}
