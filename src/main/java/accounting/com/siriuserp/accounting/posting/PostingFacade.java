/**
 * Nov 28, 2008 3:22:25 PM
 * com.siriuserp.accounting.service.posting
 * PostingFacade.java
 */
package com.siriuserp.accounting.posting;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accounting.dao.AccountingPeriodDao;
import com.siriuserp.accounting.dao.AccountingSchemaDao;
import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.accounting.dm.AccountingSchema;
import com.siriuserp.accounting.dm.PeriodStatus;
import com.siriuserp.sdk.dao.CompanyStructureDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.exceptions.ServiceException;

import javolution.util.FastList;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class PostingFacade
{
	@Autowired
	private ForexProcess forexProcess;

	@Autowired
	private CapitalProcess capitalProcess;

	@Autowired
	private OpeningProcess openingProcess;

	@Autowired
	private FixedAssetProcess fixedAssetProcess;

	@Autowired
	private IncomeSummaryProcess incomeSummaryProcess;

	@Autowired
	private CompanyStructureDao companyStructureDao;

	@Autowired
	private AccountingPeriodDao accountingPeriodDao;

	@Autowired
	private DistributionProcess distributionProcess;

	@Autowired
	private ReverseClosingProcess reverseProcess;

	@Autowired
	private AccountingSchemaDao accountingSchemaDao;

	@Autowired
	private GenericDao genericDao;

	public void preclose(String id) throws ServiceException
	{
		AccountingPeriod target = genericDao.load(AccountingPeriod.class, Long.valueOf(id));
		if (target == null)
			throw new ServiceException("Accounting Period does not exist!");

		AccountingPeriod prev = accountingPeriodDao.loadPrev(target);

		if (prev != null && !prev.getStatus().equals(PeriodStatus.CLOSED))
			throw new ServiceException("Previous Accounting Period not yet closed, please close it first!");

		List<Party> organizations = new FastList<Party>();
		organizations.add(target.getOrganization());
		organizations.addAll(companyStructureDao.loadAllVerticalDown(target.getOrganization()));

		for (Party organization : organizations)
		{
			AccountingSchema accountingSchema = accountingSchemaDao.load(organization);
			if (accountingSchema != null)
			{
				forexProcess.init(target, organization);
				forexProcess.execute();

				fixedAssetProcess.init(target, organization);
				fixedAssetProcess.execute();

				distributionProcess.init(target, organization);
				distributionProcess.execute();
			}
		}

		accountingPeriodDao.changeStatus(target, PeriodStatus.PRECLOSE);
		accountingPeriodDao.openStatusNextPeriod(target);
	}

	public void close(String id) throws ServiceException
	{
		AccountingPeriod target = genericDao.load(AccountingPeriod.class, Long.valueOf(id));
		if (target == null)
			throw new ServiceException("Accounting Period does not exist!");

		AccountingPeriod prev = accountingPeriodDao.loadPrev(target);
		if (prev != null && !prev.getStatus().equals(PeriodStatus.CLOSED))
			throw new ServiceException("Previous Accounting Period not yet closed, please close it first!");

		List<Party> organizations = new FastList<Party>();
		organizations.add(target.getOrganization());
		organizations.addAll(companyStructureDao.loadAllVerticalDown(target.getOrganization()));

		for (Party organization : organizations)
		{
			AccountingSchema accountingSchema = accountingSchemaDao.load(organization);
			if (accountingSchema != null)
			{
				incomeSummaryProcess.init(target, organization);
				incomeSummaryProcess.execute();

				capitalProcess.init(target, organization);
				capitalProcess.execute();

				openingProcess.init(target, organization);
				openingProcess.setNextPeriod(accountingPeriodDao.loadNext(target));
				openingProcess.execute();
			}
		}

		accountingPeriodDao.changeStatus(target, PeriodStatus.CLOSED);
		accountingPeriodDao.openStatusNextPeriod(target);
	}

	public void open(String id) throws ServiceException
	{
		//this is the actual target to be re-opened
		AccountingPeriod target = genericDao.load(AccountingPeriod.class, Long.valueOf(id));

		if (target == null)
			throw new ServiceException("Accounting Period does not exist!");

		AccountingPeriod next = accountingPeriodDao.loadNext(target);
		if (next != null && (next.getStatus().equals(PeriodStatus.PRECLOSE) || next.getStatus().equals(PeriodStatus.CLOSED)))
			throw new ServiceException("Next Accounting Period Already Closed,Please re-open it first!");

		doOpen(target, next);

		accountingPeriodDao.changeStatus(target, PeriodStatus.OPEN);
	}

	private void doOpen(AccountingPeriod accountingPeriod, AccountingPeriod next) throws ServiceException
	{
		List<Party> organizations = companyStructureDao.loadAllVerticalDown(accountingPeriod.getOrganization());
		organizations.add(accountingPeriod.getOrganization());

		for (Party organization : organizations)
		{
			AccountingSchema accountingSchema = accountingSchemaDao.load(organization);
			if (accountingSchema != null)
			{
				reverseProcess.init(accountingPeriod, organization);
				reverseProcess.setNext(next);
				reverseProcess.execute();
			}
		}
	}
}
