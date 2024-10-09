package com.siriuserp.sdk.dm;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public interface Siblingable {
	public Long getId();
}
