/**
 * 
 */
package com.siriuserp.dashboard.service;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.siriuserp.sdk.dm.UrlCache;
import com.siriuserp.sdk.utility.SiriusValidator;
import com.siriuserp.sdk.utility.UserHelper;

import javolution.util.FastMap;

/**
 * @author
 * Betsu Brahmana Restu
 * PT. Sirius Indonesia
 * betsu@siriuserp.com
 */

@Component
@Transactional
public class QuickSearchService
{
	public String search(String code) throws Exception
	{
		String cache = UserHelper.activeUser().getMenus().get(code.toUpperCase());
		if (SiriusValidator.validateParam(cache))
		{
			String[] names = cache.split(">");

			return names[names.length - 1].trim();
		}

		return "start.htm";
	}

	public Map<String, Object> dashboard(UrlCache cache)
	{
		Map<String, Object> dashboard = new FastMap<String, Object>();

		if (SiriusValidator.validateParam(cache.getName()))
		{
			String[] names = cache.getName().split(">");

			dashboard.put("code", cache.getCode());
			dashboard.put("name", names[names.length - 1].trim());
		}

		return dashboard;
	}
}
