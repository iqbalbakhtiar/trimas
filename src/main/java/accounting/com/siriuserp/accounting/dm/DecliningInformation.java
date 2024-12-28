/**
 * Jan 3, 2008 9:51:55 AM
 * net.konsep.sirius.model
 * DecliningInformation.java
 */
package com.siriuserp.accounting.dm;

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
@Table(name = "declining_information")
public class DecliningInformation extends Model
{
	private static final long serialVersionUID = 4105151133657809244L;

	@Column(name = "accumulated")
	private BigDecimal accumulated = BigDecimal.ZERO;

	@Column(name = "book_value")
	private BigDecimal bookValue = BigDecimal.ZERO;

	@Override
	public String getAuditCode()
	{
		return this.id + ",";
	}
}
