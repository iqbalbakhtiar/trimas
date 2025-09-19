package com.siriuserp.accountreceivable.dm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.siriuserp.sdk.dm.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "billing_type")
public class BillingType extends Model
{
	private static final long serialVersionUID = 1725317245148646705L;

	public static final Long DELIVERY_ORDER_REALIZATION = Long.valueOf(1);
	public static final Long MANUAL = Long.valueOf(2);
	public static final Long SALES_ORDER = Long.valueOf(3);

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "url")
	private String url;

	@Override
	public String getAuditCode()
	{
		return id + "," + code;
	}
}
