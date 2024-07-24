package com.siriuserp.sdk.dm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Ronny Mailindra
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Entity
@Table(name="religion")
public class Religion extends Model implements Serializable
{
    private static final long serialVersionUID = 5744524913475917079L;

    @Column(name="name",unique=true)
    private String name;

    public Religion(){}
    
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public static final Religion newInstance(String id)
    {
        if(SiriusValidator.validateParamWithZeroPosibility(id))
        {
            Religion religion = new Religion();
            religion.setId(Long.valueOf(id.trim()));

            return religion;
        }

        return null;
    }

    @Override
    public String getAuditCode()
    {
        return null;
    }
}
