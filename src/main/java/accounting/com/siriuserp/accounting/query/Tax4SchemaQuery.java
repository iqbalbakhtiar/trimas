/**
 * Dec 22, 2009 9:30:19 AM
 * com.siriuserp.administration.query
 * Tax4SchemaQuery.java
 */
package com.siriuserp.accounting.query;

import org.hibernate.Query;

import com.siriuserp.sdk.db.AbstractGridViewQuery;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public class Tax4SchemaQuery extends AbstractGridViewQuery
{
	private Long schema;

	public Tax4SchemaQuery(Long schema)
	{
		this.schema = schema;
	}

	@Override
	public Long count()
	{
		return null;
	}

	@Override
	public Object execute()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FROM Tax tax WHERE tax.id not ");
		builder.append("in(SELECT schema.tax.id FROM TaxAccountSchema schema WHERE schema.accountingSchema.id =:aschema)");

		Query query = getSession().createQuery(builder.toString());
		query.setParameter("aschema", schema);

		return query.list();
	}
}
