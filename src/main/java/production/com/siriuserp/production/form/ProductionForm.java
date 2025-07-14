package com.siriuserp.production.form;

import java.math.BigDecimal;
import java.util.List;

import com.siriuserp.inventory.dm.UnitOfMeasure;
import com.siriuserp.production.dm.CostCenterGroup;
import com.siriuserp.production.dm.CostCenterType;
import com.siriuserp.production.dm.Machine;
import com.siriuserp.production.dm.ProductionCostCenterGroup;
import com.siriuserp.production.dm.ProductionOrder;
import com.siriuserp.production.dm.ProductionOrderDetail;
import com.siriuserp.production.dm.ProductionOrderDetailBarcode;
import com.siriuserp.production.dm.ProductionOrderDetailMaterialRequest;
import com.siriuserp.sdk.dm.Container;
import com.siriuserp.sdk.dm.Facility;
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
	private ProductionOrderDetail productionOrderDetail;
	private ProductionOrderDetailBarcode productionOrderDetailBarcode;
	private ProductionOrderDetailMaterialRequest productionOrderDetailMaterialRequest;
	
	private Machine machine;
	private Facility source;
	private Facility destination;
	private Container container;
	
	private String lotNumber;
	private String coneMark;
	private String pic;
	
	private BigDecimal quantity = BigDecimal.ZERO;
	
	private List<ProductionCostCenterGroup> costCenterGroupProductions = new FastList<ProductionCostCenterGroup>();
}