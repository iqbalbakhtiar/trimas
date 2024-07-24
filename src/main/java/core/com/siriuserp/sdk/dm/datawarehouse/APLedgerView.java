/**
 * Mar 11, 2010 1:52:02 PM
 * com.siriuserp.sdk.dm.datawarehouse
 * APLedgerView.java
 */
package com.siriuserp.sdk.dm.datawarehouse;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 * Version 1.5
 */
public interface APLedgerView extends Comparable<APLedgerView>
{
    public Date getDate();
    
    public String getLedgerType();
    
    public String getCode();
    
    public BigDecimal getDebet();
    
    public BigDecimal getCredit();
}
