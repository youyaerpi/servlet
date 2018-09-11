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
			// ���Ը��ݼ������ѯ��һ���û�
			// �޸��û���״̬,״̬Ϊ1����ʾ�������������Ϊ�ա�
			user.setState(1);
			user.setCode(null);
			// �����ݿ�ִ��һ����ʵ����,update user set state=1,code = null where uid=?
			userDao.updateUser(user);
			return true;
		} else {
			// �����Ը��ݼ������ѯ��һ���û�
			return false;
		}
	}

	@Override
	public User usrLogin(User user)throws SQLException {
		UserDao userDao = new UserDaoImpl();
	   User uu=	userDao.userLogin(user);
		if(uu==null){
			throw new RuntimeException("��������");
			
		}else if(uu.getState()==0){
			throw new RuntimeException("�û�δ����");
		}else{
			return uu;
		}
		
	}

}
