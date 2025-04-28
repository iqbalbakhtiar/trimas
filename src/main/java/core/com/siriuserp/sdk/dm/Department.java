/**
 * File Name  : Department.java
 * Created On : Apr 28, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.sdk.dm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Getter
@Setter
@Entity
@Table(name = "department")
public class Department extends Model
{
	private static final long serialVersionUID = 7738248155813155245L;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "enabled", length = 1)
	@Type(type = "yes_no")
	private boolean enabled = true;

	@Column(name = "note", length = 255)
	private String note;

	@Override
	public String getAuditCode()
	{
		return this.getId().toString() + "," + this.getCode();
	}
}
