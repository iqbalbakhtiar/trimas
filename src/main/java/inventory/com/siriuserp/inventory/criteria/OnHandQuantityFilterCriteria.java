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
	private String serial;

	private Long product;
	private Long container;
	private Long productId;
	private Long containerId;

	private boolean viewByLot = false;
}
