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
import cn.springmvc.model.admin.AdminInfo;
import cn.springmvc.service.basic.AdminService;

/**
 * Servlet Filter implementation class AccessFilter
 */
@Component
public class AccessFilter implements Filter {
	private AdminService adminService;

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

		AdminInfo adminInfo = null;
		try {
			adminInfo = adminService.verify(token);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("get admin info error by token >>>> " + e.getMessage());
		}

		if (adminInfo == null || adminInfo.isExist() == false) {
			String outJson = "{\"code\":\"101\",\"msg\":\"尚未登录\"}";
			httpResponse.getOutputStream().write(outJson.getBytes("UTF-8"));
			return;
		}

		User admin = new User();
		admin.setId(adminInfo.getId());
		admin.setUsername(adminInfo.getName());
		admin.setRealname(adminInfo.getRealName());
		admin.setUserLevel(adminInfo.getUserLevel());
		admin.setLevelName(adminInfo.getLevelName());

		request.setAttribute("admin", admin);
		logger.error("get admin info success by token >>>> " + admin);

		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		ServletContext context = fConfig.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
		adminService = ctx.getBean(AdminService.class);
	}

}
