/**
 * Dec 10, 2007 9:33:50 AM
 * com.siriuserp.sdk.paging
 * FilterAndPaging.java
 */
package com.siriuserp.sdk.paging;

import java.util.List;

import com.siriuserp.sdk.base.Filterable;
import com.siriuserp.sdk.db.GridViewQuery;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@SuppressWarnings("rawtypes")
public class FilterAndPaging
{
	public static List load(Filterable dao, GridViewQuery query)
	{
		int totalData = 0;
        int totalPage = 1;
        int nextPage = 1;
        int prevPage = 1;

        totalData = dao.getMax(query).intValue();
        
        if(totalData > 0)
        {
            if(totalData % query.getFilterCriteria().getMax() == 0)
                totalPage = totalData/query.getFilterCriteria().getMax();
            else
            {
                int sisa = totalData % query.getFilterCriteria().getMax();
                totalPage = ((totalData - sisa) / query.getFilterCriteria().getMax()) + 1;
            }

            if(query.getFilterCriteria().getPage() - 1 > 0)
                prevPage = query.getFilterCriteria().getPage() - 1;

            if((query.getFilterCriteria().getPage() + 1) <= totalPage)
                nextPage = query.getFilterCriteria().getPage() + 1;
            else
                nextPage = totalPage;
        }

        query.getFilterCriteria().setNextPage(nextPage);
        query.getFilterCriteria().setPrevPage(prevPage);
        query.getFilterCriteria().setTotalPage(totalPage);

        return dao.filter(query);
	}

	public static synchronized List filter(Filterable dao, GridViewQuery query)
    {
        return load(dao, query);
    }
}
