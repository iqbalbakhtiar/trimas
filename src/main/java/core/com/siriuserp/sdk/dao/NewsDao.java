/**
 * Nov 22, 2007 1:47:22 PM
 * com.siriuserp.tools.dao
 * newsDao.java
 */
package com.siriuserp.sdk.dao;

import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.base.Filterable;
import com.siriuserp.sdk.dm.News;

/**
 * @author Ersi Agustin
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public interface NewsDao extends Dao<News>,Filterable
{
    public News loadLatesNews();
}
