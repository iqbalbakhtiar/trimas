/**
 * Nov 23, 2007 3:23:58 PM
 * com.siriuserp.tools.dto
 * NewsDTO.java
 */
package com.siriuserp.tools.criteria;

import java.util.ArrayList;
import java.util.List;

import com.siriuserp.sdk.dm.News;



/**
 * @author Ersi Agustin
 */
public class NewsAdapter
{
    private List<News> newsList = new ArrayList<News>();
    private News news;

    private String mode;
    private NewsFilterCriteria filterCriteria;
    
    private List<News> latesNews = new ArrayList<News>();
    
    private News news1;
    private News news2;
    private News news3;
    
    private String visitor;
    
    public List<News> getLatesNews()
    {
        return latesNews;
    }
    public void setLatesNews(List<News> latesNews)
    {
        this.latesNews = latesNews;
    }
    public String getVisitor()
    {
        return visitor;
    }
    public void setVisitor(String visitor)
    {
        this.visitor = visitor;
    }
    public News getNews1()
    {
        return news1;
    }
    public void setNews1(News news1)
    {
        this.news1 = news1;
    }
    public News getNews2()
    {
        return news2;
    }
    public void setNews2(News news2)
    {
        this.news2 = news2;
    }
    public News getNews3()
    {
        return news3;
    }
    public void setNews3(News news3)
    {
        this.news3 = news3;
    }
    public News getNews()
    {
        return news;
    }
    public void setNews(News news)
    {
        this.news = news;
    }
    public List<News> getNewsList()
    {
        return newsList;
    }
    public void setNewsList(List<News> newsList)
    {
        this.newsList = newsList;
    }
    public String getMode()
    {
        return mode;
    }
    public void setMode(String mode)
    {
        this.mode = mode;
    }
    public NewsFilterCriteria getFilterCriteria()
    {
        return filterCriteria;
    }
    public void setFilterCriteria(NewsFilterCriteria filterCriteria)
    {
        this.filterCriteria = filterCriteria;
    }
}
