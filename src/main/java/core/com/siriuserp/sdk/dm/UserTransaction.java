/**
 * Dec 9, 2008 11:00:19 AM
 * com.siriuserp.sdk.dm
 * Balance.java
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
public class UserTransaction
{
    @Column(name="debet")
    private BigDecimal debet = BigDecimal.ZERO;
    
    @Column(name="credit")
    private BigDecimal credit = BigDecimal.ZERO;
    
    @Column(name="default_currency_debet")
    private BigDecimal defaultdebet = BigDecimal.ZERO;
    
    @Column(name="default_currency_credit")
    private BigDecimal defaultcredit = BigDecimal.ZERO;
    
    @Column(name="entry_debet")
    private BigDecimal entrydebet = BigDecimal.ZERO;
    
    @Column(name="entry_credit")
    private BigDecimal entrycredit = BigDecimal.ZERO;
    
    @Column(name="correction_debet")
    private BigDecimal correctiondebet = BigDecimal.ZERO;
    
    @Column(name="correction_credit")
    private BigDecimal correctioncredit = BigDecimal.ZERO;
    
    @Column(name="adjustment_debet")
    private BigDecimal adjustmentdebet = BigDecimal.ZERO;
    
    @Column(name="adjustment_credit")
    private BigDecimal adjustmentcredit = BigDecimal.ZERO;
    
    public UserTransaction(){}

    public BigDecimal getDebet()
    {
        return debet;
    }

    public void setDebet(BigDecimal debet)
    {
        this.debet = debet;
    }

    public BigDecimal getCredit()
    {
        return credit;
    }

    public void setCredit(BigDecimal credit)
    {
        this.credit = credit;
    }

    public BigDecimal getDefaultdebet()
    {
        return defaultdebet;
    }

    public void setDefaultdebet(BigDecimal defaultdebet)
    {
        this.defaultdebet = defaultdebet;
    }

    public BigDecimal getDefaultcredit()
    {
        return defaultcredit;
    }

    public void setDefaultcredit(BigDecimal defaultcredit)
    {
        this.defaultcredit = defaultcredit;
    }

    public BigDecimal getEntrydebet()
    {
        return entrydebet;
    }

    public void setEntrydebet(BigDecimal entrydebet)
    {
        this.entrydebet = entrydebet;
    }

    public BigDecimal getEntrycredit()
    {
        return entrycredit;
    }

    public void setEntrycredit(BigDecimal entrycredit)
    {
        this.entrycredit = entrycredit;
    }

    public BigDecimal getCorrectiondebet()
    {
        return correctiondebet;
    }

    public void setCorrectiondebet(BigDecimal correctiondebet)
    {
        this.correctiondebet = correctiondebet;
    }

    public BigDecimal getCorrectioncredit()
    {
        return correctioncredit;
    }

    public void setCorrectioncredit(BigDecimal correctioncredit)
    {
        this.correctioncredit = correctioncredit;
    }

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
}
