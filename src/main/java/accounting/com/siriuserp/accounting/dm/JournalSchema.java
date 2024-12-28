/**
 * Nov 12, 2008 2:25:25 PM
 * com.siriuserp.sdk.dm
 * JournalSchema.java
 */
package com.siriuserp.accounting.dm;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

import com.siriuserp.sdk.dm.Model;
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
@Table(name = "journal_schema")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class JournalSchema extends Model
{
	private static final long serialVersionUID = -1464846337852908237L;

	@Column(name = "code")
	protected String code;

	@Column(name = "name")
	protected String name;

	@Column(name = "note")
	protected String note;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_organization")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	protected Party organization;

	@OneToMany(mappedBy = "journalSchema", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	protected Set<JournalSchemaIndex> indexes = new FastSet<JournalSchemaIndex>();

	@Override
	public String getAuditCode()
	{
		return this.id + "," + this.code;
	}
}
