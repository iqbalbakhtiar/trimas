package com.siriuserp.sdk.dm;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class Lot 
{
	@Column(name = "serial_no", length = 100, unique = true)
	private String serial;
	
	@Column(name = "code")
	private String code;

	@Column(name = "info")
	private String info;
}
