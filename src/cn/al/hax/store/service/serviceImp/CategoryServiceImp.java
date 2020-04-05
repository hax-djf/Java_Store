package cn.al.hax.store.service.serviceImp;

import java.util.List;

import cn.al.hax.store.dao.CategoryDao;
import cn.al.hax.store.dao.daoImp.CategoryDaoImp;
import cn.al.hax.store.domain.Category;
import cn.al.hax.store.service.CategoryService;
import cn.al.hax.store.utils.BeanFactory;
import cn.al.hax.store.utils.JedisUtils;
import redis.clients.jedis.Jedis;

public class CategoryServiceImp implements CategoryService {

	private CategoryDao CategoryDao=(CategoryDao)BeanFactory.createObject("CategoryDao");
	//得到所有的分类信息
	@Override
	public List<Category> getAllCats() throws Exception {
		return CategoryDao.getAllCats();
	}
	//添加分类信息
	@Override
	public void addCategory(Category c) throws Exception {
		//本质是向MYSQL插入一条数据
		CategoryDao.addCategory(c);
		//更新redis缓存
		//在添加新的分类以后进行分类的刷新（清除缓冲的操作）
		Jedis jedis = JedisUtils.getJedis();
		jedis.del("allCats");
		JedisUtils.closeJedis(jedis);
	}

}
