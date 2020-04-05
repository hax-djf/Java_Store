package cn.al.hax.store.dao.daoImp;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.al.hax.store.dao.CategoryDao;
import cn.al.hax.store.domain.Category;
import cn.al.hax.store.utils.JDBCUtils;

public class CategoryDaoImp implements CategoryDao {
	private QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource()); 
	/**
	 * 查询所有的分类信息
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Category> getAllCats() throws Exception {
		String sql="select * from category";
		return qr.query(sql, new BeanListHandler<Category>(Category.class));
		
	}
	/**
	 * 添加分类信息
	 * @param c
	 * @throws Exception
	 */
	@Override
	public void addCategory(Category c) throws Exception {
		String sql="insert into category values (? ,?)";
		qr.update(sql,c.getCid(),c.getCname());
	}
	
}
