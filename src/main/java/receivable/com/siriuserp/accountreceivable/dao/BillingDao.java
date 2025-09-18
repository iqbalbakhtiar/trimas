/**
 * File Name  : BillingDao.java
 * Created On : Apr 9, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountreceivable.dao;

import com.siriuserp.accountreceivable.dm.Billing;
import com.siriuserp.accountreceivable.dm.BillingReferenceType;
import com.siriuserp.sdk.base.Dao;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

public interface BillingDao extends Dao<Billing>
{
	public Billing getBillingByReference(Long referenceId, BillingReferenceType referenceType);
}
