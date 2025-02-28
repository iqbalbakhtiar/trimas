package com.siriuserp.inventory.sibling;

import com.siriuserp.inventory.dm.DWInventoryItemBalance;
import com.siriuserp.inventory.dm.DWInventoryItemBalanceDetail;
import com.siriuserp.inventory.dm.GoodsReceipt;
import com.siriuserp.inventory.dm.GoodsReceiptItem;
import com.siriuserp.inventory.dm.ProductInOutTransaction;
import com.siriuserp.inventory.dm.WarehouseTransactionItem;
import com.siriuserp.inventory.service.GoodsReceiptService;
import com.siriuserp.inventory.util.InventoryBalanceDetailUtil;
import com.siriuserp.inventory.util.InventoryBalanceUtil;
import com.siriuserp.sdk.annotation.AutomaticSibling;
import com.siriuserp.sdk.dm.AbstractSiblingRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Sibling role yang bertanggung jawab untuk menambahkan item inventory ke dalam sistem saat proses goods receipt.
 * <p>
 * Kelas ini dipanggil secara otomatis oleh metode {@link GoodsReceiptService#add(GoodsReceipt)}
 * karena adanya anotasi @{@link AutomaticSibling}.
 * </p>
 */
@Component
public class AddDWInventoryItemInSiblingRole extends AbstractSiblingRole {

    /**
     * Utility untuk memperbarui saldo utama inventory.
     */
    @Autowired
    private InventoryBalanceUtil balanceUtil;

    /**
     * Utility untuk memperbarui detail saldo inventory.
     */
    @Autowired
    private InventoryBalanceDetailUtil balanceDetailUtil;

    /**
     * Mengeksekusi logika sibling role untuk memperbarui saldo inventory berdasarkan goods receipt.
     */
    @Override
    public void execute() throws Exception {
        GoodsReceipt goodsReceipt = (GoodsReceipt) getSiblingable();

        for (GoodsReceiptItem item : goodsReceipt.getAllItems()) {
            if (!item.getInOuts().isEmpty()) {
                WarehouseTransactionItem transactionItem = genericDao.load(
                        WarehouseTransactionItem.class,
                        item.getWarehouseTransactionItem().getId()
                );

                // Memperbarui saldo utama inventory.
                balanceUtil.in(DWInventoryItemBalance.class, item, item.getReceipted());

                // Memperbarui detail saldo inventory untuk setiap transaksi.
                for (ProductInOutTransaction inOut : item.getInOuts()) {
                    balanceDetailUtil.in(
                            DWInventoryItemBalanceDetail.class,
                            item,
                            transactionItem,
                            goodsReceipt,
                            inOut.getQuantity(),
                            inOut.getPrice(),
                            goodsReceipt.getNote()
                    );
                }
            }
        }
    }
}

