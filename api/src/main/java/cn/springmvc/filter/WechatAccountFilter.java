package cn.springmvc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.springmvc.Consts;
import cn.springmvc.model.BasicModel;
import cn.springmvc.service.BasicService;

/**
 * Servlet Filter implementation class WechatAccountFilter
 */
@Component
public class WechatAccountFilter implements Filter {

	private BasicService basicService;

	private Logger logger = Logger.getLogger(WechatAccountFilter.class);

	/**
	 * Default constructor.
	 */
	public WechatAccountFilter() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		int basicId = Integer.parseInt(request.getParameter("wechatAccount"));

		if (Consts.getBASIC_DATA() != null) {
			if (Consts.getBASIC_DATA().getId() == basicId) {
				logger.error("get basicModel in Consts ==== Needn't to find in DB");
				chain.doFilter(request, response);
				return;
			}
		}

		try {
			BasicModel basicModel = basicService.getById(basicId);
			Consts.setBASIC_DATA(basicModel);
			logger.error("get baiscModel success! >>> " + basicModel);
		} catch (Exception e) {
			logger.error("get baiscModel error! >>> " + e.getMessage());
		}

		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		ServletContext context = fConfig.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
		basicService = (BasicService) ctx.getBean(BasicService.class);
	}

}
