/**
 * May 21, 2008 3:31:07 PM
 * com.siriuserp.sdk.dm
 * Theme.java
 */
package com.siriuserp.sdk.dm;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Entity
@Table(name="theme_profile")
@AttributeOverrides( 
{ 
    @AttributeOverride(name="id", column=@Column(name="id")),
    @AttributeOverride(name="version", column=@Column(name="version")),
    @AttributeOverride(name="createdDate", column=@Column(name="created_date")),
    @AttributeOverride(name="updatedDate", column=@Column(name="updated_date")),
    @AttributeOverride(name="createdBy", column=@Column(name="fk_person_created_by")),
    @AttributeOverride(name="updatedBy", column=@Column(name="fk_person_updated_by"))
})
public class Theme extends Model implements Serializable
{
    private static final long serialVersionUID = 1234326711945987202L;

    @Column(name="code")
    private String code;
    
    @Column(name="name")
    private String name;
    
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
    
    @Override
    public String getAuditCode()
    {
        return null;
    }

}
