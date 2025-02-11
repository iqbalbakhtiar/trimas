package com.siriuserp.inventory.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter	
@NoArgsConstructor
public class MasterDataFilterCriteria extends AbstractFilterCriteria
{
	private static final long serialVersionUID = -7718288990415896562L;

	private String code;
	private String name;
	private String type;
	private String note;

	private Boolean status;

}
