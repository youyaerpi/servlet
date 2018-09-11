package cn.ecjtu.store.dao.impl;

import java.sql.SQLException;
import java.util.List;

import cn.ecjtu.store.domain.Product;

public interface ProductDao {

	List<Product> findHots();
	List<Product> findNews();
	Product findProductByPid(String pid);
	int findTotalRecords(String cid) throws SQLException;
	List<Product> findProductByCidWithPage(String cid, int startIndex, int pageSize)throws SQLException;
	int findTotalRecords();
	List<Product> findAllProductsWithPage(int startIndex, int pageSize);
	void savaProduct(Product product);

	

}
