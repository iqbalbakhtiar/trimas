package com.siriuserp.accounting.dao;

import java.util.List;

import com.siriuserp.accounting.dm.AccountingSchema;
import com.siriuserp.accounting.dm.ClosingDistribution;
import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.base.Filterable;
import com.siriuserp.sdk.dm.Party;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface AccountingSchemaDao extends Dao<AccountingSchema>, Filterable
{
	public AccountingSchema load(Party organization);
	public List<ClosingDistribution> loadAllDistribution(Party organization);
	public AccountingSchema loadDistributed(Party organization);
	public Object loadCode(String code);
}
