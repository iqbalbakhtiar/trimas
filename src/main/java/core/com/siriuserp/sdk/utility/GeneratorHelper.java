/**
 * Jan 3, 2008 3:12:27 PM
 * com.siriuserp.sdk.utility
 * GeneratorHelper.java
 */
package com.siriuserp.sdk.utility;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;

import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dm.CodeSequence;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.dm.Tax;
import com.siriuserp.sdk.exceptions.ServiceException;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
public class GeneratorHelper
{
	private static GeneratorHelper helper = null;

	public GeneratorHelper()
	{
	}

	public CodeSequence create(TableType type, Date date) throws ServiceException
	{
		return create(type, null, date);
	}

	public CodeSequence create(TableType type, String company, Date date) throws ServiceException
	{
		CodeSequence codeSequence = new CodeSequence();

		if (SiriusValidator.validateParam(company))
			codeSequence.setCompany(company);

		codeSequence.setType(type);
		codeSequence.setDay(DateHelper.getDayAsInt(date));
		codeSequence.setMonth(DateHelper.toMonth(date));
		codeSequence.setYear(DateHelper.getYear(date));

		return codeSequence;
	}

	public static synchronized CodeSequence check(TableType type, CodeSequenceDao codeSequenceDao, String code, Date date)
	{
		return codeSequenceDao.load(date, code, type, CodeSequence.YEAR);
	}

	public static synchronized CodeSequence check(TableType type, CodeSequenceDao codeSequenceDao, String code, Date date, Long sequence)
	{
		return codeSequenceDao.load(date, code, type, sequence);
	}

	public static synchronized GeneratorHelper instance()
	{
		if (GeneratorHelper.helper == null)
			GeneratorHelper.helper = new GeneratorHelper();

		return GeneratorHelper.helper;
	}

	public String generate(TableType tableType, CodeSequenceDao codeSequenceDao) throws ServiceException
	{
		return generate(tableType, codeSequenceDao, "", null, null, CodeSequence.YEAR, null);
	}

	public String generate(TableType tableType, CodeSequenceDao codeSequenceDao, String code) throws ServiceException
	{
		return generate(tableType, codeSequenceDao, code, null, null, CodeSequence.YEAR, null);
	}

	public String generate(TableType tableType, CodeSequenceDao codeSequenceDao, Party organization) throws ServiceException
	{
		return generate(tableType, codeSequenceDao, organization.getCode(), null, null, CodeSequence.YEAR, null);
	}

	public String generate(TableType tableType, CodeSequenceDao codeSequenceDao, Party organization, Date date) throws ServiceException
	{
		return generate(tableType, codeSequenceDao, organization.getCode(), null, date, CodeSequence.YEAR, null);
	}

	public String generate(TableType tableType, CodeSequenceDao codeSequenceDao, Date date, Long sequence) throws ServiceException
	{
		return generate(tableType, codeSequenceDao, null, null, date, sequence, null);
	}

	public String generate(TableType tableType, CodeSequenceDao codeSequenceDao, Date date) throws ServiceException
	{
		return generate(tableType, codeSequenceDao, null, null, date, CodeSequence.MONTH, null);
	}

	public String generate(TableType tableType, CodeSequenceDao codeSequenceDao, String code, String codeExt, Date date, Tax tax) throws ServiceException
	{
		return generate(tableType, codeSequenceDao, code, codeExt, date, CodeSequence.MONTH, tax);
	}

	public String generate(TableType tableType, CodeSequenceDao codeSequenceDao, String code, String codeExt, Date date, Long sequence, Tax tax) throws ServiceException
	{
		if (date == null)
			date = DateHelper.today();

		CodeSequence codeSequence = codeSequenceDao.load(date, code, tableType, sequence);
		int index = 0;

		if (codeSequence == null)
			codeSequence = create(tableType, code, date);

		try
		{
			Method method = CodeSequence.class.getMethod("getSequence" + sequence, new Class[]
			{});
			if (method != null)
			{
				Object object = method.invoke(codeSequence, new Object[]
				{});
				if (object != null)
					index = (Integer) object;

				index++;
			}

			Method set = CodeSequence.class.getMethod("setSequence" + sequence, new Class[]
			{ int.class });
			if (set != null)
			{
				set.invoke(codeSequence, new Object[]
				{ index });

				codeSequenceDao.merge(codeSequence);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new ServiceException("Code generation fail for " + tableType, e);
		}

		switch (tableType)
		{
		case BARCODE_PRODUCT:
		case BARCODE_MOVING:
			return barcode(codeSequence, index, date);
		case RECEIVABLES_CONTRA_BON:
		case RECEIVABLES_DEDUCTION:
			return print(codeSequence, index);
		case GOODS_ISSUE_SEQUENCE:
		case MOVING_CONTAINER_ISSUE_SEQUENCE:
			return sequence(codeSequence, index);
		case SALES_ORDER:
			return sales(codeSequence, codeExt, index, date, tax);
		case DELIVERY_ORDER:
		case BILLING:
			return delivery(tableType, codeSequence, index, date);
		case PURCHASE_ORDER:
			return purchase(codeSequence, index, date);
		default:
			return format(codeSequence, index);
		}
	}

	private String format(CodeSequence codeSequence, Integer index)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(codeSequence.getType().getCode());
		sb.append(" ");

		if (SiriusValidator.validateParam(codeSequence.getCompany()))
		{
			sb.append(codeSequence.getCompany());
			sb.append(" - ");
		} else
			sb.append("- ");

		sb.append(String.valueOf(codeSequence.getYear()).substring(2));
		sb.append(" ");

		String sCode = "" + index;

		for (int idx = 0; idx < (codeSequence.getType().getLength() - sCode.trim().length()); idx++)
			sb.append("0");

		sb.append(index);

		return sb.toString();
	}

