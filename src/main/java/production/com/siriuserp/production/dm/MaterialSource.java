package com.siriuserp.production.dm;

public enum MaterialSource {
	WIP, MATERIAL_REQUEST, OUTPUT;
	
	public String getNormalizedName()
	{
		return this.toString().replace("_", " ");
	}
}
