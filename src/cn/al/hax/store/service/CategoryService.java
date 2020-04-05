package cn.al.hax.store.service;

import java.util.List;

import cn.al.hax.store.domain.Category;

public interface CategoryService {

	List<Category> getAllCats()throws Exception;

	void addCategory(Category c)throws Exception;

}
