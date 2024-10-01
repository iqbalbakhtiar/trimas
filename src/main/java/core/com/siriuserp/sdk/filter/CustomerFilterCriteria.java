/**
 * Aug 31, 2006 1:50:29 PM
 * net.konsep.sirius.sales.dto.filter
 * CustomerFilterCriteria.java
 */
package com.siriuserp.sdk.filter;

import java.util.Date;

import com.siriuserp.sdk.utility.DateHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Getter
@Setter
@NoArgsConstructor
public class CustomerFilterCriteria extends AbstractFilterCriteria
{
	private static final long serialVersionUID = 2929960869111820097L;

	private String code;
	private String salutation;
	private String name;
	private String sort;
	private String clean;
	private String customer;
	private String company;
	private String status;
	private Date date;

	private Long facility;
	
	private Boolean base;
	private Boolean active;
	
	public String getDateString()
	{
		if (this.date != null)
			return DateHelper.format(getDate());

		return null;
	}
}
