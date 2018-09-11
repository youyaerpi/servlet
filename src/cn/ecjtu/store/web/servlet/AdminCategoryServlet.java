package cn.ecjtu.store.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ecjtu.store.domain.Category;
import cn.ecjtu.store.service.impl.CategoryService;
import cn.ecjtu.store.service.impl.CategoryServiceImpl;
import cn.ecjtu.store.utils.UUIDUtils;
import cn.ecjtu.store.web.base.BaseServlet;

@SuppressWarnings("serial")
public class AdminCategoryServlet extends BaseServlet {

	public String findAllCats(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取全部分类信息
		CategoryService categoryService = new CategoryServiceImpl();
		List<Category> list = categoryService.getAllCats();
		// 全部分类信息放入request
		request.setAttribute("allCats", list);
		// 转发到/admin/category/list.jsp
		return "/admin/category/list.jsp";

	}

	// addCategoryUI
	public String addCategoryUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 添加UI

		return "/admin/category/add.jsp";
	}

	public String addCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取分类名称
		String cname = request.getParameter("cname");
		// 创建分类ID
		String id = UUIDUtils.getId();
		Category c = new Category();
		c.setCname(cname);
		c.setCid(id);
		// 调用业务层添加分类功能
		CategoryService categoryService = new CategoryServiceImpl();
		categoryService.addCategory(c);
		// 重定向到查询全部分类信息
		response.sendRedirect("/shopping_mall/AdminCategoryServlet?method=findAllCats");

		return null;
	}

}
