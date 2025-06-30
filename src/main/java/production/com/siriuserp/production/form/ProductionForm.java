package com.siriuserp.production.form;

import java.util.List;

import com.siriuserp.inventory.dm.UnitOfMeasure;
import com.siriuserp.production.dm.CostCenterGroup;
import com.siriuserp.production.dm.CostCenterType;
import com.siriuserp.production.dm.ProductionCostCenterGroup;
import com.siriuserp.production.dm.ProductionOrder;
import com.siriuserp.sdk.dm.Form;

import javolution.util.FastList;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ferdinand
 */

@Getter
@Setter
public class ProductionForm extends Form
{
	private static final long serialVersionUID = 1199915513205715186L;

	private CostCenterType type;
	private UnitOfMeasure unitOfMeasure;
	private CostCenterGroup costCenterGroup;
	
	private ProductionOrder productionOrder;
//	private ProductionOrderDetail productionOrderDetail;
	
	private String lotNumber;
	
	private List<ProductionCostCenterGroup> costCenterGroupProductions = new FastList<ProductionCostCenterGroup>();
}