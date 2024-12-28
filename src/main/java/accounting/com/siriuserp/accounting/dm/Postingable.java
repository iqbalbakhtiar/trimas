/**
 * May 20, 2009 9:52:01 AM
 * com.siriuserp.sdk.dm
 * Postingable.java
 */
package com.siriuserp.accounting.dm;

import java.util.Date;

import com.siriuserp.sdk.dm.Party;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface Postingable
{
	public Long getId();
	public Party getOrganization();
	public Date getDate();
	
	public void setJournalEntry(JournalEntry journalEntry);
	public JournalEntry getJournalEntry();
}
