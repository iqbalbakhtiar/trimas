package com.siriuserp.accounting.dm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.ReportType;
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
@Table(name = "gl_account_type")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class GLAccountType extends Model
{
	private static final long serialVersionUID = 6649708583929604901L;

	public static final Long ASSET = Long.valueOf(1);
	public static final Long LIABILITY = Long.valueOf(2);
	public static final Long OWNER_EQUITY = Long.valueOf(3);
	public static final Long REVENUE = Long.valueOf(4);
	public static final Long EXPENSE = Long.valueOf(5);
	public static final Long COGS = Long.valueOf(6);
	public static final Long OTHER_REVENUE = Long.valueOf(7);
	public static final Long OTHER_EXPENSE = Long.valueOf(8);

	@Column(name = "name")
	private String name;

	@Column(name = "note")
	private String note;

	@Column(name = "report_type")
	@Enumerated(EnumType.STRING)
	private ReportType reportType;

	public static final GLAccountType newInstance(String id)
	{
		if (SiriusValidator.validateParam(id))
		{
			GLAccountType accountType = new GLAccountType();
			accountType.setId(Long.valueOf(id));

			return accountType;
		}

		return null;
	}

	@Override
	public String getAuditCode()
	{
		return this.id + "," + this.name;
	}
}
