/**
 * Nov 14, 2008 3:45:29 PM
 * com.siriuserp.sdk.utility
 * FilterCriteriaFactory.java
 */
package com.siriuserp.sdk.utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.RootLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siriuserp.sdk.dao.GenericDao;
import com.siriuserp.sdk.dm.DisplayConfiguration;
import com.siriuserp.sdk.dm.FilterCriteriaType;
import com.siriuserp.sdk.dm.Profile;
import com.siriuserp.sdk.dm.User;
import com.siriuserp.sdk.exceptions.FilterCreationException;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.filter.AbstractFilterCriteria;
import com.siriuserp.sdk.filter.AbstractReportFilterCriteria;
import com.siriuserp.sdk.filter.GridViewFilterCriteria;

import javolution.util.FastList;

/**
 * @author Agung Dodi Perdana
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */

@SuppressWarnings("unchecked")
@Component
public class FilterCriteriaFactory
{
	public static final Logger logger = RootLogger.getLogger(FilterCriteriaFactory.class);

	@Autowired
	private GenericDao genericDao;

	public final GridViewFilterCriteria create(HttpServletRequest request, Class<? extends AbstractFilterCriteria> filterCriteria) throws ServiceException
	{
		return create(request, filterCriteria, FilterCriteriaType.DEFAULT);
	}

	public final GridViewFilterCriteria createPopup(HttpServletRequest request, Class<? extends AbstractFilterCriteria> filterCriteria) throws ServiceException
	{
		return create(request, filterCriteria, FilterCriteriaType.POPUP);
	}

	public final <T extends AbstractReportFilterCriteria> T createReport(HttpServletRequest request, Class<? extends AbstractReportFilterCriteria> filterCriteria) throws ServiceException
	{
		return (T) get(request, filterCriteria);
	}

	private GridViewFilterCriteria create(HttpServletRequest request, Class<? extends AbstractFilterCriteria> filterCriteria, FilterCriteriaType type) throws ServiceException
	{
		AbstractFilterCriteria criteria = null;

		try
		{
			criteria = filterCriteria.getDeclaredConstructor().newInstance();

			FilterCriteriaUtil.init(request, criteria);

			criteria.setPage(SiriusValidator.getValidPageIndex(request.getParameter("page")));
			criteria.setUser(UserHelper.activeUser());
			criteria.setActivePerson(UserHelper.activePerson().getId());

			HttpSession session = request.getSession(false);
			if (session != null)
			{
				Object _profile = session.getAttribute(SessionHelper.PROFILE_KEY);
				if (_profile != null)
				{
					Profile profile = (Profile) _profile;
					useProfile(type, criteria, profile);
				} else
				{
					User user = genericDao.load(User.class, UserHelper.activeUser().getId());
					if (user != null && user.getProfile() != null)
						useProfile(type, criteria, user.getProfile());
					else
						useGlobalConfig(type, criteria);
				}
			} else
				useGlobalConfig(type, criteria);

			if (SiriusValidator.validateParamWithZeroPosibility(request.getParameter("organization")))
			{
				if (!criteria.getOrganizations().isEmpty())
					FastList.recycle(criteria.getOrganizations());

				criteria.setOrganization(Long.valueOf(request.getParameter("organization")));
				criteria.getOrganizations().add(Long.valueOf(request.getParameter("organization")));
			}
		} catch (Exception e)
		{
			logger.error(e);
			e.printStackTrace();
			throw new FilterCreationException(this.getClass().getSimpleName(), e);
		}

		return criteria;
	}

	private AbstractReportFilterCriteria get(HttpServletRequest request, Class<? extends AbstractReportFilterCriteria> filterCriteria) throws ServiceException
	{
		AbstractReportFilterCriteria criteria = null;

		try
		{
			criteria = filterCriteria.getDeclaredConstructor().newInstance();

			FilterCriteriaUtil.init(request, criteria);

			if (SiriusValidator.validateParamWithZeroPosibility(request.getParameter("organization")))
			{
				if (!criteria.getOrganizations().isEmpty())
					FastList.recycle(criteria.getOrganizations());

				criteria.setOrganization(Long.valueOf(request.getParameter("organization")));
				criteria.getOrganizations().add(Long.valueOf(request.getParameter("organization")));
			}
		} catch (Exception e)
		{
			logger.error(e);
			throw new FilterCreationException(this.getClass().getSimpleName(), e);
		}

		return criteria;
	}

	private void useProfile(FilterCriteriaType type, AbstractFilterCriteria criteria, Profile profile)
	{
		switch (type)
		{
		case POPUP:
			criteria.setMax(profile.getPopuprow());
			break;
		default:
			criteria.setMax(profile.getRowperpage());
			break;
		}
	}

	private void useGlobalConfig(FilterCriteriaType type, AbstractFilterCriteria criteria)
	{
		DisplayConfiguration displayConfiguration = genericDao.load(DisplayConfiguration.class, Long.valueOf(1));
		if (displayConfiguration != null)
		{
			switch (type)
			{
			case POPUP:
				criteria.setMax(displayConfiguration.getPopuprows());
				break;
			default:
				criteria.setMax(displayConfiguration.getRow());
				break;
			}
		}
	}
}
