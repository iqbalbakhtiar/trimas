/**
 * Dec 11, 2009 9:40:54 AM
 * com.siriuserp.sdk.dm
 * ExceptionEvent.java
 */
package com.siriuserp.sdk.dm;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

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
@Table(name = "exception_event")
public class ExceptionEvent
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "error_code")
	private String errorCode;

	@Column(name = "exception_type")
	private String exceptionType;

	@Version
	private Long version;

	@Column(name = "stack_trace")
	private String stacktrace;

	@Column(name = "error_time")
	private Timestamp errorTime;

	@Column(name = "location")
	private String location;

	@Column(name = "error_message")
	private String errorMessage;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_person_logged_in")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private Party loggedin;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_exception_dictionary")
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	private ExceptionDictionary dictionary;
}
