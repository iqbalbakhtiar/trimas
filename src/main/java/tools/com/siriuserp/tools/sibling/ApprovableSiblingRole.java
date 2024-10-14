package com.siriuserp.tools.sibling;

import com.siriuserp.sdk.base.AbstractApprovableInterceptor;
import com.siriuserp.sdk.dm.*;
import com.siriuserp.sdk.utility.DateHelper;
import com.siriuserp.sdk.utility.SiriusValidator;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional(rollbackFor = Exception.class)
public class ApprovableSiblingRole extends AbstractSiblingRole implements ApplicationContextAware {

    private ApplicationContext context;

    @Override
    public void execute() throws Exception {
		System.out.println("ApprovableSiblingRole - execute");
        Model object = (Model) getSiblingable();
		Form form = object.getForm();

		if(form.getApprovalDecisionStatus() != null)
		{
			if(object instanceof ApprovableBridge)
				object = (Model) ReflectionTestUtils.invokeGetterMethod(object, "getApprovable");

			if(!SiriusValidator.validateParam(form.getRemark()))
				form.setRemark(form.getApprovalDecisionStatus().getNormalizedName().toLowerCase());

			Approvable approvable = (Approvable) object;

			ApprovalHistory history = new ApprovalHistory();
			history.setApprovalDecision(approvable.getApprovalDecision());
			history.setSequence(approvable.getApprovalDecision().getHistories().size() + 1);
			history.setApprover(approvable.getApprovalDecision().getForwardTo());
			history.setApprovalDecisionStatus(form.getApprovalDecisionStatus());
			history.setRemark(form.getRemark());
			history.setDateTime(DateHelper.now());

			ApprovalDecision approvalDecision = genericDao.load(ApprovalDecision.class, approvable.getApprovalDecision().getId());
			approvalDecision.getHistories().add(history);

			if(form.getForwardTo() != null)
				approvalDecision.setForwardTo(form.getForwardTo());

			approvalDecision.setApprovalDecisionStatus(form.getApprovalDecisionStatus());

			genericDao.update(approvalDecision);

			//Use stream avoiding ConcurrentModificationException
			List<String> interceptorNames = approvable.getInterceptors().stream()
					.map(interceptor -> interceptor.getName())
					.collect(Collectors.toList());

			for (String interceptorName : interceptorNames)
			{
				AbstractApprovableInterceptor interceptor = (AbstractApprovableInterceptor) context.getBean(interceptorName);
				if (interceptor != null)
				{
					interceptor.setApprovable(approvable);
					interceptor.setStatus(approvable.getApprovalDecision().getApprovalDecisionStatus());
					interceptor.execute();
				}
			}
		}
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
