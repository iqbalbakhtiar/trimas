/**
 * Oct 27, 2008 4:49:43 PM
 * com.siriuserp.sdk.dm
 * ContactMechanism.java
 */
package com.siriuserp.sdk.dm;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonValue;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Entity
@Table(name="contact_mechanism")
public class ContactMechanism extends Model implements JSONSupport
{
    private static final long serialVersionUID = 5738766381408352582L;

    @Column(name="contact")
    private String contact;
    
    @Column(name="active")
    @Type(type="yes_no")
    private boolean active;
    
    @Column(name="note")
    private String note;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_party")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Party party;
    
    @Column(name="contact_mechanism_type")
    @Enumerated(EnumType.STRING)
    private ContactMechanismType contactMechanismType = ContactMechanismType.PHONE;
    
    public ContactMechanism(){}
    
    public String getContact()
    {
        return contact;
    }

    public void setContact(String contact)
    {
        this.contact = contact;
    }

    public ContactMechanismType getContactMechanismType()
    {
        return contactMechanismType;
    }

    public void setContactMechanismType(ContactMechanismType contactMechanismType)
    {
        this.contactMechanismType = contactMechanismType;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public Party getParty()
    {
        return party;
    }

    public void setParty(Party party)
    {
        this.party = party;
    }

    @Override
    public String getAuditCode()
    {
        return this.contactMechanismType.toString();
    }

    @JsonValue
    public Map<String, Object> val()
    {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("contactId",getId());
        map.put("contactName",getContact());
        map.put("contactType",getContactMechanismType());
        
        return map;
    }
}
