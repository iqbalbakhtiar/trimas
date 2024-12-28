/**
 * Mar 31, 2009 2:53:16 PM
 * com.siriuserp.accounting.service
 * ProductCategoryAccountingShemaService.java
 */
package com.siriuserp.accounting.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accounting.dao.ClosingAccountTypeDao;
import com.siriuserp.accounting.dao.ProductCategoryAccountingSchemaDao;
import com.siriuserp.accounting.dao.ProductCategoryClosingAccountDao;
import com.siriuserp.accounting.dm.AccountingSchema;
import com.siriuserp.accounting.dm.ClosingAccount;
import com.siriuserp.accounting.dm.ClosingAccountType;
import com.siriuserp.accounting.dm.GroupType;
import com.siriuserp.accounting.dm.ProductCategoryAccountingSchema;
import com.siriuserp.accounting.dm.ProductCategoryClosingAccount;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.exceptions.ServiceException;

import javolution.util.FastMap;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class ProductCategoryAccountingSchemaService
{
	@Autowired
	private ClosingAccountTypeDao closingAccountTypeDao;

	@Autowired
	private ProductCategoryClosingAccountDao productCategoryClosingAccountDao;

	@Autowired
	private ProductCategoryAccountingSchemaDao productCategoryAccountingSchemaDao;

	@Autowired
	private GenericDao genericDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> preadd(String parent)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();

		ProductCategoryAccountingSchema schema = new ProductCategoryAccountingSchema();
		schema.setAccountingSchema(genericDao.load(AccountingSchema.class, Long.valueOf(parent)));
		for (ClosingAccountType type : closingAccountTypeDao.loadAll(GroupType.PRD_SALES))
		{
			ProductCategoryClosingAccount account = new ProductCategoryClosingAccount();

			ClosingAccount closingAccount = new ClosingAccount();
			closingAccount.setClosingAccountType(type);

			account.setClosingAccount(closingAccount);
			account.setSchema(schema);

			schema.getAccounts().add(account);
		}

		for (ClosingAccountType type : closingAccountTypeDao.loadAll(GroupType.PRD_PROCUREMENT))
		{
			ProductCategoryClosingAccount account = new ProductCategoryClosingAccount();

			ClosingAccount closingAccount = new ClosingAccount();
			closingAccount.setClosingAccountType(type);

			account.setClosingAccount(closingAccount);
			account.setSchema(schema);

			schema.getAccounts().add(account);
		}

		for (ClosingAccountType type : closingAccountTypeDao.loadAll(GroupType.PRODUCT))
		{
			ProductCategoryClosingAccount account = new ProductCategoryClosingAccount();

			ClosingAccount closingAccount = new ClosingAccount();
			closingAccount.setClosingAccountType(type);

			account.setClosingAccount(closingAccount);
			account.setSchema(schema);

			schema.getAccounts().add(account);
		}

		map.put("categorySchema", schema);

		return map;
	}

	public Map<String, Object> preedit(String id)
	{
		FastMap<String, Object> map = new FastMap<String, Object>();

		ProductCategoryAccountingSchema schema = load(id);

		for (ClosingAccountType type : closingAccountTypeDao.loadAll(GroupType.PRD_SALES))
		{
			ProductCategoryClosingAccount account = productCategoryClosingAccountDao.loadBySchemaAndAccountType(schema.getId(), type.getId());
			if (account == null)
			{
				account = new ProductCategoryClosingAccount();

				ClosingAccount closingAccount = new ClosingAccount();
				closingAccount.setClosingAccountType(type);

				account.setClosingAccount(closingAccount);
				account.setSchema(schema);

				schema.getAccounts().add(account);
			}
		}

		for (ClosingAccountType type : closingAccountTypeDao.loadAll(GroupType.PRD_PROCUREMENT))
		{
			ProductCategoryClosingAccount account = productCategoryClosingAccountDao.loadBySchemaAndAccountType(schema.getId(), type.getId());
			if (account == null)
			{
				account = new ProductCategoryClosingAccount();

				ClosingAccount closingAccount = new ClosingAccount();
				closingAccount.setClosingAccountType(type);

				account.setClosingAccount(closingAccount);
				account.setSchema(schema);

				schema.getAccounts().add(account);
			}
		}

		for (ClosingAccountType type : closingAccountTypeDao.loadAll(GroupType.PRODUCT))
		{
			ProductCategoryClosingAccount account = productCategoryClosingAccountDao.loadBySchemaAndAccountType(schema.getId(), type.getId());
			if (account == null)
			{
				account = new ProductCategoryClosingAccount();

				ClosingAccount closingAccount = new ClosingAccount();
				closingAccount.setClosingAccountType(type);

				account.setClosingAccount(closingAccount);
				account.setSchema(schema);

				schema.getAccounts().add(account);
			}
		}

		map.put("categorySchema", schema);

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public ProductCategoryAccountingSchema load(String id)
	{
		return genericDao.load(ProductCategoryAccountingSchema.class, Long.valueOf(id));
	}

	@AuditTrails(className = ProductCategoryAccountingSchema.class, actionType = AuditTrailsActionType.CREATE)
	public void add(ProductCategoryAccountingSchema schema) throws ServiceException
	{
		productCategoryAccountingSchemaDao.add(schema);

		AccountingSchema accountingSchema = genericDao.load(AccountingSchema.class, schema.getAccountingSchema().getId());

		for (ProductCategoryAccountingSchema product : accountingSchema.getProducts())
			if (product.getId() != schema.getId() && product.getCategory().getId() == schema.getCategory().getId())
				throw new ServiceException("Duplicate product category !");
	}

	@AuditTrails(className = ProductCategoryAccountingSchema.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(ProductCategoryAccountingSchema schema) throws ServiceException
	{
		try
		{
			productCategoryAccountingSchemaDao.update(schema);
		} catch (Exception e)
		{
			throw new ServiceException(e.getMessage(), e);
		}
	}
}
