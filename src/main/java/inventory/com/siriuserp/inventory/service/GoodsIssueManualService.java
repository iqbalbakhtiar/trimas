package com.siriuserp.inventory.service;

import com.siriuserp.inventory.dm.GoodsIssueManual;
import com.siriuserp.inventory.dm.GoodsIssueManualItem;
import com.siriuserp.inventory.dm.GoodsIssueManualType;
import com.siriuserp.inventory.dm.WarehouseTransactionType;
import com.siriuserp.inventory.form.InventoryForm;
import com.siriuserp.sdk.annotation.AuditTrails;
import com.siriuserp.sdk.annotation.AuditTrailsActionType;
import com.siriuserp.sdk.annotation.AutomaticSibling;
import com.siriuserp.sdk.annotation.InjectParty;
import com.siriuserp.sdk.dao.CodeSequenceDao;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.db.GridViewQuery;
import com.siriuserp.sdk.dm.Item;
import com.siriuserp.sdk.dm.TableType;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;
import com.siriuserp.sdk.paging.FilterAndPaging;
import com.siriuserp.sdk.utility.FormHelper;
import com.siriuserp.sdk.utility.GeneratorHelper;
import com.siriuserp.sdk.utility.QueryFactory;
import com.siriuserp.sdk.utility.ReferenceItemHelper;
import com.siriuserp.sdk.utility.SiriusValidator;
import javolution.util.FastMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Component
@Transactional(rollbackFor = Exception.class)
public class GoodsIssueManualService {
    @Autowired
    private CodeSequenceDao codeSequenceDao;

    @Autowired
    private GenericDao genericDao;

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> view(GridViewFilterCriteria filterCriteria, Class<? extends GridViewQuery> queryclass) throws Exception
    {
        FastMap<String, Object> map = new FastMap<String, Object>();
        map.put("filterCriteria", filterCriteria);
        map.put("issues", FilterAndPaging.filter(genericDao, QueryFactory.create(filterCriteria, queryclass)));
        map.put("types", GoodsIssueManualType.values());

        return map;
    }

    @InjectParty(keyName = "transaction_form")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> preadd() throws ServiceException
    {
        FastMap<String, Object> map = new FastMap<String, Object>();
        map.put("transaction_form", new InventoryForm());
        map.put("types", GoodsIssueManualType.values());

        return map;
    }

    @AuditTrails(className = GoodsIssueManual.class, actionType = AuditTrailsActionType.CREATE)
    @AutomaticSibling(roles= {"DelInventorySiblingRole"})
//    @AutomaticReverseSibling(roles = "GoodsReceiptManualGenerateBarcodeSiblingRole")
    public void add(GoodsIssueManual goodsIssue) throws Exception {
        InventoryForm form = (InventoryForm) goodsIssue.getForm();

        goodsIssue.setCode(GeneratorHelper.instance().generate(TableType.GOODS_ISSUE_MANUAL, codeSequenceDao, goodsIssue.getOrganization()));

        for (Item item : form.getItems()) {
            if (SiriusValidator.gz(item.getQuantity())) {
                GoodsIssueManualItem goodsIssueItem = new GoodsIssueManualItem();
                goodsIssueItem.setProduct(item.getProduct());
                goodsIssueItem.setIssued(item.getQuantity());
                goodsIssueItem.setGoodsIssueManual(goodsIssue);
                goodsIssueItem.getLot().setSerial(item.getSerial());
                goodsIssueItem.setFacilitySource(goodsIssue.getSource());
                goodsIssueItem.setTransactionItem(ReferenceItemHelper.init(genericDao, item.getQuantity(), WarehouseTransactionType.OUT, goodsIssueItem));

                goodsIssueItem.setSourceGrid(item.getSource().getGrid());
                goodsIssueItem.setSourceContainer(item.getSource());

                goodsIssue.getItems().add(goodsIssueItem);
            }
        }

        genericDao.add(goodsIssue);
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> preedit(Long id) throws Exception {
        GoodsIssueManual goodsIssue = genericDao.load(GoodsIssueManual.class, id);

        FastMap<String, Object> map = new FastMap<String, Object>();
        InventoryForm form = FormHelper.bind(InventoryForm.class, goodsIssue);
        map.put("transaction_form", form);
        map.put("transaction_edit", goodsIssue);

        return map;
    }

    @AuditTrails(className = GoodsIssueManual.class, actionType = AuditTrailsActionType.UPDATE)
	public void edit(GoodsIssueManual goodsIssue) throws Exception {
		genericDao.update(goodsIssue);
	}
}
