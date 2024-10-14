package com.siriuserp.sdk.dm;

import com.siriuserp.sales.dm.ApprovableType;

import java.util.Date;
import java.util.Set;

public interface ApprovableBridge extends Siblingable{

    public abstract String getCode();
    public abstract ApprovableType getApprovableType();
    public abstract String getUri();
    public abstract Date getDate();
    public abstract Party getOrganization();
    public abstract Party getApprover();

    public abstract Set<String> getInterceptorNames();
}
