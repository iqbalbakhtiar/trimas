/**
 * Aug 21, 2009 1:21:12 PM
 * com.siriuserp.sdk.dm
 * GeneralJournalSchema.java
 */
package com.siriuserp.accounting.dm;

import java.util.Set;

import javax.persistence.Entity;
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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

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
@Table(name = "general_journal_schema")
public class GeneralJournalSchema extends JournalSchema
{
	private static final long serialVersionUID = 7753486118999995853L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_chart_of_account")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	protected ChartOfAccount chartOfAccount;

	@OneToMany(mappedBy = "journalSchema", fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OrderBy("id")
	protected Set<JournalSchemaAccount> accounts = new FastSet<JournalSchemaAccount>();
}
