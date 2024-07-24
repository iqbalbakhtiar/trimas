/**
 * Mar 29, 2007 11:36:43 AM
 * net.konsep.sirius.model
 * ActivityHistory.java
 */
package com.siriuserp.sdk.dm;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.siriuserp.sdk.annotation.AuditTrailsActionType;

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
@Table(name = "user_activity_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ActivityHistory implements Serializable
{
	private static final long serialVersionUID = -3291490293948917156L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "action_date", nullable = false)
	private Timestamp actionDate;

	@Column(name = "accessed_module", nullable = false)
	private String accessedModule;

	@Column(name = "accessed_module_id", nullable = false)
	private String accessedModuleId;

	@Column(name = "action_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private AuditTrailsActionType actionType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_person_active_person")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party activePerson;

	@Version
	private Long version;

	@Override
	public boolean equals(Object obj)
	{
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public int hashCode()
	{
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public ActivityHistory()
	{
	}
}
