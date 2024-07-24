package com.siriuserp.tools.criteria;

import com.siriuserp.sdk.filter.AbstractFilterCriteria;

/**
 * @author AndresNodas
 * Sirius Indonesia, PT
 * www.siriuserp.com
 * Version 1.5
 */
public class ModuleFilterCriteria extends AbstractFilterCriteria {

	private static final long serialVersionUID = 432375126491908721L;
	
	private String code, name, uri;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
}
