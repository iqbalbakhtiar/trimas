package com.siriuserp.sdk.dm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.security.GrantedAuthority;

/**
 * @author Ronny Mailindra
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Entity
@Table(name="module_action")
public class ModuleAction extends Model implements GrantedAuthority
{
	private static final long	serialVersionUID	= 9058520024004525512L;
	
    @Column(name="name",nullable=false,length=50)
	private String name;
    
    public ModuleAction(){}

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

	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

	@Override
	public String getAuthority() {
		return getName();
	}   
}
