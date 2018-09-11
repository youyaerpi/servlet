package cn.ecjtu.store.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import cn.ecjtu.store.domain.User;

/**
 * Servlet Filter implementation class PrivilegeFilter
 */
@WebFilter({ "/jsp/cart.jsp", "/jsp/order_info.jsp", "/jsp/order_list.jsp" })
public class PrivilegeFilter implements Filter {
 
	public PrivilegeFilter() {

	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 判断当前session中是否存在已经登录成功的用户
		HttpServletRequest req=(HttpServletRequest)request;
		User user = (User) req.getSession().getAttribute("loginUser");
		// 如果存在放行
		if(user!=null){
			chain.doFilter(request, response);
		}else{
			// 不存在，转入到提示信息页面
			request.setAttribute("msg", "请用户登录再访问");
			request.getRequestDispatcher("/jsp/info.jsp").forward(request, response);
		}
		
			

	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}
