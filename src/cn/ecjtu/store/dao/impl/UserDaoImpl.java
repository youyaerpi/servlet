package cn.ecjtu.store.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.ecjtu.store.domain.User;
import cn.ecjtu.store.utils.JDBCUtils;

public class UserDaoImpl implements UserDao {
	@Override
	public void userRegist(User user) {
		// sql语句
		String sql = "INSERT INTO user VALUES (?,?,?,?,?,?,?,?,?,?)";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params = { user.getUid(), user.getUsername(), user.getPassword(), user.getName(), user.getEmail(),
				user.getTelephone(), user.getBirthday(), user.getSex(), user.getState(), user.getCode() };

		try {
			qr.update(sql, params);
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	@Override
	public User userActive(String code) throws SQLException {
		// 给出sql模板
		String sql = "select *  from user where code=?";
		// 数据池连接
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		// 执行查询
		User user = qr.query(sql, new BeanHandler<User>(User.class), code);
		return user;
	}

	@Override
	public void updateUser(User user) throws SQLException {
		String sql = "update user set username=?,password=? ,name=?,email=?,"
				+ "telephone=?,birthday=?,sex=?,state=?,code=? where uid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params = { user.getUsername(), user.getPassword(), user.getName(), user.getEmail(),
				user.getTelephone(), user.getBirthday(), user.getSex(), user.getState(), user.getCode(),
				user.getUid() };
		qr.update(sql, params);

	}

	@Override
	public User userLogin(User user)  {
		String sql = "SELECT * from user where username=? and password=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		User query=null;
		try {
			query = qr.query(sql, new BeanHandler<User>(User.class), user.getUsername(), user.getPassword());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return query;

	}
}
