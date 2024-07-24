/**
 * Nov 22, 2007 10:49:43 AM
 * com.siriuserp.tools.presentation
 * NewsCMSController.java
 */
package com.siriuserp.tools.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.siriuserp.sdk.base.ControllerBase;
import com.siriuserp.sdk.dm.News;
import com.siriuserp.sdk.exceptions.ServiceException;
import com.siriuserp.sdk.springmvc.view.ViewHelper;
import com.siriuserp.tools.criteria.NewsFilterCriteria;
import com.siriuserp.tools.query.NewsGridViewQuery;
import com.siriuserp.tools.service.NewsService;

/**
 * @author Ersi Agustin
 * Sirius Indonesia, PT
 * www.siriuserp.com
 */
@Controller
@SessionAttributes(value = "news", types = News.class)
public class NewsController extends ControllerBase
{
	@Autowired
	@Qualifier("newsService")
	private NewsService newsService;

	@RequestMapping("/newsView.htm")
	public ModelAndView view(HttpServletRequest request) throws Exception
	{
		return new ModelAndView("newsList", newsService.view(criteriaFactory.create(request, NewsFilterCriteria.class), NewsGridViewQuery.class));
	}

	@RequestMapping("/newsPrepareAdd.htm")
	public ModelAndView preadd()
	{
		return new ModelAndView("newsAdd", newsService.preadd());
	}

	@RequestMapping("/newsAdd.htm")
	public ModelAndView add(@ModelAttribute("news") News news) throws ServiceException
	{
		newsService.add(news);
		
		return ViewHelper.redirectTo("newsView.htm");
	}

	@RequestMapping("/newsPrepareUpdate.htm")
	public ModelAndView preedit(@RequestParam("id") Long id)
	{
		return new ModelAndView("newsUpdate", newsService.preedit(id));
	}

	@RequestMapping("/newsUpdate.htm")
	public ModelAndView edit(@ModelAttribute("news") News news) throws ServiceException
	{
		newsService.update(news);
		return ViewHelper.redirectTo("newsView.htm");
	}

	@RequestMapping("/newsDelete.htm")
	public ModelAndView delete(@RequestParam("id") Long id) throws ServiceException
	{
		newsService.delete(newsService.load(id));

		return ViewHelper.redirectTo("newsView.htm");
	}
}
