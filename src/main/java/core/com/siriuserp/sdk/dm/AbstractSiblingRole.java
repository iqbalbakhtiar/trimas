package com.siriuserp.sdk.dm;

import org.springframework.beans.factory.annotation.Autowired;

import com.siriuserp.sdk.dao.GenericDao;

/**
 * @author
 * Betsu Brahmana Restu
 * PT. Sirius Indonesia
 * betsu@siriuserp.com
 */

@SuppressWarnings("unchecked")
public abstract class AbstractSiblingRole implements SiblingRole
{
	protected Siblingable siblingable;

	@Autowired
	protected GenericDao genericDao;

	@Override
	public <T extends Siblingable> T getSiblingable()
	{
		return (T) siblingable;
	}

	@Override
	public void setSiblingable(Siblingable siblingable)
	{
		this.siblingable = siblingable;
	}
}
