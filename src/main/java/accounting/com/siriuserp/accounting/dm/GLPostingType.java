package com.siriuserp.accounting.dm;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public enum GLPostingType
{
	DEBET, CREDIT;

	public GLPostingType getReserveType()
	{
		if (this.equals(GLPostingType.DEBET))
			return CREDIT;
		else
			return DEBET;
	}
}
