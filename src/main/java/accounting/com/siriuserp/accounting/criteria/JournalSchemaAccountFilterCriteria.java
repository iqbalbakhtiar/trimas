/**
 * Nov 18, 2008 4:41:46 PM
 * com.siriuserp.accounting.dto.filter
 * JournalSchemaAccountFilterCriteria.java
 */
package com.siriuserp.accounting.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class JournalSchemaAccountFilterCriteria extends AbstractFilterCriteria
{
    private static final long serialVersionUID = 3171699846167527561L;

    private Long schema;
    
    private String code;
    
    private String name;
    
    private String typeValue;
    
    public JournalSchemaAccountFilterCriteria(){}

    public Long getSchema()
    {
        return schema;
    }

    public void setSchema(Long schema)
    {
        this.schema = schema;
    }

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

    public String getTypeValue()
    {
        return typeValue;
    }

    public void setTypeValue(String postingType)
    {
        this.typeValue = postingType;
    }
}
