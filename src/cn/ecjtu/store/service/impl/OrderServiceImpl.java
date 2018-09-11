package cn.ecjtu.store.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import cn.ecjtu.store.dao.impl.OrderDao;
import cn.ecjtu.store.dao.impl.OrderDaoImpl;
import cn.ecjtu.store.domain.Order;
import cn.ecjtu.store.domain.OrderItem;
import cn.ecjtu.store.domain.PageModel;
import cn.ecjtu.store.domain.User;
import cn.ecjtu.store.utils.JDBCUtils;

public class OrderServiceImpl implements OrderService {
	OrderDao orderDao = new OrderDaoImpl();

	@Override
	public void saveOrder(Order order) throws SQLException {
		/**
		 * ���涩���Ͷ��������еĶ����ͬʱ�ɹ���ʧ�ܣ� ��ȡ����
		 */
		// JDBCUtils.startTransaction();
		// OrderDao orderDao =new OrderDaoImpl();
		// orderDao.savaOrder(order);
		// for(OrderItem item:order.getList()){
		// orderDao.savaOrderItem(item);
		// }
		// JDBCUtils.commitAndClose();
		// JDBCUtils.rollbackAndClose();
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(false);
			orderDao.savaOrder(conn, order);
			for (OrderItem item : order.getList()) {
				orderDao.savaOrderItem(conn, item);
			}
			conn.commit();
		} catch (Exception e) {
			// �ع�
			conn.rollback();

		}
	}

	@Override
	public PageModel findMyOrdersWithPage(User user, int curNum) {
		// ����pageModel����Ŀ�ģ����㲢Я����ҳ����
		// select count(*) from orders where uid=?
		int totalRecords = orderDao.getTotalRecords(user);
		PageModel pm = new PageModel(curNum, totalRecords, 3);
		// �������� select * from orders where uid=? limit ?,?
		List<?> list = orderDao.findMyOrdersWithPage(user, pm.getStartIndex(), pm.getPageSize());
		pm.setList(list);
		// ����url
		pm.setUrl("OrderServlet?method=findMyOrdersWithPage");
		return pm;
	}

	@Override
	public Order findOrderByOid(String oid) {
		Order order = orderDao.findOrderByOid(oid);
		return order;
	}

	@Override
	public void updateOrder(Order order) {
		orderDao.updateOrder(order);
	}

	@Override
	public List<Order> findOrders() {
		List<Order> list = orderDao.finfindOrders();
		return list;
	}

	@Override
	public List<Order> findOrders(String str) {
		List<Order> list = orderDao.finfindOrders(str);
		return list;
	}
}
