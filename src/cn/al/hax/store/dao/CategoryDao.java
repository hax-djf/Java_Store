package cn.al.hax.store.dao;

import java.util.List;

import cn.al.hax.store.domain.Category;

public interface CategoryDao {

	List<Category> getAllCats()throws Exception;

	void addCategory(Category c)throws Exception;

}
