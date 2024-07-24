/**
 * Jan 7, 2010 2:54:32 PM
 * com.siriuserp.administration.util
 * TaxHelper.java
 */
package com.siriuserp.administration.util;

import java.math.BigDecimal;

import com.siriuserp.sdk.dm.Tax;
import com.siriuserp.sdk.utility.DecimalHelper;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class TaxHelper
{
    public static final synchronized BigDecimal get(Tax tax,BigDecimal amount)
    {
        if(tax == null)
            return BigDecimal.ZERO;
        
        return amount.multiply(DecimalHelper.percent(tax.getTaxRate()));
    }
}
