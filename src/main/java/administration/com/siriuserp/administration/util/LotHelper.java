/**
 * 
 */
package com.siriuserp.administration.util;

import com.siriuserp.sdk.dm.Lot;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Betsu Brahmana Restu
 * Sirius Indonesia, PT
 * betsu@siriuserp.com
 *
 */
public class LotHelper 
{
	private static final StringBuilder init(Lot lot) 
	{
		StringBuilder builder = new StringBuilder();
		if(lot != null)
			builder.append(SiriusValidator.getEmptyStringParam(lot.getCode()));
		
		return builder;
	}
	
	public static final String getLot(Lot lot)
	{
		return init(lot).toString();
	}
	
	public static final String getKey(Lot lot)
	{
		StringBuilder builder = init(lot);
		if(lot != null)
			builder.append(SiriusValidator.getEmptyStringParam(lot.getSerial()));
		
		return builder.toString();
	}
	
	public static final String getScrap(Lot lot)
	{
		StringBuilder builder = init(lot);
		if(lot != null)
			builder.append(SiriusValidator.getEmptyStringParam(lot.getInfo()));
		
		return builder.toString();
	}
	
	public static final String getCompare(Lot lot)
	{
		StringBuilder builder = init(lot);
		if(lot != null)
		{
			builder.append(SiriusValidator.getEmptyStringParam(lot.getSerial()));
			builder.append(SiriusValidator.getEmptyStringParam(lot.getInfo()));
		}

		return builder.toString();
	}
	
	public static final String getCat(Long category, Lot lot)
	{
		StringBuilder builder = new StringBuilder();
		builder.append(category);
		builder.append(init(lot));
		
		return builder.toString();
	}
}
