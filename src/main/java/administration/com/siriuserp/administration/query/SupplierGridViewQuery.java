package com.siriuserp.administration.query;

import org.hibernate.Query;

import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.db.ExecutorType;
import com.siriuserp.sdk.dm.PartyRelationshipType;
import com.siriuserp.sdk.dm.PartyRoleType;
import com.siriuserp.sdk.filter.SupplierFilterCriteria;
import com.siriuserp.sdk.utility.SiriusValidator;

public class SupplierGridViewQuery extends AbstractGridViewQuery {
	@Override
	public Query getQuery(ExecutorType executorType) {
		SupplierFilterCriteria criteria = (SupplierFilterCriteria) getFilterCriteria();
		
		StringBuilder builder = new StringBuilder();
		
		if (executorType.equals(ExecutorType.COUNT))
			builder.append("SELECT COUNT(DISTINCT relationship) ");
		else
			builder.append("SELECT DISTINCT(relationship) ");
		
		builder.append("FROM PartyRelationship relationship ");
		builder.append("WHERE relationship.partyRoleTypeFrom.id =:fromRoleType ");
		builder.append("AND relationship.partyRoleTypeTo.id =:toRoleType ");
		builder.append("AND relationship.relationshipType.id =:relationshipType ");
		
		if (SiriusValidator.validateParam(criteria.getCode()))
			builder.append("AND relationship.partyFrom.code LIKE :code ");

		if (SiriusValidator.validateParam(criteria.getCustomer()))
			builder.append("AND relationship.partyFrom.fullName LIKE :customer ");
		
		if (SiriusValidator.validateParam(criteria.getCompany()))
			builder.append("AND relationship.partyTo.fullName LIKE :company ");
		
		if(criteria.getBase() != null)
			builder.append("AND relationship.partyFrom.base =:base");
		
		if(criteria.getActive() != null)
			builder.append("AND relationship.partyFrom.active =:active");
		
		builder.append(" ORDER BY relationship.id DESC ");
		
		Query query = getSession().createQuery(builder.toString());
		query.setReadOnly(true);
		query.setParameter("fromRoleType", PartyRoleType.SUPPLIER);
		query.setParameter("toRoleType", PartyRoleType.CUSTOMER);
		query.setParameter("relationshipType", PartyRelationshipType.SUPPLIER_RELATIONSHIP);
		
		if (SiriusValidator.validateParam(criteria.getCode()))
			query.setParameter("code", "%" + criteria.getCode() + "%");

		if (SiriusValidator.validateParam(criteria.getCustomer()))
			query.setParameter("customer", "%" + criteria.getCustomer() + "%");
		
		if (SiriusValidator.validateParam(criteria.getCompany()))
			query.setParameter("company", "%" + criteria.getCompany() + "%");
		
		if(criteria.getBase() != null)
			query.setParameter("base", criteria.getBase());
		
		if(criteria.getActive() != null)
			query.setParameter("active", criteria.getActive());

		return query;
	}
}
