/**
 * File Name  : AdministrationForm.java
 * Created On : Jul 22, 2023
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.administration.form;

import java.math.BigDecimal;
import java.util.List;

import com.siriuserp.sdk.dm.FacilityImplementation;
import com.siriuserp.sdk.dm.FacilityType;
import com.siriuserp.sdk.dm.Form;
import com.siriuserp.sdk.dm.Item;

import javolution.util.FastList;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Getter
@Setter
public class AdministrationForm extends Form
{
	private static final long serialVersionUID = -2227887206962969112L;

	private FacilityType facilityType;
	private FacilityImplementation implementation;

	private BigDecimal chargedToCompany;
	private BigDecimal paidToExpedition;
	private BigDecimal subsidy;
	private BigDecimal chargedAmount;

	private boolean active = true;

	private final List<Item> types = new FastList<Item>();
}
