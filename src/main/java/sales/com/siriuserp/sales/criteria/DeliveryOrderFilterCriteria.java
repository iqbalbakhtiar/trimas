/**
 * File Name  : DeliveryOrderFilterCriteria.java
 * Created On : Mar 18, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sales.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Getter
@Setter
@NoArgsConstructor
public class DeliveryOrderFilterCriteria extends AbstractFilterCriteria
{
	private static final long serialVersionUID = -6328872532983743382L;

	private String code;
	private String customer;
	private String driver;
	private String vehicle;
	private String facility;
	private String shippingAddress;
	private String status;

	private Date date;
}
