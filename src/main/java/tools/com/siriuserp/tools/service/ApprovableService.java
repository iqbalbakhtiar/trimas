package com.siriuserp.tools.service;

import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.QueryFactory;
import javolution.util.FastMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Component
@Transactional(rollbackFor = Exception.class)
public class ApprovableService {

    @Autowired
    private GenericDao genericDao;

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws Exception {
        System.out.println("approvables service");
        FastMap<String, Object> map = new FastMap<String, Object>();
        map.put("filterCriteria", filterCriteria);
        map.put("approvables", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));

        return map;
    }
}