	private String sequence(CodeSequence codeSequence, Integer index)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(codeSequence.getCompany().split(",")[1]);
		sb.append(".");
		sb.append(index);

		return sb.toString();
	}

	private String print(CodeSequence codeSequence, Integer index)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(codeSequence.getType().getCode());
		sb.append("/");
		sb.append(DateHelper.PRINT.format(DateHelper.today()));
		sb.append(".");

		if (SiriusValidator.validateParam(codeSequence.getCompany()))
			sb.append(codeSequence.getCompany());

		String sCode = "" + index;

		for (int idx = 0; idx < (codeSequence.getType().getLength() - sCode.trim().length()); idx++)
			sb.append("0");

		sb.append(".");
		sb.append(index);

		return sb.toString();
	}

	private String barcode(CodeSequence codeSequence, Integer index, Date date)
	{
		StringBuilder barcode = new StringBuilder();

		if (SiriusValidator.validateParam(codeSequence.getCompany()))
			barcode.append(codeSequence.getCompany());

		barcode.append(codeSequence.getYear());
		barcode.append(DateHelper.getMonth(date));

		String sCode = "" + index;

		for (int idx = 0; idx < (codeSequence.getType().getLength() - sCode.trim().length()); idx++)
			barcode.append("0");

		barcode.append(index);

		return barcode.toString();
	}

	//SEQ/CODEEXT/TAXORNOTAX/MONTH/YEAR EX:001/BNG/SSM/05/2025
	private String sales(CodeSequence codeSequence, String codeExt, Integer index, Date date, Tax tax)
	{
		StringBuffer sb = new StringBuffer();
		String sCode = "" + index;

		for (int idx = 0; idx < (codeSequence.getType().getLength() - sCode.trim().length()); idx++)
			sb.append("0");

		sb.append(index);

		if (SiriusValidator.validateParam(codeExt))
			sb.append("/" + codeExt);

		if (tax != null && tax.getTaxRate().compareTo(BigDecimal.ZERO) > 0)
			sb.append("/SSM");

		sb.append("/" + DateHelper.getMonth(date));
		sb.append("/" + DateHelper.getYear(date));

		return sb.toString();
	}

	//CODE|YEAR|MONTH|SEQ EX:SJ2505001
	private String delivery(TableType tableType, CodeSequence codeSequence, Integer index, Date date)
	{
		StringBuffer sb = new StringBuffer();
		String sCode = "" + index;
		String year = DateHelper.getYear(date) + "";

		sb.append(tableType.getCode());
		sb.append(year.substring(year.length() - 2, year.length()));
		sb.append(DateHelper.getMonth(date));

		for (int idx = 0; idx < (codeSequence.getType().getLength() - sCode.trim().length()); idx++)
			sb.append("0");

		sb.append(index);

		return sb.toString();
	}

	//SEQ/CODEEXT/TAXORNOTAX/MONTH/YEAR EX:001/BNG/SSM/05/2025
	private String purchase(CodeSequence codeSequence, Integer index, Date date)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("PO/");
		String sCode = "" + index;

		for (int idx = 0; idx < (codeSequence.getType().getLength() - sCode.trim().length()); idx++)
			sb.append("0");

		sb.append(index);

		if (codeSequence.getCompany() != null)
			sb.append("/" + codeSequence.getCompany());

		sb.append("/" + DateHelper.toMonthRome(Integer.valueOf(DateHelper.getMonth(date))));
		sb.append("/" + DateHelper.getYear(date));

		return sb.toString();
	}
}
