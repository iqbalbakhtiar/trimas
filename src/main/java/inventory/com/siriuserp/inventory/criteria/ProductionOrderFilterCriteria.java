package com.siriuserp.inventory.criteria;

import java.util.Date;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ferdinand
 */

@Getter
@Setter
@NoArgsConstructor
public class ProductionOrderFilterCriteria extends AbstractFilterCriteria 
{
	private static final long serialVersionUID = -1300983886575370697L;

	private String code;
	private String lotNumber;
	private String status;
	
	private Date dateFrom;
    private Date dateTo;
}
