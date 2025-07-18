/**
 * File Name  : FundApplicationFilterCriteria.java
 * Created On : Jul 18, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.accountpayable.criteria;

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
public class FundApplicationFilterCriteria extends AbstractFilterCriteria
{
	private static final long serialVersionUID = -6701041325229376103L;

	private String code;
	private String reference;
	private String status;

	private Long organization;
	private Long supplier;

	private Date dateFrom;
	private Date dateTo;
}
