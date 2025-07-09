/**
 * File Name  : ReceiptManualType.java
 * Created On : Dec 5, 2023
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountreceivable.dm;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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

import com.siriuserp.accounting.dm.GLAccount;
import com.siriuserp.sdk.dm.Model;

import javolution.util.FastSet;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Getter
@Setter
@Entity
@Table(name = "receipt_manual_type")
public class ReceiptManualType extends Model
{
	private static final long serialVersionUID = 7115122097202085453L;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "enabled", length = 1)
	@Type(type = "yes_no")
	private boolean enabled = true;

	@Column(name = "note", length = 255)
	private String note;

	@Enumerated(EnumType.STRING)
	@Column(name = "reference_type")
	private ReceiptManualReferenceType referenceType = ReceiptManualReferenceType.GENERAL;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_gl_account")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private GLAccount account;

	@OneToMany(mappedBy = "receiptManualType", fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@Fetch(FetchMode.SELECT)
	@Type(type = "com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
	private Set<ReceiptManual> receiptManuals = new FastSet<ReceiptManual>();

	public boolean isDeleteable()
	{
		if (!getReceiptManuals().isEmpty())
			return false;

		return true;
	}

	@Override
	public String getAuditCode()
	{
		return this.getId().toString();
	}
}
