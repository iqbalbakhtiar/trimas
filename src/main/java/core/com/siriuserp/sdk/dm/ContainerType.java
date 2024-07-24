/**
 * Sep 20, 2006 11:48:03 AM
 * net.konsep.sirius.model
 * ContainerType.java
 */
package com.siriuserp.sdk.dm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Entity
@Table(name="container_type")
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class ContainerType extends Model
{
    private static final long serialVersionUID = -1067928907408305372L;
    
    @Column(name="name")
    private String name;
    
    @Column(name="note")
    private String note;
    
    public ContainerType(){}

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    @Override
    public String getAuditCode()
    {
        return null;
    }
}
