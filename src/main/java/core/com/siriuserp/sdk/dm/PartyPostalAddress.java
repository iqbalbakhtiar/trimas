/**
 * Oct 27, 2008 5:26:17 PM
 * com.siriuserp.sdk.dm
 * PartyPostalAddress.java
 */
package com.siriuserp.sdk.dm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Entity
@Table(name="party_postal_address")
public class PartyPostalAddress extends Model
{
    private static final long serialVersionUID = 836951766005200497L;

    @Column(name="note")
    private String note;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_party")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Party party;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_postal_address")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private PostalAddress postalAddress;
    
    public PartyPostalAddress(){}
    
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

    public PostalAddress getPostalAddress()
    {
        return postalAddress;
    }

    public void setPostalAddress(PostalAddress postalAddress)
    {
        this.postalAddress = postalAddress;
    }

    @Override
    public String getAuditCode()
    {
        return "";
    }
}
