/**
 * File Name  : BillingReportFilterCriteria.java
 * Created On : Jul 19, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountreceivable.criteria;

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
public class BillingReportFilterCriteria extends AbstractReportFilterCriteria
{
	private static final long serialVersionUID = -151985480534300014L;

	private Long facility;
	private Long customer;

	private Date dateFrom;
	private Date dateTo;
}
