package cn.ecjtu.store.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ecjtu.store.domain.PageModel;
import cn.ecjtu.store.domain.Product;
import cn.ecjtu.store.service.impl.ProductService;
import cn.ecjtu.store.service.impl.ProductServiceImpl;
import cn.ecjtu.store.web.base.BaseServlet;

@SuppressWarnings("serial")
public class ProductServlet extends BaseServlet {

	public String findProductByPid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/**
		 * ��ȡ��Ʒ��pid ������Ʒpid��ѯ��Ʒ��Ϣ ����ȡ������Ʒ����request�� ת����jsp/product_info.jsp
		 */
		String pid = request.getParameter("pid");
		ProductService productService = new ProductServiceImpl();
		Product product = productService.findProductByPid(pid);
		request.setAttribute("product", product);
		// System.out.println(product);
		return "/jsp/product_info.jsp";
	}

	public String findProductByCidWithPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		/**
		 * ʵ����Ʒ��ҳ��ѯ
		 */
		String cid = request.getParameter("cid");
		int curNum = Integer.parseInt(request.getParameter("num"));
		ProductService productService = new ProductServiceImpl();
		/**
		 * ����PageModel��������������1.��ǰҳ��Ʒ��Ϣ��2.��ҳ��3.url��
		 */
		PageModel pm = productService.findProductByCidWithPage(cid, curNum);
		//����PageModel����request�У���ת����/jsp/product_list.jsp
		request.setAttribute("page", pm);
		return "/jsp/product_list.jsp";
		

	}

}
