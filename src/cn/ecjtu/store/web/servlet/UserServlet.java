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
	 * 点击首页注册按钮跳转到UserServlet 再由UserServlet转发到register页面，方便项目管理
	 */
	public String registUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return "/jsp/register.jsp";
	}

	/**
	 * 点击首页登录按钮跳转到UserServlet 再由UserServlet转发到login页面，方便项目管理
	 */
	public String loginUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return "/jsp/login.jsp";
	}

	/**
	 * 注册
	 */
	public String userRegist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 一、 接收表单数据,
		Map<String, String[]> map = request.getParameterMap();
		User user = new User();

		/*
		 * //BeanUtils找到User.class文件 上有setBirthday方法要执行，将199x—xx-xx转换为事件日期格式。
		 * //BeanUtils不知道这个字符串的时间格式是什么。一下三行代码设置时间转换格式 //1_创建时间类型转换器
		 * DateConverter dt= new DateConverter(); //2_设置转换的格式
		 * dt.setPattern("yyyy-MM-dd"); //3_注册转换器 ConvertUtils.register(dt,
		 * java.util.Date.class);
		 * 
		 * BeanUtils.populate(user, map);
		 */

		/**
		 * MybeanUtils小工具解決上述代码
		 */
		MyBeanUtils.populate(user, map);
		// 为用户的其他属性赋值
		user.setUid(UUIDUtils.getId());
		// 状态默认为0
		user.setState(0);
		// 激活码
		user.setCode(UUIDUtils.getCode());
		System.out.println(user);

		// 二、调用业务层注册功能
		UserService userService = new UserServiceImpl();
		try {
			// 注册成功
			// 发送邮件
			userService.userRegist(user);
			MailUtils.sendMail(user.getEmail(), user.getCode());
			request.setAttribute("msg", "用户注册成功，请激活");
		} catch (Exception e) {
			// 注冊失敗，跳转到提示页面
			request.setAttribute("msg", "注册失败，请重新注册");

		}
		return "/jsp/info.jsp";
	}

	public String userLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/**
		 * 获取用户数据（账户密码） 调用业务层登录功能
		 */
		User user = new User();
		MyBeanUtils.populate(user, request.getParameterMap());
		UserService userService = new UserServiceImpl();
		User user02 = null;
		try {
			user02 = userService.usrLogin(user);
			// 用户登录成功,将用户信息放入session中
			request.getSession().setAttribute("loginUser", user02);
			response.sendRedirect("/shopping_mall/index.jsp");
			return null;

		} catch (Exception e) {
			// 用户登陆失败
			String msg = e.getMessage();
			request.setAttribute("msg", msg);
			return "/jsp/login.jsp";
		}

	}
	//退出功能
	public String logoutUI(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		/**
		 * 清除session
		 * 重定向到首页
		 */
		request.getSession().invalidate();
		response.sendRedirect("/shopping_mall/index.jsp");
		return null;
	}
	
	public String active(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/**
		 * 获取激活码 调用业务层的激活功能 激活信息提示
		 */
		String code = request.getParameter("code");
		UserService userService = new UserServiceImpl();
		boolean flag = userService.userActive(code);
		if (flag == true) {
			request.setAttribute("msg", "用户激活成功请登录");
			return "/jsp/login.jsp";
		} else {
			request.setAttribute("msg", "用户激活失败请重新激活");
			return "/jsp/info.jsp";
		}
	}
}
