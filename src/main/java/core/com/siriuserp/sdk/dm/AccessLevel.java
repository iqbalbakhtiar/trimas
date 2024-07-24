package com.siriuserp.sdk.dm;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.security.GrantedAuthority;

import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Ronny Mailindra
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Entity
@Table(name="access_level")
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class AccessLevel extends Model implements GrantedAuthority
{
    private static final long	serialVersionUID	= -7064541718976742768L;

    @Column(name="level",nullable=false)
    private String level;
    
    @Column(name="name",nullable=false)
    private String name;
    
    @ManyToMany
    (
            cascade={CascadeType.PERSIST,CascadeType.MERGE},
            targetEntity=com.siriuserp.sdk.dm.ModuleAction.class
    )
    @JoinTable
    (
            name="accesslevel_action",
            joinColumns={@JoinColumn(name="access_level_id")},
            inverseJoinColumns={@JoinColumn(name="action_id")}
    )
    private Set<ModuleAction> actions = new HashSet<ModuleAction>();
    
    public AccessLevel(){}
    
    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }
    public String getAuthority() {
        return this.getLevel();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Set<ModuleAction> getActions() {
        return actions;
    }
    public void setActions(Set<ModuleAction> actions) {
        this.actions = actions;
    }
    
    public static final AccessLevel newInstance(String id)
    {
        if(SiriusValidator.validateParamWithZeroPosibility(id))
        {
            AccessLevel accessLevel = new AccessLevel();
            accessLevel.setId(Long.valueOf(id));
            
            return accessLevel;
        }
        
        return null;
    }

    @Override
    public String getAuditCode()
    {
        return null;
    }

    @Override
    public int compareTo(Object o)
    {
        return 0;
    }
}
