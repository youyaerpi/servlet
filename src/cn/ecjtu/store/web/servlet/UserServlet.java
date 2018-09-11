package cn.ecjtu.store.web.servlet;

import java.io.IOException;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.ecjtu.store.domain.User;
import cn.ecjtu.store.service.impl.UserService;
import cn.ecjtu.store.service.impl.UserServiceImpl;
import cn.ecjtu.store.utils.MailUtils;
import cn.ecjtu.store.utils.MyBeanUtils;
import cn.ecjtu.store.utils.UUIDUtils;
import cn.ecjtu.store.web.base.BaseServlet;

@SuppressWarnings("serial")
public class UserServlet extends BaseServlet {
	/**
	 * �����ҳע�ᰴť��ת��UserServlet ����UserServletת����registerҳ�棬������Ŀ����
	 */
	public String registUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return "/jsp/register.jsp";
	}

	/**
	 * �����ҳ��¼��ť��ת��UserServlet ����UserServletת����loginҳ�棬������Ŀ����
	 */
	public String loginUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return "/jsp/login.jsp";
	}

	/**
	 * ע��
	 */
	public String userRegist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// һ�� ���ձ�����,
		Map<String, String[]> map = request.getParameterMap();
		User user = new User();

		/*
		 * //BeanUtils�ҵ�User.class�ļ� ����setBirthday����Ҫִ�У���199x��xx-xxת��Ϊ�¼����ڸ�ʽ��
		 * //BeanUtils��֪������ַ�����ʱ���ʽ��ʲô��һ�����д�������ʱ��ת����ʽ //1_����ʱ������ת����
		 * DateConverter dt= new DateConverter(); //2_����ת���ĸ�ʽ
		 * dt.setPattern("yyyy-MM-dd"); //3_ע��ת���� ConvertUtils.register(dt,
		 * java.util.Date.class);
		 * 
		 * BeanUtils.populate(user, map);
		 */

		/**
		 * MybeanUtilsС���߽�Q��������
		 */
		MyBeanUtils.populate(user, map);
		// Ϊ�û����������Ը�ֵ
		user.setUid(UUIDUtils.getId());
		// ״̬Ĭ��Ϊ0
		user.setState(0);
		// ������
		user.setCode(UUIDUtils.getCode());
		System.out.println(user);

		// ��������ҵ���ע�Ṧ��
		UserService userService = new UserServiceImpl();
		try {
			// ע��ɹ�
			// �����ʼ�
			userService.userRegist(user);
			MailUtils.sendMail(user.getEmail(), user.getCode());
			request.setAttribute("msg", "�û�ע��ɹ����뼤��");
		} catch (Exception e) {
			// ע��ʧ������ת����ʾҳ��
			request.setAttribute("msg", "ע��ʧ�ܣ�������ע��");

		}
		return "/jsp/info.jsp";
	}

	public String userLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/**
		 * ��ȡ�û����ݣ��˻����룩 ����ҵ����¼����
		 */
		User user = new User();
		MyBeanUtils.populate(user, request.getParameterMap());
		UserService userService = new UserServiceImpl();
		User user02 = null;
		try {
			user02 = userService.usrLogin(user);
			// �û���¼�ɹ�,���û���Ϣ����session��
			request.getSession().setAttribute("loginUser", user02);
			response.sendRedirect("/shopping_mall/index.jsp");
			return null;

		} catch (Exception e) {
			// �û���½ʧ��
			String msg = e.getMessage();
			request.setAttribute("msg", msg);
			return "/jsp/login.jsp";
		}

	}
	//�˳�����
	public String logoutUI(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		/**
		 * ���session
		 * �ض�����ҳ
		 */
		request.getSession().invalidate();
		response.sendRedirect("/shopping_mall/index.jsp");
		return null;
	}
	
	public String active(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/**
		 * ��ȡ������ ����ҵ���ļ���� ������Ϣ��ʾ
		 */
		String code = request.getParameter("code");
		UserService userService = new UserServiceImpl();
		boolean flag = userService.userActive(code);
		if (flag == true) {
			request.setAttribute("msg", "�û�����ɹ����¼");
			return "/jsp/login.jsp";
		} else {
			request.setAttribute("msg", "�û�����ʧ�������¼���");
			return "/jsp/info.jsp";
		}
	}
}
