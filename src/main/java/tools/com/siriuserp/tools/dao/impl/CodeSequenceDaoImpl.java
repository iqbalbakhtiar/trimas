/**
 * May 31, 2006
 * CustomerSequenceDaoImpl.java
 */
package com.siriuserp.tools.dao.impl;

import java.util.Date;

import org.hibernate.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.db.DaoHelper;
import com.siriuserp.sdk.dm.CodeSequence;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.SiriusValidator;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Transactional(rollbackFor=Exception.class)
@Component
public class CodeSequenceDaoImpl extends DaoHelper<CodeSequence> implements CodeSequenceDao
{
	private CodeSequence load(Integer day, Integer month, Integer year, String company, TableType tableType)
    {
    	StringBuilder builder = new StringBuilder();
        builder.append("FROM CodeSequence gen ");
        builder.append("WHERE gen.type =:type ");
        
        if(SiriusValidator.validateIntParam(day))
        	builder.append("AND gen.day =:day ");
        	
        if(SiriusValidator.validateIntParam(month))
        	builder.append("AND gen.month =:month ");
        
        if(SiriusValidator.validateIntParam(year))
        	builder.append("AND gen.year =:year ");
        
        if(SiriusValidator.validateParam(company))
        	builder.append("AND gen.company LIKE :company ");
        
        Query query = getSession().createQuery(builder.toString());
        query.setCacheable(true);
        
        if(SiriusValidator.validateIntParam(day))
        	query.setParameter("day",day);
        
        if(SiriusValidator.validateIntParam(month))
        	query.setParameter("month",month);
        
        if(SiriusValidator.validateIntParam(year))
        	query.setParameter("year",year);
        
        if(SiriusValidator.validateParam(company))
        	query.setParameter("company","%"+company+"%");
        
        query.setParameter("type",tableType);
        query.setMaxResults(1);
        
        return (CodeSequence)query.uniqueResult();
    }

    private CodeSequence loadStartDay(Date date, String company, TableType tableType)
    {
    	return load(DateHelper.getDayAsInt(date), DateHelper.toMonth(date), DateHelper.getYear(date), company, tableType);
    }

    private CodeSequence loadStartMonth(Date date, String company, TableType tableType)
    {
    	return load(null, DateHelper.toMonth(date), DateHelper.getYear(date), company, tableType);
    }
    
    private CodeSequence loadStartYear(Date date, String company, TableType tableType)
    {
    	return load(null, null, DateHelper.getYear(date), company, tableType);
    }

	@Override
	public CodeSequence load(Date date, String company, TableType tableType, Long sequence) {
		switch (sequence.intValue()) {
		case 1:
			return loadStartDay(date, company, tableType);
		case 2:
			return loadStartMonth(date, company, tableType);
		default:
			return loadStartYear(date, company, tableType);
		}
	}
}
