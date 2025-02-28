package com.siriuserp.administration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.administration.criteria.CoreTaxFilterCriteria;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.QueryFactory;

import javolution.util.FastMap;

/**
 * @author ferdinand
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class CoreTaxProductService 
{
	@Autowired
	private GenericDao genericDao;
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public FastMap<String,Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws ServiceException
    {
        FastMap<String,Object> map = new FastMap<String, Object>();
        CoreTaxFilterCriteria criteria = (CoreTaxFilterCriteria) filterCriteria;
        
        map.put("products", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));
        map.put("filterCriteria", criteria);
        
        return map;
    }
}
