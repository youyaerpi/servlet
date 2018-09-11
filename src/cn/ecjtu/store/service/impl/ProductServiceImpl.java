package cn.ecjtu.store.service.impl;

import java.sql.SQLException;
import java.util.List;

import cn.ecjtu.store.dao.impl.ProductDao;
import cn.ecjtu.store.dao.impl.ProductDaoImpl;
import cn.ecjtu.store.domain.PageModel;
import cn.ecjtu.store.domain.Product;

public class ProductServiceImpl implements ProductService {
	ProductDao productDao = new ProductDaoImpl();

	@Override
	public List<Product> findHots() {
		List<Product> findHots = productDao.findHots();
		return findHots;
	}

	@Override
	public List<Product> findNews() {
		List<Product> findNews = productDao.findNews();
		return findNews;
	}

	@Override
	public Product findProductByPid(String pid) {
		Product product = productDao.findProductByPid(pid);
		return product;
	}

	@Override
	public PageModel findProductByCidWithPage(String cid, int curNum) throws SQLException {
		/**
		 * 1.����pageModel����Ŀ�ģ������ҳ���� 2.�������� 3.����url
		 */
		// ͳ�Ƶ�ǰ��������Ʒ���� �� ��sql��䣺select count(*)from product where cid=?
		int totalRecords = productDao.findTotalRecords(cid);
		PageModel pm = new PageModel(curNum, totalRecords, 12);
		// 2.��������,select* from product where cid=? limit ?,?
		List<Product> list = productDao.findProductByCidWithPage(cid, pm.getStartIndex(), pm.getPageSize());

		pm.setList(list);
		// ����url
		pm.setUrl("ProductServlet?method=findProductByCidWithPage&cid=" + cid);
		return pm;
	}

	@Override
	public PageModel findAllProductsWithPage(int curNum) {
		// ��������
		int totalRecords = productDao.findTotalRecords();
		PageModel pm = new PageModel(curNum, totalRecords, 5);
		// ��������select * from product limit ?,?
		List<Product> list = productDao.findAllProductsWithPage(pm.getStartIndex(), pm.getPageSize());
		pm.setList(list);
		// ����url
		pm.setUrl("AdminProductServlet?method=findAllProductsWithPage");
		return pm;
	}

	@Override
	public void savaProduct(Product product) {
		productDao.savaProduct(product);
		
	}

}
