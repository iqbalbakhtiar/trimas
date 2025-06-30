package com.siriuserp.inventory.service;

import java.math.RoundingMode;
import java.util.HashSet;

import org.apache.commons.lang.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accounting.form.AccountingForm;
import com.siriuserp.accountreceivable.dm.BillingBatch;
import com.siriuserp.accountreceivable.dm.BillingBatchItem;
import com.siriuserp.accountreceivable.dm.BillingItem;
import com.siriuserp.sales.dm.DeliveryOrderRealization;
import com.siriuserp.sales.dm.DeliveryOrderRealizationItem;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.base.Service;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.CurrencyDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.dm.PostalAddress;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.EnglishNumber;
import com.siriuserp.sdk.utility.FormHelper;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;

import javolution.util.FastMap;

@Component
@Transactional(rollbackFor = Exception.class)
public class BillingBatchService extends Service
{
	@Autowired
	private GenericDao genericDao;

	@Autowired
	private CodeSequenceDao codeSequenceDao;

	@Autowired
	private CurrencyDao currencyDao;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws Exception
	{
		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("filterCriteria", filterCriteria);
		map.put("batches", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));

		return map;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	@InjectParty(keyName = "billing_batch_form")
	public FastMap<String, Object> preadd() throws ServiceException
	{
		FastMap<String, Object> map = new FastMap<String, Object>();

		map.put("billing_batch_form", new AccountingForm());

		return map;
	}

	@AuditTrails(className = BillingBatch.class, actionType = AuditTrailsActionType.CREATE)
	public void add(BillingBatch billBatch) throws Exception
	{
		AccountingForm form = (AccountingForm) billBatch.getForm();

		billBatch.setCode(GeneratorHelper.instance().generate(TableType.BILLING_BATCH, codeSequenceDao));

		for (Item item : form.getItems())
		{
			if (item.getBilling() != null && item.isEnabled())
			{
				BillingBatchItem contraBonItem = new BillingBatchItem();
				contraBonItem.setBilling(item.getBilling());
				contraBonItem.setAmount(item.getBilling().getUnpaid());
				contraBonItem.setBillingBatch(billBatch);

				billBatch.getItems().add(contraBonItem);
			}
		}

		genericDao.add(billBatch);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public FastMap<String, Object> preedit(Long id) throws Exception
	{
		BillingBatch billingBatch = genericDao.load(BillingBatch.class, id);
		AccountingForm form = FormHelper.bind(AccountingForm.class, billingBatch);

		// Get first active customer address, filter by active / enabled
		// Not filter by "selected" / "default" because data is always false
		PostalAddress customerAddress = billingBatch.getCustomer().getPostalAddresses().stream().filter(PostalAddress::isEnabled).findFirst().orElse(null);

		FastMap<String, Object> map = new FastMap<String, Object>();
		map.put("billing_batch_form", form);
		map.put("billing_batch_edit", form.getBillingBatch());
		map.put("customerAddress", customerAddress);
		map.put("defaultCurrency", currencyDao.loadDefaultCurrency());

		// Find DO Code for Print Out
		HashSet<String> references = new HashSet<String>();
		for (BillingBatchItem item : billingBatch.getItems())
		{
			for (BillingItem billingItem : item.getBilling().getItems())
			{
				if (billingItem.getBillingReferenceItem().getReferenceId() != null)
				{
					DeliveryOrderRealization dor = genericDao.load(DeliveryOrderRealization.class, billingItem.getBillingReferenceItem().getReferenceId());
					for (DeliveryOrderRealizationItem dorItem : dor.getItems())
					{
						if (dorItem.getDeliveryOrderItem().getDeliveryOrder().getCode() != null && !dorItem.getDeliveryOrderItem().getDeliveryOrder().getCode().isEmpty())
							references.add(dorItem.getDeliveryOrderItem().getDeliveryOrder().getCode());
					}
				}
			}
		}

		String saidId = EnglishNumber.convertIdComma(form.getBillingBatch().getAmount().setScale(2, RoundingMode.HALF_UP));
		map.put("references", String.join(", ", references));
		map.put("saidId", WordUtils.capitalizeFully(saidId));

		return map;
	}

	@AuditTrails(className = BillingBatch.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(BillingBatch billBatch) throws Exception
	{
		genericDao.update(billBatch);
	}
}
