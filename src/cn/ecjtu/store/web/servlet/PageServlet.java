package cn.ecjtu.store.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ecjtu.store.domain.Category;
import cn.ecjtu.store.domain.Product;
import cn.ecjtu.store.service.impl.CategoryService;
import cn.ecjtu.store.service.impl.CategoryServiceImpl;
import cn.ecjtu.store.service.impl.ProductService;
import cn.ecjtu.store.service.impl.ProductServiceImpl;
import cn.ecjtu.store.web.base.BaseServlet;

@SuppressWarnings("serial")
public class PageServlet extends BaseServlet {
/**
 * 页面分类信息
 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/**
		 * 调用业务层的功能，获取全部分类信息，返回集合
		 *将集合放入request
		 *转发到真实的首页 ，可以在header界面使用ajax技术每次加载不同页面时向数据库拿取数据
	   */
		//可以在header界面使用ajax技术每次加载不同页面时向数据库拿取数据，下面代码可以不要了
//		CategoryService categoryService =new CategoryServiceImpl();
//		List<Category>list=categoryService.getAllCats();
//		request.setAttribute("allCats", list);
		
		//调用业务层查询最新商品，最热商品方法，返回两个集合
		ProductService productService= new ProductServiceImpl() ;
		List<Product> list01=productService.findHots();
		List<Product> list02=productService.findNews();
		//将两个集合放入到request中
		request.setAttribute("hots", list01);
		request.setAttribute("news", list02);

		//转发到真实页面
		return "/jsp/index.jsp";
	}

}
