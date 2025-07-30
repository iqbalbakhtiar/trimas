/**
 * 
 */
package com.siriuserp.inventory.criteria;

import java.util.Date;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ferdinand
 */

@Getter
@Setter
public class StockAdjustmentFilterCriteria extends AbstractFilterCriteria
{
	private static final long serialVersionUID = 7102489002882121613L;

	private String organizationName;
	private String facilityName;
	private String code;

	private Long facility;

	private Date dateFrom;
	private Date dateTo;
}
