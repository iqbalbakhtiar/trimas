/**
 * Nov 12, 2008 10:14:42 AM
 * com.siriuserp.sdk.dm
 * JournalEntryIndex.java
 */
package com.siriuserp.accounting.dm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.utility.SiriusValidator;

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
@Table(name = "index_type")
public class IndexType extends Model
{
	private static final long serialVersionUID = -8455988963653632466L;

	public static final Long CUSTOMER = Long.valueOf(1);
	public static final Long SUPPLIER = Long.valueOf(2);
	public static final Long CUSTOMER_SERVICE = Long.valueOf(3);
	public static final Long SALES = Long.valueOf(4);
	public static final Long PROJECT = Long.valueOf(7);
	public static final Long VOUCHER = Long.valueOf(9);

	@Column(name = "name")
	private String name;

	@Column(name = "note")
	private String note;

	@Column(name = "unselectable")
	@Type(type = "yes_no")
	private boolean unselectable = true;

	public static final IndexType newInstance(String id)
	{
		if (SiriusValidator.validateParamWithZeroPosibility(id))
		{
			IndexType indexType = new IndexType();
			indexType.setId(Long.valueOf(id));

			return indexType;
		}

		return null;
	}

	@Override
	public String getAuditCode()
	{
		return this.id + "," + this.name;
	}
}
