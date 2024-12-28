/**
 * May 20, 2009 8:53:40 AM
 * com.siriuserp.sdk.base
 * PostingRole.java
 */
package com.siriuserp.accounting.posting;

import com.siriuserp.accounting.dm.Postingable;
import com.siriuserp.sdk.base.Command;
import com.siriuserp.sdk.exceptions.ServiceException;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public interface PostingRole extends Command
{
	public <T extends Postingable> T getPostingable();
	public void setPostingable(Postingable postingable);

	public AutomaticPosting getAutomaticPosting();
	public void setAutomaticPosting(AutomaticPosting automaticPosting);

	public void inisialize() throws ServiceException;
	public void commit() throws ServiceException;
	public void audit() throws ServiceException;

}
