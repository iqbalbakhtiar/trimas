/**
 * Oct 17, 2008 3:12:03 PM
 * com.siriuserp.sdk.dm
 * Unit.java
 */
package com.siriuserp.sdk.dm;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;


/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Embeddable
public class Unit implements Serializable
{
    private static final long serialVersionUID = 2433733341409072497L;

    @Column(name="quantity")
    protected BigDecimal quantity = BigDecimal.valueOf(0);
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_unit_of_measure")
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @Fetch(FetchMode.SELECT)
    protected UnitOfMeasure unitOfMeasure;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_unit_of_measure_factor")
    @LazyToOne(LazyToOneOption.NO_PROXY)
    @Fetch(FetchMode.SELECT)
    protected UnitofMeasureFactor factor;
    
    public Unit(){}

    public BigDecimal getQuantity()
    {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity)
    {
        this.quantity = quantity;
    }

    public UnitOfMeasure getUnitOfMeasure()
    {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure)
    {
        this.unitOfMeasure = unitOfMeasure;
    }

    public UnitofMeasureFactor getFactor()
    {
        return factor;
    }

    public void setFactor(UnitofMeasureFactor factor)
    {
        this.factor = factor;
    }
}
