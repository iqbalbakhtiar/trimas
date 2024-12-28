/**
 * Nov 18, 2008 4:47:16 PM
 * com.siriuserp.accounting.service
 * JournalSchemaAccountService.java
 */
package com.siriuserp.accounting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accounting.dao.JournalSchemaAccountDao;
import com.siriuserp.accounting.query.JournalSchemaAccountGridViewQuery;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.QueryFactory;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class JournalSchemaAccountService
{
	@Autowired
	private JournalSchemaAccountDao journalSchemaAccountDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria) throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("accounts", FilterAndPaging.filter(journalSchemaAccountDao, QueryFactory.create(filterCriteria, JournalSchemaAccountGridViewQuery.class)));
		map.put("filterCriteria", filterCriteria);

		return map;
	}
}
