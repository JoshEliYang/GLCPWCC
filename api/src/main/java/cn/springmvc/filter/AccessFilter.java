package cn.springmvc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.springmvc.model.User;
import cn.springmvc.model.UserAccess;
import cn.springmvc.service.AdminService;
import cn.springmvc.service.LoginService;

/**
 * Servlet Filter implementation class AccessFilter
 */
@Component
public class AccessFilter implements Filter {
	private AdminService adminService;
	private LoginService loginService;

	Logger logger = Logger.getLogger(AccessFilter.class);

	/**
	 * Default constructor.
	 */
	public AccessFilter() {
		// TODO Auto-generated constructor stub
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
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String token = request.getParameter("token");

		UserAccess userAccess = null;
		try {
			loginService.clearExpiredAccessDat();

			userAccess = loginService.tokenCheck(token);
			logger.error("get user access success >>>> " + userAccess);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("exception occured when getting user access ! >>>> " + e.getMessage());
		}

		if (userAccess == null) {
			String outJson = "{\"code\":\"101\",\"msg\":\"尚未登录\"}";
			httpResponse.getOutputStream().write(outJson.getBytes("UTF-8"));
			return;
		}

		try {
			User admin = adminService.getUserById(userAccess.getUserId());
			request.setAttribute("admin", admin);
			logger.error("get admin info success by token >>>> " + admin);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("get admin info failed by token >>>> " + e.getMessage());
		}

		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		ServletContext context = fConfig.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
		adminService = ctx.getBean(AdminService.class);
		loginService = ctx.getBean(LoginService.class);
	}

}
