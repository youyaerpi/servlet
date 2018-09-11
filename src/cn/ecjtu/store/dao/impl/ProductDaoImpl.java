package cn.ecjtu.store.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.ecjtu.store.domain.Product;
import cn.ecjtu.store.utils.JDBCUtils;

public class ProductDaoImpl implements ProductDao {

	@Override
	public List<Product> findHots() {
		String sql = "SELECT * FROM product WHERE pflag=0 AND is_hot=1 ORDER BY pdate DESC LIMIT 0,9";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		List<Product> queryHots = null;
		try {
			queryHots = qr.query(sql, new BeanListHandler<Product>(Product.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return queryHots;
	}

	@Override
	public List<Product> findNews() {
		String sql = "SELECT * FROM product WHERE pflag=0 ORDER BY pdate DESC LIMIT 0,9";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		List<Product> queryNews = null;
		try {
			queryNews = qr.query(sql, new BeanListHandler<Product>(Product.class));

		} catch (SQLException e) {

			e.printStackTrace();

		}
		return queryNews;
	}

	@Override
	public Product findProductByPid(String pid) {
		String sql = "select * from product where pid=? ";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Product query = null;
		try {
			query = qr.query(sql, new BeanHandler<Product>(Product.class), pid);
			// System.out.println(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return query;
	}

	@Override
	public int findTotalRecords(String cid) throws SQLException {
		String sql = "select count(*)from product where cid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Long num = (Long) qr.query(sql, new ScalarHandler(), cid);

		return num.intValue();
	}

	@Override
	public List<Product> findProductByCidWithPage(String cid, int startIndex, int pageSize) {
		String sql = "select* from product where cid=? limit ?,? ";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		List<Product> query = null;
		try {
			query = qr.query(sql, new BeanListHandler<Product>(Product.class), cid, startIndex, pageSize);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return query;
	}

	@Override
	public int findTotalRecords() {
		String sql = "select count(*) from product";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Long num = null;
		try {
			num = (Long) qr.query(sql, new ScalarHandler());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num.intValue();
	}

	@Override
	public List<Product> findAllProductsWithPage(int startIndex, int pageSize) {
		String sql = "select * from product order by pdate desc limit ?,?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		List<Product> list = null;
		try {
			list = qr.query(sql, new BeanListHandler<Product>(Product.class), startIndex, pageSize);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void savaProduct(Product product) {
		String sql="insert into product values(?,?,?,?,?,?,?,?,?,?)";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params ={product.getPid(),product.getPname(),product.getMarket_price(),product.getShop_price(),
				product.getPimage(),product.getPdate(),product.getIs_hot(),product.getPdesc(),product.getPflag(),product.getCid()};
		try {
			qr.update(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
