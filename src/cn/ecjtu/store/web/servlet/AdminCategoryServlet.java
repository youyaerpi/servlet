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
		// ��ȡȫ��������Ϣ
		CategoryService categoryService = new CategoryServiceImpl();
		List<Category> list = categoryService.getAllCats();
		// ȫ��������Ϣ����request
		request.setAttribute("allCats", list);
		// ת����/admin/category/list.jsp
		return "/admin/category/list.jsp";

	}

	// addCategoryUI
	public String addCategoryUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ���UI

		return "/admin/category/add.jsp";
	}

	public String addCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ��ȡ��������
		String cname = request.getParameter("cname");
		// ��������ID
		String id = UUIDUtils.getId();
		Category c = new Category();
		c.setCname(cname);
		c.setCid(id);
		// ����ҵ�����ӷ��๦��
		CategoryService categoryService = new CategoryServiceImpl();
		categoryService.addCategory(c);
		// �ض��򵽲�ѯȫ��������Ϣ
		response.sendRedirect("/shopping_mall/AdminCategoryServlet?method=findAllCats");

		return null;
	}

}
