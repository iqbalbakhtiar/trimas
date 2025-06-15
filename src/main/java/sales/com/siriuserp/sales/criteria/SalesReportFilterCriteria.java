/**
 * File Name  : SalesReportFilterCriteria.java
 * Created On : Apr 30, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.criteria;

import java.util.Date;

import com.siriuserp.sdk.filter.AbstractReportFilterCriteria;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Getter
@Setter
public class SalesReportFilterCriteria extends AbstractReportFilterCriteria
{
	private static final long serialVersionUID = 2198093229565553383L;

	private Long facility;
	private Long customer;

	private String code;
	private String salesOrderCode;
	private String salesInternalType;
	private String status;

	private Boolean taxReport;
	private Boolean showBale = false;

	private Date dateFrom;
	private Date dateTo;
}
