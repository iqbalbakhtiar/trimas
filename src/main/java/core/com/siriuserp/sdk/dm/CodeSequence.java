/**
 * Mar 2, 2011 8:51:55 AM
 * com.siriuserp.sdk.dm
 * CodeGeneratorSequence.java
 */
package com.siriuserp.sdk.dm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia,PT
 * www.siriuserp.com
 * Sinch version 1.5
 */
@Entity
@Table(name="code_generator_sequence")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class CodeSequence implements Serializable
{
    private static final long serialVersionUID = 3619648313176025729L;

    public static final Long DAY = Long.valueOf(1);
	public static final Long MONTH = Long.valueOf(2);
	public static final Long YEAR = Long.valueOf(3);
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @Column(name="code_day")
    private int day;
    
    @Column(name="code_month")
    private int month;
    
    @Column(name="code_year")
    private int year;

    @Enumerated(EnumType.STRING)
    @Column(name="table_type")
    private TableType type; 

    @Column(name="company_code")
    private String company;
    
    @Column(name="sequence_1")
    private int sequence1;
    
    @Column(name="sequence_2")
    private int sequence2;
    
    @Column(name="sequence_3")
    private int sequence3;
 
    public CodeSequence(){}

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public int getDay()
    {
        return day;
    }

    public void setDay(int day)
    {
        this.day = day;
    }

    public int getMonth()
    {
        return month;
    }

    public void setMonth(int month)
    {
        this.month = month;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public TableType getType()
    {
        return type;
    }

    public void setType(TableType type)
    {
        this.type = type;
    }

	public int getSequence1() 
	{
		return sequence1;
	}

	public void setSequence1(int sequence1) 
	{
		this.sequence1 = sequence1;
	}

	public int getSequence2() 
	{
		return sequence2;
	}

	public void setSequence2(int sequence2)
	{
		this.sequence2 = sequence2;
	}

	public int getSequence3() 
	{
		return sequence3;
	}

	public void setSequence3(int sequence3) 
	{
		this.sequence3 = sequence3;
	}

	public String getCompany()
    {
        return company;
    }

    public void setCompany(String company)
    {
        this.company = company;
    }
}
