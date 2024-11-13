package com.siriuserp.tools.dao.impl;

import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.dm.Approvable;
import com.siriuserp.sdk.dm.ApprovalDecisionStatus;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.exceptions.DataAdditionException;
import com.siriuserp.sdk.exceptions.DataDeletionException;
import com.siriuserp.sdk.exceptions.DataEditException;
import com.siriuserp.tools.dao.ApprovableDao;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

@Component
public class ApprovableDaoImpl extends DaoHelper<Approvable> implements ApprovableDao {

    @Override
    public Long getTotalRequisition(Party party) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT COUNT(app) FROM ApprovalDecision app ");
        builder.append("WHERE app.forwardTo.id =:party ");
        builder.append("AND app.approvalDecisionStatus !=:finish AND app.approvalDecisionStatus !=:reject ");

        builder.append("ORDER BY app.id ASC");

        Query query = getSession().createQuery(builder.toString());
        query.setCacheable(true);
        query.setReadOnly(true);
        query.setParameter("party", party.getId());
        query.setParameter("finish", ApprovalDecisionStatus.APPROVE_AND_FINISH);
        query.setParameter("reject", ApprovalDecisionStatus.REJECTED);

        Object object = query.uniqueResult();

        if(object != null)
            return (Long)object;

        return 0L;
    }

    @Override
    public void add(Approvable bean) throws DataAdditionException {
        getSession().save(bean);
    }

    @Override
    public void update(Approvable bean) throws DataEditException {
        getSession().update(bean);
    }

    @Override
    public void delete(Approvable bean) throws DataDeletionException {
        getSession().delete(bean);
    }

    @Override
    public void merge(Approvable bean) throws DataEditException {
        getSession().merge(bean);
    }
}
