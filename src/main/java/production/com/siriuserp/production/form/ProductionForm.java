/**
 * File Name  : ProductionForm.java
 * Created On : Jul 30, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.production.form;

import com.siriuserp.production.dm.ProductionStatus;
import com.siriuserp.production.dm.WorkOrder;
import com.siriuserp.sdk.dm.Form;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Getter
@Setter
public class ProductionForm extends Form
{
	private static final long serialVersionUID = 1199915513205715186L;

	private WorkOrder workOrder;

	private ProductionStatus productionStatus;
}