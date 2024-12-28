/**
 * Nov 24, 2008 10:52:42 AM
 * com.siriuserp.sdk.dm
 * ClosingDistributionDestination.java
 */
package com.siriuserp.accounting.dm;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.siriuserp.sdk.dm.Model;
import com.siriuserp.sdk.dm.Party;

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
@Table(name = "closing_distribution_destination")
public class ClosingDistributionDestination extends Model
{
	private static final long serialVersionUID = -7618137692930582864L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_organization")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party organization;

	@Column(name = "rate")
	private BigDecimal rate = BigDecimal.ZERO;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_closing_distribution")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private ClosingDistribution closingDistribution;

	@Override
	public String getAuditCode()
	{
		return this.id + "";
	}
}
