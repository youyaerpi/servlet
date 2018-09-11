package cn.ecjtu.store.dao.impl;

import java.util.List;

import cn.ecjtu.store.domain.Category;

public interface CategoryDao {

	List<Category> getAllCats();

	void addCategory(Category c);

}
