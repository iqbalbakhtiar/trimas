/**
 * Nov 15, 2008 11:12:21 AM
 * com.siriuserp.sdk.dm
 * AccessType.java
 */
package com.siriuserp.sdk.dm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Entity
@Table(name="access_type")
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class AccessType extends Model
{
    private static final long serialVersionUID = -3322181174934156816L;

    public static final Long NO_ACCESS = Long.valueOf(1);
    
    public static final Long READ_ONLY = Long.valueOf(2);
    
    public static final Long READ_ADD = Long.valueOf(3);
    
    @Column(name="name")
    private String name;
    
    @Column(name="read")
    @Type(type="yes_no")
    private boolean read;
    
    @Column(name="add")
    @Type(type="yes_no")
    private boolean add;
    
    @Column(name="edit")
    @Type(type="yes_no")
    private boolean edit;
    
    @Column(name="delete")
    @Type(type="yes_no")
    private boolean delete;
    
    @Column(name="print")
    @Type(type="yes_no")
    private boolean print;
    
    @Column(name="rank")
    private Long rank;
    
    public AccessType(){}
    
    public Long getRank()
    {
        return rank;
    }

    public void setRank(Long rank)
    {
        this.rank = rank;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean isRead()
    {
        return read;
    }

    public void setRead(boolean read)
    {
        this.read = read;
    }

    public boolean isAdd()
    {
        return add;
    }

    public void setAdd(boolean add)
    {
        this.add = add;
    }

    public boolean isEdit()
    {
        return edit;
    }

    public void setEdit(boolean edit)
    {
        this.edit = edit;
    }

    public boolean isDelete()
    {
        return delete;
    }

    public void setDelete(boolean delete)
    {
        this.delete = delete;
    }

    public boolean isPrint()
    {
        return print;
    }

    public void setPrint(boolean print)
    {
        this.print = print;
    }

    @Override
    public String getAuditCode()
    {
        return this.id+","+this.name;
    }
}
