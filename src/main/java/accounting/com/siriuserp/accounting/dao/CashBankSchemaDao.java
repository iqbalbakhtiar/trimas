/**
 * 
 */
package com.siriuserp.accounting.dao;

import com.siriuserp.accounting.dm.CashBankSchema;
import com.siriuserp.sdk.base.Dao;

/**
 * @author
 * Betsu Brahmana Restu
 * PT. Sirius Indonesia
 * betsu@siriuserp.com
 */

public interface CashBankSchemaDao extends Dao<CashBankSchema>
{
	CashBankSchema getCashBankByTypeAndOrganization(Long type, Long organization);
}
