package com.siriuserp.sdk.dao;

import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.dm.AccessLevel;


public interface AccessLevelDao extends Dao<AccessLevel>
{
	public AccessLevel findAccessLevelByLevelName(String levelName);
}
