package cn.ecjtu.store.service.impl;

import java.util.List;

import cn.ecjtu.store.dao.impl.CategoryDao;
import cn.ecjtu.store.dao.impl.CategoryDaoImpl;
import cn.ecjtu.store.domain.Category;

public class CategoryServiceImpl implements CategoryService {
	
	CategoryDao categoryDao = new CategoryDaoImpl();
	@Override
	public List<Category> getAllCats() {
		
		return categoryDao.getAllCats();

	}

	@Override
	public void addCategory(Category c) {
		categoryDao.addCategory(c);
		
	}
}
