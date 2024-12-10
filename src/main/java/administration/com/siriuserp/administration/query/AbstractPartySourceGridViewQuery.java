/**
 * 
 */
package com.siriuserp.administration.query;

import org.hibernate.Query;

import com.siriuserp.administration.criteria.PartyFilterCriteria;
import com.siriuserp.administration.util.RelationshipHelper;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.PartyRelationshipType;
import com.siriuserp.sdk.dm.PartyRoleType;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastList;

/**
 * @author
 * Betsu Brahmana Restu 
 * Sirius Indonesia, PT
 * betsu@siriuserp.com
 */

@SuppressWarnings("unchecked")
public abstract class AbstractPartySourceGridViewQuery extends AbstractGridViewQuery {
	protected FastList<Long> accessibleParties = new FastList<Long>();

	public FastList<Long> getAccessibleParties() {
		return accessibleParties;
	}

	public void setAccessibleParties(FastList<Long> accessibleParties) {
		this.accessibleParties = accessibleParties;
	}

	@Override
	public void init() {
		PartyFilterCriteria criteria = (PartyFilterCriteria) getFilterCriteria();

		if (SiriusValidator.validateParam(criteria.getOrganization()) && SiriusValidator.validateParam(criteria.getSource())) {
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT DISTINCT(relationship.partyFrom.id) ");
			builder.append("FROM PartyRelationship relationship ");
			builder.append("JOIN relationship.partyTo toParty ");
			builder.append("JOIN relationship.partyFrom fromParty ");
			builder.append("WHERE relationship.partyRoleTypeFrom.id = :rel0 ");
			builder.append("AND relationship.partyRoleTypeTo.id = :rel1 ");
			builder.append("AND relationship.relationshipType.id = :rel2 ");
			builder.append("AND toParty.id = :org ");

			Query query = getSession().createQuery(builder.toString());
			query.setCacheable(true);
			query.setReadOnly(true);
			query.setParameter("org", criteria.getOrganization());

			// Menggunakan RelationshipHelper untuk set parameter rel0, rel1, dan rel2 sesuai dengan source
			if (criteria.getSource().equalsIgnoreCase("CUSTOMER"))
				RelationshipHelper.build(query, PartyRoleType.CUSTOMER, PartyRoleType.SUPPLIER, PartyRelationshipType.CUSTOMER_RELATIONSHIP);
//			if (criteria.getSource().equalsIgnoreCase("SALESPERSON"))
//				RelationshipHelper.build(query, PartyRoleType.SALES_PERSON, PartyRoleType.FACILITY, PartyRelationshipType.EMPLOYMENT_RELATIONSHIP);
//			else if (criteria.getSource().equalsIgnoreCase("EXPEDITION"))
//				RelationshipHelper.build(query, PartyRoleType.EXPEDITION, PartyRoleType.FACILITY, PartyRelationshipType.EXPEDITION_RELATIONSHIP);
			else if (criteria.getSource().equalsIgnoreCase("SUPPLIER"))
				RelationshipHelper.build(query, PartyRoleType.SUPPLIER, PartyRoleType.CUSTOMER, PartyRelationshipType.SUPPLIER_RELATIONSHIP);
//			else if (criteria.getSource().equalsIgnoreCase("MEMBER"))
//				RelationshipHelper.build(query, PartyRoleType.MEMBERSHIP, PartyRoleType.SUPPLIER, PartyRelationshipType.CUSTOMER_RELATIONSHIP);
//			else if (criteria.getSource().equalsIgnoreCase("EMPLOYEE"))
//				RelationshipHelper.build(query, PartyRoleType.EMPLOYEE, PartyRoleType.FACILITY, PartyRelationshipType.EMPLOYMENT_RELATIONSHIP);
			else if (criteria.getSource().equalsIgnoreCase("SALESAPPROVER"))
				RelationshipHelper.build(query, PartyRoleType.SALES_APPROVER, PartyRoleType.COMPANY, PartyRelationshipType.EMPLOYMENT_RELATIONSHIP);
//			else if (criteria.getSource().equalsIgnoreCase("TOAPPROVER"))
//				RelationshipHelper.build(query, PartyRoleType.TRANSFER_ORDER_APPROVER, PartyRoleType.FACILITY, PartyRelationshipType.EMPLOYMENT_RELATIONSHIP);
//			else if (criteria.getSource().equalsIgnoreCase("PTOAPPROVER"))
//				RelationshipHelper.build(query, PartyRoleType.PRE_TRANSFER_ORDER_APPROVER, PartyRoleType.FACILITY, PartyRelationshipType.EMPLOYMENT_RELATIONSHIP);
//			else if (criteria.getSource().equalsIgnoreCase("PSAAPPROVER"))
//				RelationshipHelper.build(query, PartyRoleType.PRE_STOCK_ADJUSTMENT_APPROVER, PartyRoleType.FACILITY, PartyRelationshipType.EMPLOYMENT_RELATIONSHIP);
//			else if (criteria.getSource().equalsIgnoreCase("SARAPPROVER"))
//				RelationshipHelper.build(query, PartyRoleType.STOCK_ADJUSTMENT_RESPONSIBILITY_APPROVER, PartyRoleType.FACILITY, PartyRelationshipType.EMPLOYMENT_RELATIONSHIP);
			else if (criteria.getSource().equalsIgnoreCase("PURCHASEAPPROVER"))
				RelationshipHelper.build(query, PartyRoleType.PURCHASE_APPROVER, PartyRoleType.COMPANY, PartyRelationshipType.EMPLOYMENT_RELATIONSHIP);
//			else if (criteria.getSource().equalsIgnoreCase("DRIVER"))
//				RelationshipHelper.build(query, PartyRoleType.DRIVER, PartyRoleType.FACILITY, PartyRelationshipType.EMPLOYMENT_RELATIONSHIP);
//			else if (criteria.getSource().equalsIgnoreCase("PURCHASEREQUISITIONER"))
//				RelationshipHelper.build(query, PartyRoleType.PURCHASE_REQUISITIONER, PartyRoleType.FACILITY, PartyRelationshipType.EMPLOYMENT_RELATIONSHIP);
//			else if (criteria.getSource().equalsIgnoreCase("DELIVERYPLANNINGAPPROVER"))
//				RelationshipHelper.build(query, PartyRoleType.DELIVERY_PLANNING_APPROVER, PartyRoleType.FACILITY, PartyRelationshipType.EMPLOYMENT_RELATIONSHIP);
//			else if (criteria.getSource().equalsIgnoreCase("PRODUCTCONSUMPTIONREQUESTER"))
//				RelationshipHelper.build(query, PartyRoleType.PRODUCT_CONSUMPTION_REQUESTER, PartyRoleType.FACILITY, PartyRelationshipType.EMPLOYMENT_RELATIONSHIP);
//			else if (criteria.getSource().equalsIgnoreCase("PRODUCTCONSUMPTIONAPPROVER"))
//				RelationshipHelper.build(query, PartyRoleType.PRODUCT_CONSUMPTION_APPROVER, PartyRoleType.FACILITY, PartyRelationshipType.EMPLOYMENT_RELATIONSHIP);
//			else if (criteria.getSource().equalsIgnoreCase("REIMBURSEMENTREQUISITIONER"))
//				RelationshipHelper.build(query, PartyRoleType.REIMBURSEMENT_REQUISITIONER, PartyRoleType.FACILITY, PartyRelationshipType.EMPLOYMENT_RELATIONSHIP);
//			else if (criteria.getSource().equalsIgnoreCase("REIMBURSEMENTAPPROVER"))
//				RelationshipHelper.build(query, PartyRoleType.REIMBURSEMENT_APPROVER, PartyRoleType.FACILITY, PartyRelationshipType.EMPLOYMENT_RELATIONSHIP);
//			else if (criteria.getSource().equalsIgnoreCase("TRANSACTIONREQUISITIONER"))
//				RelationshipHelper.build(query, PartyRoleType.INTER_TRANSACTION_REQUISITIONER, PartyRoleType.FACILITY, PartyRelationshipType.EMPLOYMENT_RELATIONSHIP);
//			else if (criteria.getSource().equalsIgnoreCase("TRANSACTIONAPPROVER"))
//				RelationshipHelper.build(query, PartyRoleType.INTER_TRANSACTION_APPROVER, PartyRoleType.FACILITY, PartyRelationshipType.EMPLOYMENT_RELATIONSHIP);
//			else if (criteria.getSource().equalsIgnoreCase("COLLECTOR"))
//				RelationshipHelper.build(query, PartyRoleType.COLLECTOR, PartyRoleType.FACILITY, PartyRelationshipType.EMPLOYMENT_RELATIONSHIP);
//			else if (criteria.getSource().equalsIgnoreCase("FIXEDASSETAPPROVER"))
//				RelationshipHelper.build(query, PartyRoleType.FIXED_ASSET_APPROVER, PartyRoleType.FACILITY, PartyRelationshipType.EMPLOYMENT_RELATIONSHIP);
//			else if (criteria.getSource().equalsIgnoreCase("MEMOAPPROVER"))
//				RelationshipHelper.build(query, PartyRoleType.MEMO_APPROVER, PartyRoleType.FACILITY, PartyRelationshipType.EMPLOYMENT_RELATIONSHIP);
//			else if (criteria.getSource().equalsIgnoreCase("FINANCIALAPPROVER"))
//				RelationshipHelper.build(query, PartyRoleType.FINANCIAL_APPROVER, PartyRoleType.FACILITY, PartyRelationshipType.EMPLOYMENT_RELATIONSHIP);

			getAccessibleParties().addAll(query.list());
		}

		super.init();
	}
}

