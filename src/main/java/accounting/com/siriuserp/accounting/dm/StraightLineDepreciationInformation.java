/**
 * Jan 2, 2008 3:52:10 PM
 * net.konsep.sirius.model
 * StraightLineDepreciationInformation.java
 */
package com.siriuserp.accounting.dm;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.siriuserp.sdk.dm.Model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@Getter
@Setter
@Entity
@Table(name = "straight_line_depreciation_information")
public class StraightLineDepreciationInformation extends Model implements Serializable
{
	private static final long serialVersionUID = -3801730948022696846L;

	@Column(name = "month")
	private BigDecimal month;

	@Column(name = "accumulated")
	private BigDecimal accumulated;

	@Column(name = "book_value")
	private BigDecimal bookValue;

	@Override
	public String getAuditCode()
	{
		return null;
	}
}
