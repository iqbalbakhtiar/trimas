/**
 * File Name  : Machine.java
 * Created On : Aug 7, 2025
 * Email	  : iqbal@siriuserp.com
 */
package com.siriuserp.production.dm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.siriuserp.sdk.dm.Model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Iqbal Bakhtiar
 * PT. Sirius Indonesia
 * www.siriuserp.com
 */

@Entity
@Getter
@Setter
@Table(name = "machine")
public class Machine extends Model
{
	private static final long serialVersionUID = -3769801332712944851L;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "status")
	@Type(type = "yes_no")
	private boolean enabled = true;

	@Column(name = "note")
	private String note;

	@Override
	public String getAuditCode()
	{
		return this.getId() + "," + this.getCode();
	}
}
