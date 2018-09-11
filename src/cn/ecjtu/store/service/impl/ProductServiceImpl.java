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
		 * 1.创建pageModel对象，目的：计算分页参数 2.关联集合 3.关联url
		 */
		// 统计当前分类下商品个数 。 即sql语句：select count(*)from product where cid=?
		int totalRecords = productDao.findTotalRecords(cid);
		PageModel pm = new PageModel(curNum, totalRecords, 12);
		// 2.关联集合,select* from product where cid=? limit ?,?
		List<Product> list = productDao.findProductByCidWithPage(cid, pm.getStartIndex(), pm.getPageSize());

		pm.setList(list);
		// 关联url
		pm.setUrl("ProductServlet?method=findProductByCidWithPage&cid=" + cid);
		return pm;
	}

	@Override
	public PageModel findAllProductsWithPage(int curNum) {
		// 创建对象
		int totalRecords = productDao.findTotalRecords();
		PageModel pm = new PageModel(curNum, totalRecords, 5);
		// 关联集合select * from product limit ?,?
		List<Product> list = productDao.findAllProductsWithPage(pm.getStartIndex(), pm.getPageSize());
		pm.setList(list);
		// 关联url
		pm.setUrl("AdminProductServlet?method=findAllProductsWithPage");
		return pm;
	}

	@Override
	public void savaProduct(Product product) {
		productDao.savaProduct(product);
		
	}

}
