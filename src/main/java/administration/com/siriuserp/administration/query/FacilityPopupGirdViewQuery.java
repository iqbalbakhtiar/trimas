/**
 * May 4, 2009 3:11:54 PM
 * com.siriuserp.popup.query
 * FacilityPopupGirdViewQuery.java
 */
package com.siriuserp.administration.query;

import com.siriuserp.sales.criteria.SalesOrderFilterCriteria;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.dm.ApprovalDecisionStatus;
import org.hibernate.Query;

import com.siriuserp.administration.criteria.FacilityFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.Facility;
import com.siriuserp.sdk.dm.FacilityImplementation;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastList;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class FacilityPopupGirdViewQuery extends AbstractGridViewQuery {
    @Override
    public Query getQuery(ExecutorType executorType) {
        FacilityFilterCriteria criteria = (FacilityFilterCriteria) getFilterCriteria();

        StringBuilder builder = new StringBuilder();

        if (executorType.equals(ExecutorType.COUNT))
            builder.append("SELECT COUNT(DISTINCT facility) ");
        else
            builder.append("SELECT DISTINCT(facility) ");

        builder.append("FROM Facility facility WHERE 1=1 ");

        //WHERE CLAUSE
        if (SiriusValidator.validateLongParam(criteria.getOrganization()))
            builder.append("AND facility.owner.id =:organization ");

        builder.append(" ORDER BY facility.id DESC ");

        Query query = getSession().createQuery(builder.toString());
        query.setReadOnly(true);

        //SET QUERY PARAM
        if (SiriusValidator.validateLongParam(criteria.getOrganization()))
            query.setParameter("organization", criteria.getOrganization());

        return query;
    }
}
