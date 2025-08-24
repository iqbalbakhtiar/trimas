/**
 * File Name  : SalesOrderFilterCriteria.java
 * Created On : Mar 6, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.criteria;

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
public class SalesOrderFilterCriteria extends AbstractFilterCriteria
{
	private static final long serialVersionUID = 4812990284661889240L;

	private String code;
	private String customer;
	private String tax;
	private String approver;
	private Long productId;

	private Boolean approved;

	private Date date;
}
