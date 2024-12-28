/**
 * Sep 8, 2009 11:00:42 AM
 * com.siriuserp.accounting.query
 * ChartOfAccountPopupGridViewQuery.java
 */
package com.siriuserp.accounting.query;

import com.siriuserp.sdk.db.AbstractGridViewQuery;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class ChartOfAccountPopupGridViewQuery extends AbstractGridViewQuery
{

    @Override
    public Long count()
    {
        return Long.valueOf(0);
    }

    @Override
    public Object execute()
    {
        return getSession().createQuery("FROM ChartOfAccount").list();
    }

}
