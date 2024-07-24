/**
 * Nov 4, 2009 2:35:14 PM
 * com.siriuserp.tools.query
 * CompanyStructureGridViewQuery.java
 */
package com.siriuserp.tools.query;

import com.siriuserp.sdk.db.AbstractGridViewQuery;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class CompanyStructureGridViewQuery extends AbstractGridViewQuery
{

    @Override
    public Long count()
    {
        return null;
    }

    @Override
    public Object execute()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT DISTINCT(org) FROM Organization org,PartyRelationship rel WHERE rel.relationshipType.id = 5 AND (rel.fromRole.party.id=org.id OR rel.toRole.party.id=org.id)");
        builder.append(" ORDER BY org.firstName ASC");
        
        return getSession().createQuery(builder.toString()).list();
    }

}
