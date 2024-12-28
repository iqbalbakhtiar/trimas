/**
 * Jan 28, 2008 10:26:08 AM
 * com.siriuserp.accounting.service
 * PostingProcess.java
 */
package com.siriuserp.accounting.posting;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import com.siriuserp.accounting.dao.ClosingAccountDao;
import com.siriuserp.accounting.dao.GLAccountBalanceDao;
import com.siriuserp.accounting.dao.GLAccountDao;
import com.siriuserp.accounting.dm.AccountingPeriod;
import com.siriuserp.accounting.dm.GLPostingType;
import com.siriuserp.accounting.dm.JournalEntryConfiguration;
import com.siriuserp.accounting.service.JournalEntryService;
import com.siriuserp.sdk.base.Command;
import com.siriuserp.sdk.dao.CurrencyDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.Currency;
import com.siriuserp.sdk.dm.ExchangeType;
import com.siriuserp.sdk.dm.Party;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

public abstract class PostingProcess implements Command
{
	@Autowired
	protected CurrencyDao currencyDao;

	@Autowired
	protected ClosingAccountDao closingAccountDao;

	@Autowired
	protected JournalEntryService journalEntryService;

	@Autowired
	protected GLAccountBalanceDao accountBalanceDao;

	@Autowired
	protected GenericDao genericDao;

	@Autowired
	protected GLAccountDao accountDao;

	protected AccountingPeriod accountingPeriod;

	protected Party organization;

	protected JournalEntryConfiguration configuration;

	protected Currency defaultCurrency;

	protected ExchangeType defaultExchangeType = ExchangeType.MIDDLE;

	public ExchangeType getDefaultExchangeType()
	{
		return defaultExchangeType;
	}

	public void setDefaultExchangeType(ExchangeType defaultExchangeType)
	{
		this.defaultExchangeType = defaultExchangeType;
	}

	public Currency getDefaultCurrency()
	{
		return defaultCurrency;
	}

	public void setDefaultCurrency(Currency defaultCurrency)
	{
		this.defaultCurrency = defaultCurrency;
	}

	public AccountingPeriod getAccountingPeriod()
	{
		return accountingPeriod;
	}

	public void setAccountingPeriod(AccountingPeriod accountingPeriod)
	{
		this.accountingPeriod = accountingPeriod;
	}

	public JournalEntryConfiguration getConfiguration()
	{
		return configuration;
	}

	public void setConfiguration(JournalEntryConfiguration configuration)
	{
		this.configuration = configuration;
	}

	public final GLPostingType toPostingType(GLPostingType compare1, GLPostingType compare2)
	{
		if (compare1.equals(GLPostingType.CREDIT) && compare2.equals(GLPostingType.CREDIT))
			return GLPostingType.CREDIT;
		else if (compare1.equals(GLPostingType.CREDIT) && compare2.equals(GLPostingType.DEBET))
			return GLPostingType.DEBET;
		else if (compare1.equals(GLPostingType.DEBET) && compare2.equals(GLPostingType.CREDIT))
			return GLPostingType.CREDIT;
		else
			return GLPostingType.DEBET;
	}

	public final GLPostingType reverse(GLPostingType type)
	{
		switch (type)
		{
		case CREDIT:
			return GLPostingType.DEBET;

		default:
			return GLPostingType.CREDIT;
		}
	}

	public final GLPostingType reverse(BigDecimal amount)
	{
		if (amount.compareTo(BigDecimal.valueOf(0)) < 0)
			return GLPostingType.DEBET;
		else
			return GLPostingType.CREDIT;
	}

	public final GLPostingType toPostingType(BigDecimal amount)
	{
		if (amount.compareTo(BigDecimal.valueOf(0)) < 0)
			return GLPostingType.CREDIT;
		else
			return GLPostingType.DEBET;
	}

	public final GLPostingType toPostingType(GLPostingType type, BigDecimal amount)
	{
		switch (type)
		{
		case CREDIT:
			if (amount.compareTo(BigDecimal.valueOf(0)) < 0)
				return GLPostingType.DEBET;
			else
				return GLPostingType.CREDIT;
		case DEBET:
			if (amount.compareTo(BigDecimal.valueOf(0)) < 0)
				return GLPostingType.CREDIT;
			else
				return GLPostingType.DEBET;
		}

		return GLPostingType.DEBET;
	}

	public final GLPostingType toPostingType(GLPostingType type, BigDecimal debet, BigDecimal credit)
	{
		if (type.equals(GLPostingType.CREDIT))
		{
			BigDecimal amount = credit.subtract(debet);

			if (amount.compareTo(BigDecimal.valueOf(0)) < 0)
				return GLPostingType.DEBET;
			else
				return GLPostingType.CREDIT;
		} else
		{
			BigDecimal amount = debet.subtract(credit);

			if (amount.compareTo(BigDecimal.valueOf(0)) < 0)
				return GLPostingType.CREDIT;
			else
				return GLPostingType.DEBET;
		}
	}

	public BigDecimal toAmount(GLPostingType type, BigDecimal debet, BigDecimal credit)
	{
		if (type.equals(GLPostingType.DEBET))
			return debet.subtract(credit);
		else
			return credit.subtract(debet);
	}

	public Party getOrganization()
	{
		return organization;
	}

	public void setOrganization(Party organization)
	{
		this.organization = organization;
	}

	public void init(AccountingPeriod accountingPeriod, Party organization)
	{
		setDefaultCurrency(currencyDao.loadDefaultCurrency());
		setAccountingPeriod(accountingPeriod);
		setOrganization(organization);

		JournalEntryConfiguration configuration = genericDao.load(JournalEntryConfiguration.class, Long.valueOf(1));
		if (configuration != null)
		{
			setConfiguration(configuration);
			setDefaultExchangeType(configuration.getExchangeType());
		}
	}
}
