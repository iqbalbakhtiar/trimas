package com.siriuserp.inventory.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductFilterCriteria extends AbstractFilterCriteria {
	
	private static final long serialVersionUID = -7718288990415896562L;
	
	private String code;
	private String name;
	private String origin;
	private String brand;
	private String grade;
	private String part;
	private String unitOfMeasure;
	private String productCategory;
	private String type;
	
	private Boolean status;
	private Boolean base;
	
	private Long categoryId;

}
