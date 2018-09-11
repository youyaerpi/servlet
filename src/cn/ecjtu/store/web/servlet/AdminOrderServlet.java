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
		// 获取全部订单。
		String str = request.getParameter("state");
		if (str == null || "".equals(str)) {
			list = orderService.findOrders();
		} else {
			list = orderService.findOrders(str);
		}

		// 将全部的订单存放在request中
		request.setAttribute("allOrders", list);
		// 转发到/admin/order/list.jsp
		return "/admin/order/list.jsp";

	}

	// findOrderInfoByOidWithAjax:ajax异步加载订单详情
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

	// updateOrderByOid:更新订单状态
	public String updateOrderByOid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取到订单oid
		String oid = request.getParameter("oid");
		// 根据订单id查询订单
		OrderService orderService = new OrderServiceImpl();
		Order order = orderService.findOrderByOid(oid);
		// 设置订单状态
		order.setState(3);
		// 修改订单信息
		orderService.updateOrder(order);
		// 重定向到查询已发货订单
		response.sendRedirect("/shopping_mall/AdminOrderServlet?method=findOrders&state=3");
		return null;
	}

}
