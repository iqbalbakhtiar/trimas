package com.siriuserp.production.form;

import com.siriuserp.inventory.dm.UnitOfMeasure;
import com.siriuserp.production.dm.CostCenterGroup;
import com.siriuserp.production.dm.CostCenterType;
import com.siriuserp.sdk.dm.Form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CostCenterForm extends Form {

	private static final long serialVersionUID = -1116229667455960263L;
	
	private CostCenterType type;
	private UnitOfMeasure unitOfMeasure;
	private CostCenterGroup costCenterGroup;
	
}
