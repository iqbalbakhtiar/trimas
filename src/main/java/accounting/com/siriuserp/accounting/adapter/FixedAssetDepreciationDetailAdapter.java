package com.siriuserp.accounting.adapter;

import java.math.BigDecimal;

import com.siriuserp.sdk.adapter.AbstractUIAdapter;
import com.siriuserp.sdk.dm.Month;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FixedAssetDepreciationDetailAdapter extends AbstractUIAdapter
{
	private static final long serialVersionUID = -7554399576155746977L;

	private Month month;
	private BigDecimal amount;

	public FixedAssetDepreciationDetailAdapter(Month month, BigDecimal amount)
	{
		super();
		this.month = month;
		this.amount = amount;
	}
}
