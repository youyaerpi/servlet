package cn.ecjtu.store.service.impl;

import java.sql.SQLException;

import cn.ecjtu.store.domain.User;

public interface UserService {

	public  void userRegist(User user) throws SQLException ;

	public boolean userActive(String code)throws SQLException;

	public User usrLogin(User user)throws SQLException;

}