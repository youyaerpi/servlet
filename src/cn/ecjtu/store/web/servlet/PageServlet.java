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
 * ҳ�������Ϣ
 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/**
		 * ����ҵ���Ĺ��ܣ���ȡȫ��������Ϣ�����ؼ���
		 *�����Ϸ���request
		 *ת������ʵ����ҳ ��������header����ʹ��ajax����ÿ�μ��ز�ͬҳ��ʱ�����ݿ���ȡ����
	   */
		//������header����ʹ��ajax����ÿ�μ��ز�ͬҳ��ʱ�����ݿ���ȡ���ݣ����������Բ�Ҫ��
//		CategoryService categoryService =new CategoryServiceImpl();
//		List<Category>list=categoryService.getAllCats();
//		request.setAttribute("allCats", list);
		
		//����ҵ����ѯ������Ʒ��������Ʒ������������������
		ProductService productService= new ProductServiceImpl() ;
		List<Product> list01=productService.findHots();
		List<Product> list02=productService.findNews();
		//���������Ϸ��뵽request��
		request.setAttribute("hots", list01);
		request.setAttribute("news", list02);

		//ת������ʵҳ��
		return "/jsp/index.jsp";
	}

}
