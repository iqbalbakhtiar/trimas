/**
 * 
 */
package com.siriuserp.inventory.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ferdinand
 */

@Getter
@Setter
public class OnHandQuantityFilterCriteria extends AbstractFilterCriteria
{
	private static final long serialVersionUID = -442419731297599613L;

	private String code;
	private String name;
	private String type;
	private String productCategory;
	private String uom;
	private String lotCode;

	private Long product;
	private Long container;

	private boolean viewByLot = false;
}
