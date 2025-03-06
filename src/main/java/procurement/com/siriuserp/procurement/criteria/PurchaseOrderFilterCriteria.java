/**
 * File Name  : PurchaseOrderFilterCriteria.java
 * Created On : Feb 22, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.procurement.criteria;

import java.util.Date;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Getter
@Setter
@NoArgsConstructor
public class PurchaseOrderFilterCriteria extends AbstractFilterCriteria
{
	private static final long serialVersionUID = -3802351065802161093L;

	private String code;
	private String supplierName;
	private String tax;
	private String approver;
	private String approvalDecisionStatus;
	private String billToAddress;
	private String shipToFacility;

	private Date dateFrom;
	private Date dateTo;

}
