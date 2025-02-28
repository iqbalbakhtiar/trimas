package com.siriuserp.administration.criteria;

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
public class CoreTaxFilterCriteria extends AbstractFilterCriteria 
{
	private static final long serialVersionUID = -4840940037402249077L;

	private String code;
	private String description;
	private String group;
	private String facilityCode;
	private String facilityDescription;
	private String trxCode;
}
