/**
 * Nov 15, 2008 11:39:38 AM
 * com.siriuserp.sdk.dm
 * ModuleDetail.java
 */
package com.siriuserp.sdk.dm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name="module_detail")
public class ModuleDetail extends Model
{
    private static final long serialVersionUID = 7234679205969676788L;

    @Column(name="uri")
    private String uri;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fk_module")
    @LazyToOne(LazyToOneOption.PROXY)
    @Fetch(FetchMode.SELECT)
    private Module module;
    
    @Column(name="module_detail_type")
    @Enumerated(EnumType.STRING)
    private ModuleDetailType detailType;
    
    public ModuleDetail(){}
    
    public String getUri()
    {
        return uri;
    }

    public void setUri(String uri)
    {
        this.uri = uri;
    }

    public Module getModule()
    {
        return module;
    }

    public void setModule(Module module)
    {
        this.module = module;
    }

    public ModuleDetailType getDetailType()
    {
        return detailType;
    }

    public void setDetailType(ModuleDetailType detailType)
    {
        this.detailType = detailType;
    }

    @Override
    public String getAuditCode()
    {
        return this.id+"";
    }
}
