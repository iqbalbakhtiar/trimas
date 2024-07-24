/**
 * Apr 7, 2006
 * UnitOfMeasure.java
 */
package com.siriuserp.sdk.dm;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastSet;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Entity
@Table(name="unit_of_measure")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class UnitOfMeasure extends Model
{
    private static final long serialVersionUID = 5359175064975506659L;

    @Column(name="measure_id")
    private String measureId;

    @Column(name="name")
    private String name;

    @Column(name="is_base",length=1)
    @Type(type="yes_no")
    private boolean base = false;
    
    @Column(name="is_pack",length=1)
    @Type(type="yes_no")
    private boolean pack = false;
    
    @Column(name="pattern")
    private String pattern;
    
    @Enumerated(EnumType.STRING)
    @Column(name="unit_type",length=25,nullable=false)
    private UnitType type;
    
    @OneToMany(mappedBy="from",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Fetch(FetchMode.SELECT)
    @Type(type="com.siriuserp.sdk.hibernate.types.SiriusHibernateCollectionType")
    private Set<UnitofMeasureFactor> factors = new FastSet<UnitofMeasureFactor>();
    
    public UnitOfMeasure(){}

    public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public UnitType getType()
    {
        return type;
    }

    public void setType(UnitType type)
    {
        this.type = type;
    }

    public String getMeasureId()
    {
        return measureId;
    }

    public void setMeasureId(String measureId)
    {
        this.measureId = measureId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean isBase()
    {
        return base;
    }

    public void setBase(boolean base)
    {
        this.base = base;
    }

    public boolean isPack() {
		return pack;
	}

	public void setPack(boolean pack) {
		this.pack = pack;
	}
	
	public boolean isDecimal()
	{
		return getPattern().equals("#,##0.000");
	}

	public static final UnitOfMeasure newInstance(String id)
    {
        if(SiriusValidator.validateParamWithZeroPosibility(id))
        {
            UnitOfMeasure measure = new UnitOfMeasure();
            measure.setId(Long.valueOf(id));
            return measure;
        }
        
        return null;
    }
    
    @Override
    public String getAuditCode()
    {
        return this.measureId+","+this.name;
    }

    public Set<UnitofMeasureFactor> getFactors()
    {
        return factors;
    }

    public void setFactors(Set<UnitofMeasureFactor> factors)
    {
        this.factors = factors;
    }
}
