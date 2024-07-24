/**
 * May 21, 2008 3:28:28 PM
 * com.siriuserp.sdk.dm
 * Locale.java
 */
package com.siriuserp.sdk.dm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Entity
@Table(name="locale_profile")
public class Locale extends Model implements Serializable
{
    private static final long serialVersionUID = -2157408814109466991L;

    @Column(name="code")
    private String code;
    
    @Column(name="name")
    private String name;
    
    @Column(name="base")
    @Type(type="yes_no")
    private boolean base;
    
    public Locale(){}
    
    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
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

    @Override
    public String getAuditCode()
    {
        return this.id+","+this.code;
    }
}
