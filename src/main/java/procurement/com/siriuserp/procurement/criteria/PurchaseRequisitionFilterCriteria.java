/**
 * File Name  : PurchaseRequisitionFilterCriteria.java
 * Created On : Feb 21, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.procurement.criteria;

import java.util.Date;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Getter
@Setter
public class PurchaseRequisitionFilterCriteria extends AbstractFilterCriteria
{
	private static final long serialVersionUID = -4788701818034665363L;

	private String code;
	private String requisitionerName;
	private String productName;
	private String productCategoryName;

	private Date dateFrom;
	private Date dateTo;
}
