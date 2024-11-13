package com.siriuserp.tools.dao;

import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.base.Filterable;
import com.siriuserp.sdk.dm.Approvable;
import com.siriuserp.sdk.dm.Party;

public interface ApprovableDao extends Dao<Approvable>, Filterable {
    Long getTotalRequisition(Party party);
}
