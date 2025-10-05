package com.siriuserp.inventory.criteria;

import java.util.List;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;

import javolution.util.FastList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductFilterCriteria extends AbstractFilterCriteria
{
	private static final long serialVersionUID = -7718288990415896562L;

	private String code;
	private String name;
	private String barcode;
	private String packagingCode;
	private String unitOfMeasure;
	private String categoryName;
	private String productCategoryType;
	private String purchaseRequestType;
	private String type;
	private String uom;
	private String serialNo;
	private String excludeCategoryType;
	private String lotCode;
	private String productCategory;
	private String target;

	private Boolean saleable;
	private Boolean status;
	private Boolean base;
	private Boolean serial;

	private boolean onHand = false;
	private boolean availableSales = true;

	private Long productId;
	private Long categoryId;
	private Long facility;
	private Long grid;
	private Long container;
	private Long id;
	
	private int index;

	private List<String> barcodes = new FastList<String>();
}
