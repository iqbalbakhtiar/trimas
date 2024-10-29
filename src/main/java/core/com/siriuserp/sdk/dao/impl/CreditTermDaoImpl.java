package com.siriuserp.sdk.dao.impl;

import com.siriuserp.sdk.dao.CreditTermDao;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.dm.CreditTerm;
import com.siriuserp.sdk.utility.DateHelper;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CreditTermDaoImpl extends DaoHelper<CreditTerm> implements CreditTermDao {
    @Override
    public CreditTerm loadByParty(Long fromparty, Long toparty, Long relation) {
        StringBuilder builder = new StringBuilder();
        builder.append("FROM CreditTerm term");
        builder.append(" WHERE term.partyRelationship.fromRole.party.id =:fromparty");
        builder.append(" AND term.partyRelationship.toRole.party.id =:toparty");
        builder.append(" AND term.partyRelationship.relationshipType.id =:relation");
        builder.append(" ORDER BY term.id DESC");

        Query query = getSession().createQuery(builder.toString());
        query.setParameter("fromparty", fromparty);
        query.setParameter("toparty", toparty);
        query.setParameter("relation", relation);
        query.setMaxResults(1);

        Object object = (Object) query.uniqueResult();
        if (object != null)
            return (CreditTerm) object;

        return null;
    }

    @Override
    public CreditTerm loadByRelationship(Long relationshipId, boolean active, Date date) {
        StringBuilder builder = new StringBuilder();
        builder.append("FROM CreditTerm term ");
        builder.append("WHERE term.partyRelationship.id =:relationshipId ");

        if (active)
        {
            builder.append("AND :today BETWEEN term.validFrom AND (CASE WHEN term.validTo IS NOT NULL THEN term.validTo ELSE :today END) ");
			/*builder.append("AND (CASE WHEN term.validTo IS NOT NULL THEN (:today BETWEEN term.validFrom AND term.validTo) ");
			builder.append("ELSE (:today >= term.validFrom) END) ");*/
        }

        builder.append("ORDER BY term.id DESC");

        Query query = getSession().createQuery(builder.toString());
        query.setMaxResults(1);
        query.setParameter("relationshipId", relationshipId);
        query.setParameter("today", (date != null) ? date : DateHelper.today());

        Object object = (Object) query.uniqueResult();
        if (object != null)
            return (CreditTerm) object;

        return null;
    }
}
