/**
 * Nov 14, 2008 9:33:26 AM
 * com.siriuserp.sdk.dm
 * DisplayConfiguration.java
 */
package com.siriuserp.sdk.dm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

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
@Table(name = "display_configuration")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DisplayConfiguration extends Model
{
	private static final long serialVersionUID = 6287459844526875260L;

	@Column(name = "row")
	private Integer row;

	@Column(name = "popup_row")
	private Integer popuprows;

	@Column(name = "logo")
	private String logo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_locale")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Locale locale;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_organization_default")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party organization;

	@Override
	public String getAuditCode()
	{
		return this.id + "";
	}
}
