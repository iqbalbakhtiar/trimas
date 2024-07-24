/**
 * Apr 24, 2006
 * Tax.java
 */
package com.siriuserp.sdk.dm;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Entity
@Table(name="tax")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Tax extends Model
{
    private static final long   serialVersionUID    = -5117967880187565037L;

    @Column(name="tax_rate")
    private BigDecimal taxRate = BigDecimal.ZERO;
    
    @Column(name="tax_id")
    private String taxId;
    
    @Column(name="tax_name")
    private String taxName;
    
    @Column(name="description")
    private String description;
    
    public Tax(){}
    
    public BigDecimal getTaxRate()
    {
        return taxRate;
    }
    public void setTaxRate(BigDecimal taxRate)
    {
        this.taxRate = taxRate;
    }
    public String getTaxId()
    {
        return taxId;
    }
    public void setTaxId(String taxId)
    {
        this.taxId = taxId;
    }
    public String getTaxName()
    {
        return taxName;
    }
    public void setTaxName(String taxName)
    {
        this.taxName = taxName;
    }
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public static final synchronized Tax newInstance(String id, String taxName)
    {
        if(SiriusValidator.validateParamWithZeroPosibility(id))
        {
            Tax tax = new Tax();
            tax.setId(Long.valueOf(id));
            tax.setTaxName(taxName);
            
            return tax;
        }
        
        return null;
    }
    
    @Override
    public String getAuditCode()
    {
        return this.taxId+","+this.taxName;
    }
}
