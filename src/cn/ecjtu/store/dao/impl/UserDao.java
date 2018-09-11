package cn.ecjtu.store.dao.impl;

import java.sql.SQLException;

import cn.ecjtu.store.domain.User;

public interface UserDao {

	public void userRegist(User user) throws SQLException;

	public User userActive(String code) throws SQLException;

	public void updateUser(User user) throws SQLException;

	public User userLogin(User user) throws SQLException;

}
