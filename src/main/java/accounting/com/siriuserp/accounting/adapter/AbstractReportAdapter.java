/**
 * Dec 2, 2008 10:31:05 AM
 * com.siriuserp.sdk.adapter
 * AbstractReportAdapter.java
 */
package com.siriuserp.accounting.adapter;

import com.siriuserp.sdk.adapter.UIAdapter;
import com.siriuserp.sdk.dm.Party;

import javolution.util.FastList;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Getter
@Setter
public abstract class AbstractReportAdapter implements UIAdapter
{
	private static final long serialVersionUID = 2017816684173502429L;

	protected Party organization;
	protected FastList<Long> organizations = new FastList<Long>();
}
