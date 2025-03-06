package com.siriuserp.inventory.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;

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
	private String productCategory;
	private String type;
	private String uom;

	private Boolean status;
	private Boolean base;
	private Boolean serial;

	private Long categoryId;
	private Long container;
}
