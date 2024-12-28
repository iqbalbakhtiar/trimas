package com.siriuserp.accounting.aspect;

import java.lang.reflect.Modifier;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.accounting.dm.JournalEntryConfiguration;
import com.siriuserp.accounting.dm.Postingable;
import com.siriuserp.accounting.posting.AutomaticPosting;
import com.siriuserp.accounting.posting.PostingRole;
import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.utility.SiriusValidator;

import javolution.util.FastList;

@Component
@Aspect
@Transactional(rollbackFor = Exception.class)
public class AutomaticPostingAspect implements ApplicationContextAware
{
	private ApplicationContext context;

	@Autowired
	private GenericDao genericDao;

	@Transactional(propagation = Propagation.REQUIRED)
	@Around("@annotation(posting) && args(postingable,..)")
	public void post(ProceedingJoinPoint point, AutomaticPosting posting, Postingable postingable) throws Throwable
	{
		point.proceed();

		JournalEntryConfiguration config = genericDao.load(JournalEntryConfiguration.class, Long.valueOf(1));

		if (config.isJournalEntryTransaction())
		{
			FastList<String> classes = new FastList<String>();

			for (Class<? extends PostingRole> roleclass : posting.roleClasses())
				if (!Modifier.isAbstract(roleclass.getModifiers()))
					classes.add(roleclass.getSimpleName());

			for (String roleclass : posting.roles())
				if (SiriusValidator.validateParam(roleclass))
					classes.add(roleclass);

			for (String roleclass : classes)
			{
				PostingRole postingRole = null;

				if (context.containsBean(toQName(roleclass)))
					postingRole = (PostingRole) context.getBean(toQName(roleclass), PostingRole.class);
				else
					postingRole = (PostingRole) context.getBean(roleclass, PostingRole.class);

				if (postingRole != null)
				{
					postingRole.setPostingable(postingable);
					postingRole.setAutomaticPosting(posting);

					postingRole.inisialize();
					postingRole.execute();
					postingRole.commit();
				}
			}
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
		this.context = applicationContext;
	}

	private String toQName(String name)
	{
		StringBuilder builder = new StringBuilder();

		builder.append(name.substring(0, 1).toLowerCase());
		builder.append(name.substring(1, name.length()));

		return builder.toString();
	}
}
