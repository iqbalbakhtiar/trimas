/**
 * Oct 22, 2008 11:23:21 AM
 * com.siriuserp.sdk.dm
 * UnitofMeasureFactor.java
 */
package com.siriuserp.sdk.dm;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Entity
@Table(name="unit_of_measure_factor")
public class UnitofMeasureFactor extends Model
{
    private static final long serialVersionUID = -7672223719898108980L;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_uom_from")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private UnitOfMeasure from;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_uom_to")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private UnitOfMeasure to;
    
    @Column(name="factor")
    private BigDecimal factor = BigDecimal.ONE;
    
    public UnitofMeasureFactor(){}
    
    public UnitOfMeasure getFrom()
    {
        return from;
    }

    public void setFrom(UnitOfMeasure from)
    {
        this.from = from;
    }

    public UnitOfMeasure getTo()
    {
        return to;
    }

    public void setTo(UnitOfMeasure to)
    {
        this.to = to;
    }

    public BigDecimal getFactor()
    {
        return factor;
    }

    public void setFactor(BigDecimal factor)
    {
        this.factor = factor;
    }

    @Override
    public String getAuditCode()
    {
        return this.id+"";
    }
}
