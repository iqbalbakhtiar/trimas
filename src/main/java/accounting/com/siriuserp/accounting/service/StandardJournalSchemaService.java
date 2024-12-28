/**
 * Nov 12, 2008 2:44:38 PM
 * com.siriuserp.accounting.service
 * JournalSchemaService.java
 */
package com.siriuserp.accounting.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accounting.dao.JournalSchemaAccountDao;
import com.siriuserp.accounting.dao.JournalSchemaIndexDao;
import com.siriuserp.accounting.dm.GLAccount;
import com.siriuserp.accounting.dm.IndexType;
import com.siriuserp.accounting.dm.JournalSchema;
import com.siriuserp.accounting.dm.JournalSchemaAccount;
import com.siriuserp.accounting.dm.JournalSchemaIndex;
import com.siriuserp.accounting.dm.StandardJournalSchema;
import com.siriuserp.accounting.query.StandardJournalSchemaGridViewQuery;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.AbstractGridViewQuery;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class StandardJournalSchemaService
{
	@Autowired
	private GenericDao genericDao;

	@Autowired
	private JournalSchemaIndexDao journalSchemaIndexDao;

	@Autowired
	private JournalSchemaAccountDao journalSchemaAccountDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria)
	{
		AbstractGridViewQuery query = new StandardJournalSchemaGridViewQuery();
		query.setFilterCriteria(filterCriteria);

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("journalSchemas", FilterAndPaging.filter(genericDao, query));
		map.put("filterCriteria", filterCriteria);

		if (SiriusValidator.validateParamWithZeroPosibility(filterCriteria.getOrganization()))
			map.put("organization", genericDao.load(Party.class, filterCriteria.getOrganization()));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	@InjectParty(keyName = "journalSchema")
	public FastMap<String, Object> preadd()
	{
		FastMap<String, Object> map = new FastMap<String, Object>();

		StandardJournalSchema journalSchema = new StandardJournalSchema();
		for (IndexType index : genericDao.loadAll(IndexType.class))
		{
			JournalSchemaIndex schemaIndex = new JournalSchemaIndex();
			schemaIndex.setOn(true);
			schemaIndex.setIndex(index);
			schemaIndex.setJournalSchema(journalSchema);

			journalSchema.getIndexes().add(schemaIndex);
		}

		map.put("journalSchema_add", journalSchema);

		return map;
	}

	public FastMap<String, Object> preedit(String id)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();

		StandardJournalSchema journalSchema = load(id);

		for (IndexType index : genericDao.loadAll(IndexType.class))
		{
			JournalSchemaIndex dbIndex = journalSchemaIndexDao.loadByParentAndType(journalSchema.getId(), index.getId());
			if (dbIndex == null)
			{
				JournalSchemaIndex journalSchemaIndex = new JournalSchemaIndex();
				journalSchemaIndex.setIndex(index);
				journalSchemaIndex.setJournalSchema(journalSchema);

				journalSchema.getIndexes().add(journalSchemaIndex);
			}
		}

		map.put("journalSchema_edit", journalSchema);

		return map;
	}

	@AuditTrails(className = JournalSchema.class, actionType = AuditTrailsActionType.CREATE)
	public void add(StandardJournalSchema journalSchema) throws ServiceException
	{
		genericDao.add(journalSchema);

		for (JournalSchemaAccount account : journalSchema.getAccounts())
		{
			account.setAccount(genericDao.load(GLAccount.class, account.getAccount().getId()));
			account.setJournalSchema(journalSchema);

			journalSchemaAccountDao.add(account);
		}
	}

	@AuditTrails(className = JournalSchema.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(StandardJournalSchema journalSchema, Set<JournalSchemaAccount> accounts) throws ServiceException
	{
		for (JournalSchemaAccount account : journalSchema.getAccounts())
			journalSchemaAccountDao.delete(account);

		for (JournalSchemaAccount account : accounts)
		{
			account.setAccount(genericDao.load(GLAccount.class, account.getAccount().getId()));
			account.setJournalSchema(journalSchema);

			JournalSchemaAccount out = journalSchemaAccountDao.load(journalSchema.getId(), account.getAccount().getId(), account.getPostingType());
			if (out == null)
				journalSchemaAccountDao.add(account);
		}

		genericDao.update(journalSchema);
	}

	@AuditTrails(className = JournalSchema.class, actionType = AuditTrailsActionType.DELETE)
	public void delete(StandardJournalSchema journalSchema) throws ServiceException
	{
		genericDao.delete(journalSchema);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public StandardJournalSchema load(String id)
	{
		return genericDao.load(StandardJournalSchema.class, Long.valueOf(id));
	}
}
