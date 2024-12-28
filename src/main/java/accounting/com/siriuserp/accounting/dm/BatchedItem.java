/**
 * Nov 12, 2008 11:56:32 AM
 * com.siriuserp.sdk.dm
 * BatchedItem.java
 */
package com.siriuserp.accounting.dm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.siriuserp.sdk.dm.Model;

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
@Table(name = "batched_item")
public class BatchedItem extends Model
{
	private static final long serialVersionUID = 7116656923743515829L;

	@Column(name = "batched_item_member")
	@Enumerated(EnumType.STRING)
	private BatchedItemMember member;

	@Column(name = "note")
	private String note;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_journal_entry_configuration")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private JournalEntryConfiguration configuration;

	@Override
	public String getAuditCode()
	{
		return this.id + "";
	}
}
