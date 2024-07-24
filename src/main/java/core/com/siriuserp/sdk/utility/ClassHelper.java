/**
 * 
 */
package com.siriuserp.sdk.utility;

/**
 * @author
 * Betsu Brahmana Restu
 * PT. Sirius Indonesia
 * betsu@siriuserp.com
 */
public class ClassHelper
{
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean isSubClassOnly(Class clazz, Object object) 
	{
	    return object != null && clazz.isAssignableFrom((Class) object) && object.getClass() != clazz;
	}
}
