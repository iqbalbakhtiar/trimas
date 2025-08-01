/**
 * File Name  : WorkOrderFilterCriteria.java
 * Created On : Jul 30, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.production.criteria;

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
public class WorkOrderFilterCriteria extends AbstractFilterCriteria
{
	private static final long serialVersionUID = -6546637979931072731L;

	private String code;
	private String operatorName;
	private String approverName;
	private String productionStatus;

	private Date dateFrom;
	private Date dateTo;
}
