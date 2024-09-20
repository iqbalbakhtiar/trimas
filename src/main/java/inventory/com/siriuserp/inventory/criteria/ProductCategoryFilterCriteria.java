package com.siriuserp.inventory.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductCategoryFilterCriteria extends AbstractFilterCriteria {

	private static final long serialVersionUID = -1980746792202760331L;

	private String name;
	private String code;

	private String type;
	private String status;
}
