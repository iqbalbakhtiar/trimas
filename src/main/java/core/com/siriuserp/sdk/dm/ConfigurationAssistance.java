/**
 * Nov 12, 2008 11:43:52 AM
 * com.siriuserp.sdk.dm
 * ConfigurationAssistance.java
 */
package com.siriuserp.sdk.dm;

import javax.persistence.CascadeType;
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
@Table(name = "configuration_asistance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ConfigurationAssistance extends Model
{
	private static final long serialVersionUID = 3767422851839816155L;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_display_configuration")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private DisplayConfiguration displayConfiguration;

	@Override
	public String getAuditCode()
	{
		return this.id + "";
	}
}
