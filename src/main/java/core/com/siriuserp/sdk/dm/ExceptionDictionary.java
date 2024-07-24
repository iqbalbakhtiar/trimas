/**
 * Dec 11, 2009 9:48:29 AM
 * com.siriuserp.sdk.dm
 * ExceptionDictionary.java
 */
package com.siriuserp.sdk.dm;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javolution.util.FastSet;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Entity
@Table(name="exception_dictionary")
public class ExceptionDictionary implements Serializable
{
    private static final long serialVersionUID = -1565377108099301651L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @Column(name="code")
    private String code;
    
    @Column(name="description")
    private String description;
    
    @Version
    private Long version;
    
    @OneToMany(mappedBy="dictionary",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Fetch(FetchMode.SELECT)
    private Set<ExceptionCause> causes = new FastSet<ExceptionCause>();
    
    @OneToMany(mappedBy="dictionary",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Fetch(FetchMode.SELECT)
    private Set<ExceptionSolution> solutions = new FastSet<ExceptionSolution>();
    
    public ExceptionDictionary(){}

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Long getVersion()
    {
        return version;
    }

    public void setVersion(Long version)
    {
        this.version = version;
    }

    public Set<ExceptionCause> getCauses()
    {
        return causes;
    }

    public void setCauses(Set<ExceptionCause> causes)
    {
        this.causes = causes;
    }

    public Set<ExceptionSolution> getSolutions()
    {
        return solutions;
    }

    public void setSolutions(Set<ExceptionSolution> solutions)
    {
        this.solutions = solutions;
    }
}
