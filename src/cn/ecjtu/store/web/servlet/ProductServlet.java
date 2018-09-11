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
		 * 获取商品的pid 根据商品pid查询商品信息 将获取到的商品放入request中 转发到jsp/product_info.jsp
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
		 * 实现商品分页查询
		 */
		String cid = request.getParameter("cid");
		int curNum = Integer.parseInt(request.getParameter("num"));
		ProductService productService = new ProductServiceImpl();
		/**
		 * 返回PageModel对象，三个参数（1.当前页商品信息，2.分页，3.url）
		 */
		PageModel pm = productService.findProductByCidWithPage(cid, curNum);
		//保存PageModel对象到request中，并转发到/jsp/product_list.jsp
		request.setAttribute("page", pm);
		return "/jsp/product_list.jsp";
		

	}

}
