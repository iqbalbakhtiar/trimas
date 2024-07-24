/**
 * Apr 2, 2009 5:41:37 PM
 * com.siriuserp.ar.service
 * Normalizer.java
 */
package com.siriuserp.sdk.transformer;

import com.siriuserp.sdk.exceptions.ServiceException;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public interface Transformer
{
    public Object transform(Object proxy)throws ServiceException;
}
