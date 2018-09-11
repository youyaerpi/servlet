package cn.ecjtu.store.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ecjtu.store.domain.Order;
import cn.ecjtu.store.service.impl.OrderService;
import cn.ecjtu.store.service.impl.OrderServiceImpl;
import cn.ecjtu.store.web.base.BaseServlet;
import net.sf.json.JSONArray;

@SuppressWarnings("serial")
public class AdminOrderServlet extends BaseServlet {

	public String findOrders(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		OrderService orderService = new OrderServiceImpl();
		List<Order> list = null;
		// ��ȡȫ��������
		String str = request.getParameter("state");
		if (str == null || "".equals(str)) {
			list = orderService.findOrders();
		} else {
			list = orderService.findOrders(str);
		}

		// ��ȫ���Ķ��������request��
		request.setAttribute("allOrders", list);
		// ת����/admin/order/list.jsp
		return "/admin/order/list.jsp";

	}

	// findOrderInfoByOidWithAjax:ajax�첽���ض�������
	public String findOrderInfoByOidWithAjax(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String oid = request.getParameter("id");
		OrderService orderService = new OrderServiceImpl();
		Order order = orderService.findOrderByOid(oid);
		String jsonStr = JSONArray.fromObject(order.getList()).toString();
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().print(jsonStr);
		return null;
	}

	// updateOrderByOid:���¶���״̬
	public String updateOrderByOid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ��ȡ������oid
		String oid = request.getParameter("oid");
		// ���ݶ���id��ѯ����
		OrderService orderService = new OrderServiceImpl();
		Order order = orderService.findOrderByOid(oid);
		// ���ö���״̬
		order.setState(3);
		// �޸Ķ�����Ϣ
		orderService.updateOrder(order);
		// �ض��򵽲�ѯ�ѷ�������
		response.sendRedirect("/shopping_mall/AdminOrderServlet?method=findOrders&state=3");
		return null;
	}

}
