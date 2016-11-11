package com.lemanov.internetshop.servlet;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionFilter implements Filter {
	
	private FilterConfig filterConfig;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
		System.out.println("Fiter is running");
		HttpSession session = null;
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		try {
			session = req.getSession(false);
			String reqUri = req.getRequestURI();
			if (reqUri.contains("index.jsp") || reqUri.endsWith("InternetShop/") || reqUri.contains("logout.jsp")
					|| reqUri.contains("register") || reqUri.contains("login")) {
				System.out.println("Filter: 1 permit URI");
				chain.doFilter(request, response);
			} else if (session == null) {
				System.out.println("Filter: 2 session is null. Not index.jsp");
				res.sendRedirect("./logout.jsp");
			} else if (session.getAttribute("userID") == null) {
				System.out.println("Filter: 3 Session userID=null");
				res.sendRedirect("./logout.jsp");
			} else {
				System.out.println("Filter: 4 doFilter default");
				chain.doFilter(request, response);
			}
		} catch (IOException io) {
			System.out.println("IOException raised in SimpleFilter");
		} catch (ServletException se) {
			System.out.println("ServletException raised in SimpleFilter");
		}
	}

	public FilterConfig getFilterConfig() {
		return this.filterConfig;
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}