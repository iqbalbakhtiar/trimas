package com.siriuserp.security.dao.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.AccessDeniedException;
import org.springframework.security.ui.AccessDeniedHandler;

/**
 * @author Muhammad Khairullah
 * Sirius Indonesia,PT
 * www.siriuserp.com
 */

public class SiriusAccessDeniedHandlerImpl implements AccessDeniedHandler
{
	public static final String SPRING_SECURITY_ACCESS_DENIED_EXCEPTION_KEY = "SPRING_SECURITY_403_EXCEPTION";
	private String errorPage;

	public void handle(ServletRequest request, ServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException
	{
		if (errorPage != null)
		{
			HttpServletRequest servletRequest = (HttpServletRequest) request;
			servletRequest.setAttribute(SPRING_SECURITY_ACCESS_DENIED_EXCEPTION_KEY, accessDeniedException);
			
			RequestDispatcher rd = request.getRequestDispatcher(errorPage);
			String acceptHeader = ((HttpServletRequest) request).getHeader("Accept");
			String pathInfo = ((HttpServletRequest) request).getPathInfo();

			if (acceptHeader.contains("json"))
			{
				HttpServletResponse res = (HttpServletResponse) response;
				res.setContentType("application/json;charset=UTF-8");
				res.setHeader("Cache-Control", "no-cache");
				res.getWriter().write("{\"message\":\""+accessDeniedException.getLocalizedMessage()+"\",\"status\":\""+pathInfo+"\"}");
			} 
			else
			{
				rd.forward(request, response);
				if (!response.isCommitted())
					((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
			}
		}
	}

	public void setErrorPage(String errorPage)
	{
		if ((errorPage != null) && !errorPage.startsWith("/"))
			throw new IllegalArgumentException("errorPage must begin with '/'");
		this.errorPage = errorPage;
	}
}
