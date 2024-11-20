package com.siriuserp.inventory.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;
import javolution.util.FastList;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InventoryItemFilterCriteria extends AbstractFilterCriteria {
    private static final long serialVersionUID = 5907744269887499586L;

    private String name;
    private String code;
    private String category;
    private String barcode;
    private String lot;
    private String ref;
    private String type;
    private String info;

    private boolean reserved  = false;
    private boolean transfer = false;
    private boolean converted = false;

    private Long id;
    private Long grid;
    private Long container;
    private Long purchasePlan;
    private Long product;
    private Long facility;
    private Long productCategory;
    private Long priceCategory;

    private int printRow;
    private int printSize;

    private List<Long> grids = new FastList<Long>();
}
