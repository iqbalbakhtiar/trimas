package com.siriuserp.sdk.dao;

import com.siriuserp.sdk.base.Dao;
import com.siriuserp.sdk.base.Filterable;
import com.siriuserp.sdk.dm.CreditTerm;

public interface CreditTermDao extends Dao<CreditTerm>,Filterable {
    public CreditTerm loadByParty(Long fromparty, Long toparty, Long relation);
    public CreditTerm loadByRelationship(Long relationshipId, boolean active);
}
