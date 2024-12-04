package com.siriuserp.sdk.utility;

import org.springframework.beans.BeanUtils;

import com.siriuserp.sdk.dm.Approvable;
import com.siriuserp.sdk.dm.ApprovableBridge;
import com.siriuserp.sdk.dm.ApprovableInterceptorName;
import com.siriuserp.sdk.dm.ApprovalDecision;
import com.siriuserp.sdk.dm.ApprovalDecisionStatus;

public class ApprovableBridgeHelper {
    @SuppressWarnings("unchecked")
	public static <T extends Approvable> T create(Class<T> tClass, ApprovableBridge bridge) throws Exception {
        Approvable approvable = (Approvable) Class.forName(tClass.getCanonicalName()).getDeclaredConstructor().newInstance();

        BeanUtils.copyProperties(bridge, approvable);

        ApprovalDecision decision = new ApprovalDecision();
        decision.setApprovalDecisionStatus(ApprovalDecisionStatus.REQUESTED);
        decision.setCreatedDate(DateHelper.now());
        decision.setForwardTo(bridge.getApprover());
        decision.setApprovable(approvable);
        approvable.setApprovalDecision(decision);

        for(String value : bridge.getInterceptorNames())
		{
			ApprovableInterceptorName interceptor = new ApprovableInterceptorName();
			interceptor.setApprovable(approvable);
			interceptor.setName(value);
			approvable.getInterceptors().add(interceptor);
		}

        return (T) approvable;
    }
}
