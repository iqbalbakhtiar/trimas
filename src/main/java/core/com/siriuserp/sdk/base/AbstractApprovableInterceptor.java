package com.siriuserp.sdk.base;

import com.siriuserp.sdk.dm.Approvable;
import com.siriuserp.sdk.dm.ApprovalDecisionStatus;

public abstract class AbstractApprovableInterceptor implements Interceptor {
    protected Approvable approvable;

    protected ApprovalDecisionStatus status;

    public Approvable getApprovable()
    {
        return approvable;
    }

    public void setApprovable(Approvable approvable)
    {
        this.approvable = approvable;
    }

    public ApprovalDecisionStatus getStatus()
    {
        return status;
    }

    public void setStatus(ApprovalDecisionStatus status)
    {
        this.status = status;
    }
}
