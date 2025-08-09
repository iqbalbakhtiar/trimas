/**
 * File Name  : MachineFilterCriteria.java
 * Created On : Aug 7, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.production.criteria;

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
public class MachineFilterCriteria extends AbstractFilterCriteria
{
	private static final long serialVersionUID = 8036250753966651206L;

	private String code;
	private String name;
	private String status;
}
