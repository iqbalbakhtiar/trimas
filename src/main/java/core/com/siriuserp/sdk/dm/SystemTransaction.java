/**
 * Dec 9, 2008 11:41:56 AM
 * com.siriuserp.sdk.dao.impl
 * SystemTransaction.java
 */
package com.siriuserp.sdk.dm;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Embeddable
public class SystemTransaction
{
    @Column(name="autoadjustment_debet")
    private BigDecimal adjustmentdebet = BigDecimal.ZERO;
    
    @Column(name="autoadjustment_credit")
    private BigDecimal adjustmentcredit = BigDecimal.ZERO;
    
    @Column(name="closing_debet")
    private BigDecimal closingdebet = BigDecimal.ZERO;
    
    @Column(name="closing_credit")
    private BigDecimal closingcredit = BigDecimal.ZERO;
    
    @Column(name="opening_debet")
    private BigDecimal openingdebet = BigDecimal.ZERO;
    
    @Column(name="opening_credit")
    private BigDecimal openingcredit = BigDecimal.ZERO;
    
    public SystemTransaction(){}

    public BigDecimal getAdjustmentdebet()
    {
        return adjustmentdebet;
    }

    public void setAdjustmentdebet(BigDecimal adjustmentdebet)
    {
        this.adjustmentdebet = adjustmentdebet;
    }

    public BigDecimal getAdjustmentcredit()
    {
        return adjustmentcredit;
    }

    public void setAdjustmentcredit(BigDecimal adjustmentcredit)
    {
        this.adjustmentcredit = adjustmentcredit;
    }

    public BigDecimal getClosingdebet()
    {
        return closingdebet;
    }

    public void setClosingdebet(BigDecimal closingdebet)
    {
        this.closingdebet = closingdebet;
    }

    public BigDecimal getClosingcredit()
    {
        return closingcredit;
    }

    public void setClosingcredit(BigDecimal closingcredit)
    {
        this.closingcredit = closingcredit;
    }

    public BigDecimal getOpeningdebet()
    {
        return openingdebet;
    }

    public void setOpeningdebet(BigDecimal openingdebet)
    {
        this.openingdebet = openingdebet;
    }

    public BigDecimal getOpeningcredit()
    {
        return openingcredit;
    }

    public void setOpeningcredit(BigDecimal openingcredit)
    {
        this.openingcredit = openingcredit;
    }
}
