/**
 * File Name  : FundApplicationService.java.java
 * Created On : Jul 12, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountpayable.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accountpayable.criteria.APLedgerFilterCriteria;
import com.siriuserp.accountpayable.dm.FundApplication;
import com.siriuserp.accountpayable.dm.FundApplicationItem;
import com.siriuserp.accountpayable.dm.FundApplicationStatus;
import com.siriuserp.accountpayable.form.PayablesForm;
import com.siriuserp.accountpayable.query.FundApplicationAddQuery;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.dm.Party;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.EnglishNumber;
import com.siriuserp.sdk.utility.FormHelper;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;
import com.siriuserp.sdk.utility.UserHelper;

import javolution.util.FastMap;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class FundApplicationService
{
	@Autowired
	private GenericDao genericDao;

	@Autowired
	private CodeSequenceDao codeSequenceDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("filterCriteria", filterCriteria);
		map.put("applications", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preadd1() throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("filterCriteria", new APLedgerFilterCriteria());
		map.put("organization", UserHelper.activeUser().getProfile().getOrganization());

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preadd2(APLedgerFilterCriteria criteria) throws Exception
	{
		FundApplicationAddQuery query = new FundApplicationAddQuery();
		query.setFilterCriteria(criteria);

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("fundApplication_form", new PayablesForm());
		map.put("organization", genericDao.load(Party.class, criteria.getOrganization()));
		map.put("reports", genericDao.generate(query));
		map.put("criteria", criteria);

		return map;
	}

	@AuditTrails(className = FundApplication.class, actionType = AuditTrailsActionType.CREATE)
	public void add(FundApplication fundApplication) throws Exception
	{
		PayablesForm form = (PayablesForm) fundApplication.getForm();

		fundApplication.setCode(GeneratorHelper.instance().generate(TableType.FUND_APPLICATION, codeSequenceDao));

		BigDecimal amount = BigDecimal.ZERO;

		for (Item item : form.getItems())
		{
			if (item.getReference() != null && item.isEnabled())
			{
				FundApplicationItem applicationItem = new FundApplicationItem();
				applicationItem.setSupplier(genericDao.load(Party.class, item.getReference()));
				applicationItem.setAmount(item.getAmount());
				applicationItem.setFundApplication(fundApplication);

				fundApplication.getItems().add(applicationItem);

				amount = amount.add(applicationItem.getAmount());
			}
		}

		fundApplication.setAmount(amount);
		genericDao.add(fundApplication);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id) throws Exception
	{
		FundApplication fundApplication = genericDao.load(FundApplication.class, id);
		PayablesForm form = FormHelper.bind(PayablesForm.class, fundApplication);
		String saidId = EnglishNumber.convertIdComma(form.getFundApplication().getAmount().setScale(2, RoundingMode.HALF_UP));

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("fundApplication_form", form);
		map.put("fundApplication_edit", form.getFundApplication());
		map.put("saidId", WordUtils.capitalizeFully(saidId));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preeditItem(Long id) throws Exception
	{
		FundApplicationItem applicationItem = genericDao.load(FundApplicationItem.class, id);
		String saidId = EnglishNumber.convertIdComma(applicationItem.getAmount().setScale(2, RoundingMode.HALF_UP));

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("applicationItem", applicationItem);
		map.put("bankAccount", applicationItem.getSupplier().getDefaultBankAccount());
		map.put("saidId", WordUtils.capitalizeFully(saidId));

		return map;
	}

	@AuditTrails(className = FundApplication.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(FundApplication fundApplication) throws Exception
	{
		genericDao.update(fundApplication);
	}

	@AuditTrails(className = FundApplication.class, actionType = AuditTrailsActionType.UPDATE)
	public void delete(Long id) throws Exception
	{
		FundApplication fundApplication = genericDao.load(FundApplication.class, id);

		if (fundApplication.getStatus().equals(FundApplicationStatus.NEW))
			genericDao.delete(fundApplication);
	}
}
