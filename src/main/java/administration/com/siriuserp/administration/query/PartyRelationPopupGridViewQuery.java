/**
 * Apr 14, 2009 3:23:56 PM
 * com.siriuserp.popup.controller
 * CompanyStructureStandardGridViewQuery.java
 */
package com.siriuserp.administration.query;

import org.hibernate.Query;

import com.siriuserp.administration.criteria.PartyFilterCriteria;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Rama Almer Felix
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class PartyRelationPopupGridViewQuery extends AbstractGridViewQuery
{
    @Override
    public Query getQuery(ExecutorType executorType) {
        PartyFilterCriteria criteria = (PartyFilterCriteria) getFilterCriteria();

        StringBuilder builder = new StringBuilder();

        if (executorType.equals(ExecutorType.COUNT))
            builder.append("SELECT COUNT(DISTINCT relationship) ");
        else
            builder.append("SELECT DISTINCT(relationship) ");

        builder.append("FROM PartyRelationship relationship ");
        builder.append("WHERE 1=1 ");

        if (SiriusValidator.validateParamWithZeroPosibility(criteria.getFromRoleType()))
            builder.append("AND relationship.partyRoleTypeFrom.id =:fromRoleType ");
        if (SiriusValidator.validateParamWithZeroPosibility(criteria.getToRoleType()))
            builder.append("AND relationship.partyRoleTypeTo.id =:toRoleType ");
        if (SiriusValidator.validateParamWithZeroPosibility(criteria.getRelationshipType()))
            builder.append("AND relationship.relationshipType.id =:relationshipType ");
        if (SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
            builder.append("AND relationship.partyTo.id =:organization ");
        if (SiriusValidator.validateParamWithZeroPosibility(criteria.getName())) {
            builder.append("AND (relationship.partyFrom.code LIKE :name ");
            builder.append("OR relationship.partyFrom.fullName LIKE :name) ");
        }
        if (criteria.getBase() != null)
            builder.append("AND relationship.partyFrom.base = :base ");
        if (SiriusValidator.validateParamWithZeroPosibility(criteria.getExcept()))
            builder.append("AND relationship.partyFrom.id !=:except ");

        builder.append("ORDER BY relationship.id DESC ");

        Query query = getSession().createQuery(builder.toString());
        query.setReadOnly(true);
        if (SiriusValidator.validateParamWithZeroPosibility(criteria.getFromRoleType()))
            query.setParameter("fromRoleType", criteria.getFromRoleType());
        if (SiriusValidator.validateParamWithZeroPosibility(criteria.getToRoleType()))
            query.setParameter("toRoleType", criteria.getToRoleType());
        if (SiriusValidator.validateParamWithZeroPosibility(criteria.getRelationshipType()))
            query.setParameter("relationshipType", criteria.getRelationshipType());
        if (SiriusValidator.validateParamWithZeroPosibility(criteria.getOrganization()))
            query.setParameter("organization", criteria.getOrganization());
        if (SiriusValidator.validateParamWithZeroPosibility(criteria.getName()))
            query.setParameter("name", "%" + criteria.getName() + "%");
        if (criteria.getBase() != null)
            query.setParameter("base", criteria.getBase());
        if (SiriusValidator.validateParamWithZeroPosibility(criteria.getExcept()))
            query.setParameter("except", criteria.getExcept());

        return query;
    }
}
