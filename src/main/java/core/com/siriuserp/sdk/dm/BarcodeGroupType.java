package com.siriuserp.sdk.dm;

import lombok.Getter;

/**
 * @author Rama Almer Felix
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Getter
public enum BarcodeGroupType {
    PRODUCTION("Production"),
    STOCK_ADJUSTMENT("Stock Adjustment"),
    PURCHASE_ORDER("Purchase Order");

    private final String normalizedName;

    BarcodeGroupType(String normalizedName) {
        this.normalizedName = normalizedName;
    }

}
