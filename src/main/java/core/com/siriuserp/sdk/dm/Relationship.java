/**
 * Mar 5, 2008 16:35:00 AM
 * net.konsep.sirius.model
 * Relationship.java
 */
package com.siriuserp.sdk.dm;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Arief Ginanjar (agien)
 * 
 * @hibernate.class
 * table="relationship"
 * lazy="false"
 */
@Entity
@Table(name="relationship")
@AttributeOverrides( 
{ 
    @AttributeOverride(name="id", column=@Column(name="id")),
    @AttributeOverride(name="version", column=@Column(name="version")),
    @AttributeOverride(name="createdDate", column=@Column(name="created_date")),
    @AttributeOverride(name="updatedDate", column=@Column(name="updated_date")),
    @AttributeOverride(name="createdBy", column=@Column(name="fk_person_created_by")),
    @AttributeOverride(name="updatedBy", column=@Column(name="fk_person_updated_by"))
})
public class Relationship extends Model implements Serializable
{
    private static final long serialVersionUID = -751899063794235393L;
    
    @Column(name="relation_type",nullable=false,length=30)
    private String relationType;
    
    public Relationship() {}
    
    public String getRelationType()
    {
        return relationType;
    }

    public void setRelationType(String relationType)
    {
        this.relationType = relationType;
    }

    @Override
    public String toString()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("id: "+id+"\n");
        buffer.append("relationType: "+relationType+"\n");
        
        return buffer.toString();
    }
    
    @Override
    public String getAuditCode()
    {
        return this.id+","+this.relationType;
    }
}
