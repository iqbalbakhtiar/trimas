/**
 * File Name  : DeliveryPlanningFilterCriteria.java
 * Created On : Mar 6, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.criteria;

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
public class DeliveryPlanningFilterCriteria extends AbstractFilterCriteria
{
	private static final long serialVersionUID = 5822361324464508313L;

	private String code;
	private String customerName;
	private String salesOrderCode;
	private String inProgress = "ALL";

	private Date dateFrom;
	private Date dateTo;
}
