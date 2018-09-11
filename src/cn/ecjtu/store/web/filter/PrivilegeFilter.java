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
		// �жϵ�ǰsession���Ƿ�����Ѿ���¼�ɹ����û�
		HttpServletRequest req=(HttpServletRequest)request;
		User user = (User) req.getSession().getAttribute("loginUser");
		// ������ڷ���
		if(user!=null){
			chain.doFilter(request, response);
		}else{
			// �����ڣ�ת�뵽��ʾ��Ϣҳ��
			request.setAttribute("msg", "���û���¼�ٷ���");
			request.getRequestDispatcher("/jsp/info.jsp").forward(request, response);
		}
		
			

	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}
