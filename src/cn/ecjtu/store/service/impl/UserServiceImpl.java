package cn.ecjtu.store.service.impl;

import java.sql.SQLException;

import cn.ecjtu.store.dao.impl.UserDao;
import cn.ecjtu.store.dao.impl.UserDaoImpl;
import cn.ecjtu.store.domain.User;

public class UserServiceImpl implements UserService {
	@Override
	public void userRegist(User user) throws SQLException {
		UserDao userDao = new UserDaoImpl();
		userDao.userRegist(user);

	}

	@Override
	public boolean userActive(String code) throws SQLException {
		UserDao userDao = new UserDaoImpl();
		User user = userDao.userActive(code);
		if (user != null) {
			// 可以根据激活码查询到一个用户
			// 修改用户的状态,状态为1，表示激活，激活码设置为空。
			user.setState(1);
			user.setCode(null);
			// 对数据库执行一次真实操作,update user set state=1,code = null where uid=?
			userDao.updateUser(user);
			return true;
		} else {
			// 不可以根据激活码查询到一个用户
			return false;
		}
	}

	@Override
	public User usrLogin(User user)throws SQLException {
		UserDao userDao = new UserDaoImpl();
	   User uu=	userDao.userLogin(user);
		if(uu==null){
			throw new RuntimeException("密码有误");
			
		}else if(uu.getState()==0){
			throw new RuntimeException("用户未激活");
		}else{
			return uu;
		}
		
	}

}
