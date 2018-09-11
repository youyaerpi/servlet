package cn.ecjtu.store.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.ecjtu.store.domain.Order;
import cn.ecjtu.store.domain.OrderItem;
import cn.ecjtu.store.domain.Product;
import cn.ecjtu.store.domain.User;
import cn.ecjtu.store.utils.JDBCUtils;

public class OrderDaoImpl implements OrderDao {

	@Override
	public void savaOrder(Connection conn, Order order) {

		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		// 此时不需要再次建立连接，因为已经传递过来了一个conn
		QueryRunner qr = new QueryRunner();
		Object[] params = { order.getOid(), order.getOrdertime(), order.getTotal(), order.getState(),
				order.getAddress(), order.getName(), order.getTelephone(), order.getUser().getUid() };
		try {
			qr.update(conn, sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void savaOrderItem(Connection conn, OrderItem item) {
		String sql = "insert into orderitem values(?,?,?,?,?)";
		QueryRunner qr = new QueryRunner();
		Object[] params = { item.getItemid(), item.getQuantity(), item.getTotal(), item.getProduct().getPid(),
				item.getOrder().getOid() };
		try {
			qr.update(conn, sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public int getTotalRecords(User user) {
		String sql = "select count(*) from orders where uid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Long num = null;
		try {
			num = (Long) qr.query(sql, new ScalarHandler(), user.getUid());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return num.intValue();
	}

	@Override
	public List<Order> findMyOrdersWithPage(User user, int startIndex, int pageSize) {
		String sql = "select * from orders where uid=? limit ?,?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		List<Order> list = null;
		try {
			list = qr.query(sql, new BeanListHandler<Order>(Order.class), user.getUid(), startIndex, pageSize);
			/**
			 * 遍历所有订单
			 */
			for (Order order : list) {
				// 获取每笔订单oid，查询每笔订单下的订单项以及订单项对应的商品信息
				String oid = order.getOid();
				sql = "SELECT * FROM orderitem o,product p WHERE o.pid=p.pid and oid=?";
				List<Map<String, Object>> list2 = qr.query(sql, new MapListHandler(), oid);
				// 遍历list
				for (Map<String, Object> map : list2) {
					OrderItem orderItem = new OrderItem();
					Product product = new Product();
					DateConverter dt = new DateConverter();
					dt.setPattern("yyyy-MM-dd");
					ConvertUtils.register(dt, java.util.Date.class);
					try {
						BeanUtils.populate(orderItem, map);
						BeanUtils.populate(product, map);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
					orderItem.setProduct(product);
					order.getList().add(orderItem);

				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Order findOrderByOid(String oid) {
		// 根据oid查询当前订单
		String sql = "select * from orders where oid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Order order = null;
		try {
			order = qr.query(sql, new BeanHandler<Order>(Order.class), oid);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		// 根据订单oid查询查询订单下所有的订单项以及订单项关联的商品
		sql = "SELECT * FROM orderitem o,product p WHERE o.pid=p.pid and oid=?";
		List<Map<String, Object>> list2 = null;
		try {
			list2 = qr.query(sql, new MapListHandler(), oid);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		// 遍历list
		for (Map<String, Object> map : list2) {
			OrderItem orderItem = new OrderItem();
			Product product = new Product();
			DateConverter dt = new DateConverter();
			dt.setPattern("yyyy-MM-dd");
			ConvertUtils.register(dt, java.util.Date.class);

			try {
				BeanUtils.populate(orderItem, map);
				BeanUtils.populate(product, map);
			} catch (IllegalAccessException e) {

				e.printStackTrace();
			} catch (InvocationTargetException e) {

				e.printStackTrace();
			}

			orderItem.setProduct(product);
			order.getList().add(orderItem);

		}

		return order;

	}

	@Override
	public void updateOrder(Order order) {
		String sql = "update orders set ordertime=? ,total=? ,state=? ,address=?, name=? ,telephone=? where oid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params = { order.getOrdertime(), order.getTotal(), order.getState(), order.getAddress(),
				order.getName(), order.getTelephone(), order.getOid() };
		try {
			qr.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Order> finfindOrders() {
		String sql = "select * from orders";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		List<Order> list = null;
		try {
			list = qr.query(sql, new BeanListHandler<Order>(Order.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public List<Order> finfindOrders(String str) {
		String sql = "select * from orders where state=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		List<Order> list = null;
		try {
			list = qr.query(sql, new BeanListHandler<Order>(Order.class),str);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
//	public String langing(String name){
//		String sql=".......";
//		QueryRunner qr= new QueryRunner(JDBCUtils.getDataSource());
//		try {
//			String password = (String) qr.query(sql, new ScalarHandler());
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}

}
