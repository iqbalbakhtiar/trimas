/**
 * 
 */
package com.siriuserp.administration.util;

import org.hibernate.Query;

/**
 * @author
 * Betsu Brahmana Restu 
 * Sirius Indonesia, PT
 * betsu@siriuserp.com
 */

public class RelationshipHelper 
{
	public static final synchronized void build(Query query, Long...args)
	{
		for(int i=0;i<args.length;i++)
			query.setParameter("rel"+i, args[i]);
	}
}
