package cn.ecjtu.store.service.impl;

import java.util.List;

import cn.ecjtu.store.domain.Category;

public interface CategoryService {
	public List<Category> getAllCats();

	public void addCategory(Category c);

	
}
