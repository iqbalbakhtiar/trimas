package com.siriuserp.procurement.query;

import org.hibernate.Query;

import com.siriuserp.procurement.criteria.DirectPurchaseOrderFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.dm.ApprovalDecisionStatus;
import com.siriuserp.sdk.utility.SiriusValidator;

public class DirectPurchaseOrderGridViewQuery extends AbstractGridViewQuery {
    @Override
    public Query getQuery(ExecutorType executorType) {
        DirectPurchaseOrderFilterCriteria criteria = (DirectPurchaseOrderFilterCriteria) getFilterCriteria();

        StringBuilder builder = new StringBuilder();

        if (executorType.equals(ExecutorType.COUNT))
            builder.append("SELECT COUNT(DISTINCT po) ");
        else
            builder.append("SELECT DISTINCT(po) ");

        builder.append("FROM PurchaseOrder po WHERE 1=1 ");

        if (SiriusValidator.validateParam(criteria.getCode()))
            builder.append("AND po.code LIKE :code");

        if (SiriusValidator.validateDate(criteria.getDateFrom()))
        {
            if (SiriusValidator.validateDate(criteria.getDateTo()))
                builder.append("AND po.date BETWEEN :startDate AND :endDate ");
            else
                builder.append("AND po.date >= :startDate ");
        } else if (SiriusValidator.validateDate(criteria.getDateTo()))
            builder.append("AND po.date <= :endDate ");

        if (SiriusValidator.validateParam(criteria.getSupplier()))
            builder.append("AND po.supplier.fullName LIKE :supplier ");

        if (SiriusValidator.validateParam(criteria.getTax()))
            builder.append("AND po.tax.taxName LIKE :tax ");

        if (SiriusValidator.validateParam(criteria.getApprovalDecisionStatus()))
            builder.append("AND po.approvable.approvalDecision.approvalDecisionStatus =:approvalDecisionStatus ");

        if (SiriusValidator.validateParam(criteria.getApprover()))
            builder.append("AND po.approver.fullName LIKE :approver ");

        if (SiriusValidator.validateParam(criteria.getBillToAddress()))
            builder.append("AND po.billTo.addressName LIKE :billToAddress ");

        if (SiriusValidator.validateParam(criteria.getShipToFacility()))
            builder.append("AND po.shipTo.name LIKE :shipToFacility ");

        builder.append(" ORDER BY po.id DESC ");

        Query query = getSession().createQuery(builder.toString());
        query.setReadOnly(true);

        // SET QUERY PARAM
        if (SiriusValidator.validateParam(criteria.getCode()))
            query.setParameter("code", "%" + criteria.getCode() + "%");

        if (SiriusValidator.validateDate(criteria.getDateFrom()))
            query.setParameter("startDate", criteria.getDateFrom());

        if (SiriusValidator.validateDate(criteria.getDateTo()))
            query.setParameter("endDate", criteria.getDateTo());

        if (SiriusValidator.validateParam(criteria.getSupplier()))
            query.setParameter("supplier", "%" + criteria.getSupplier() + "%");

        if (SiriusValidator.validateParam(criteria.getTax()))
            query.setParameter("tax", "%" + criteria.getTax() + "%");

        if (SiriusValidator.validateParam(criteria.getApprovalDecisionStatus()))
            query.setParameter("approvalDecisionStatus", ApprovalDecisionStatus.valueOf(criteria.getApprovalDecisionStatus()));

        if (SiriusValidator.validateParam(criteria.getApprover()))
            query.setParameter("approver", "%" + criteria.getApprover() + "%");

        if (SiriusValidator.validateParam(criteria.getBillToAddress()))
            query.setParameter("billToAddress", "%" + criteria.getBillToAddress() + "%");

        if (SiriusValidator.validateParam(criteria.getShipToFacility()))
            query.setParameter("shipToFacility", "%" + criteria.getShipToFacility() + "%");

        return query;
    }
}
