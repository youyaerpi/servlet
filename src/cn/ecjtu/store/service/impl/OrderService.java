package cn.ecjtu.store.service.impl;

import java.sql.SQLException;
import java.util.List;

import cn.ecjtu.store.domain.Order;
import cn.ecjtu.store.domain.PageModel;
import cn.ecjtu.store.domain.User;

public interface OrderService {

	void saveOrder(Order order)throws SQLException;

	PageModel findMyOrdersWithPage(User user, int curNum);

	Order findOrderByOid(String oid);

	void updateOrder(Order order);

	List<Order> findOrders();

	List<Order> findOrders(String str);


}
