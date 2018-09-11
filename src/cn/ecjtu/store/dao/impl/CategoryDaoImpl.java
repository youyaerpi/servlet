package cn.ecjtu.store.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.ecjtu.store.domain.Category;
import cn.ecjtu.store.utils.JDBCUtils;

public class CategoryDaoImpl implements CategoryDao {

	@Override
	public List<Category> getAllCats() {
		String sql="select * from category";
		QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
		try {
			List<Category> query = qr.query(sql, new BeanListHandler<Category>(Category.class));
			return query;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		
	}

	@Override
	public void addCategory(Category c) {
		String sql="insert into category values(?,?)";
		QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
		try {
			qr.update(sql,c.getCid(),c.getCname());
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

}
