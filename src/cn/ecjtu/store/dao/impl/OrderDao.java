package cn.ecjtu.store.dao.impl;

import java.sql.Connection;
import java.util.List;

import cn.ecjtu.store.domain.Order;
import cn.ecjtu.store.domain.OrderItem;
import cn.ecjtu.store.domain.User;

public interface OrderDao {

	void savaOrder(Connection conn, Order order);

	void savaOrderItem(Connection conn, OrderItem item);

	int getTotalRecords(User user);

	List<?> findMyOrdersWithPage(User user, int startIndex, int pageSize);

	Order findOrderByOid(String oid);

	void updateOrder(Order order);

	List<Order> finfindOrders();

	List<Order> finfindOrders(String str);

}
