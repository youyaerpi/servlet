package cn.ecjtu.store.service.impl;

import java.sql.SQLException;
import java.util.List;

import cn.ecjtu.store.domain.PageModel;
import cn.ecjtu.store.domain.Product;
public interface ProductService {
    Product findProductByPid(String pid);
	List<Product> findHots();
	List<Product> findNews();
	PageModel findProductByCidWithPage(String cid, int curNum)throws SQLException;
	PageModel findAllProductsWithPage(int curNum);
	void savaProduct(Product product);

}
