package cn.al.hax.store.dao.daoImp;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.al.hax.store.dao.ProductDao;
import cn.al.hax.store.domain.Product;
import cn.al.hax.store.utils.JDBCUtils;

public class ProductDaoImp implements ProductDao {
	private QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
	/**
	 * 保存商品信息
	 */
	@Override
	public void saveProduct(Product product) throws Exception {
		String sql="INSERT INTO product VALUES(?,?,?,?,?,?,?,?,?,?)";
		Object[] params={product.getPid(),product.getPname(),product.getMarket_price(),product.getShop_price(),product.getPimage(),product.getPdate(),product.getIs_hot(),product.getPdesc(),product.getPflag(),product.getCid()};
		qr.update(sql,params);
	}

	@Override
	/**
	 * 这个是mysql里面支持的分页查询的功能。
	 *startIndex 开始查询的数据地方的索引
	 *pageSize 从开始查询的地方，查询的到指定长度的数据
	 * 返回的是一个product的对象，里面存储着所有的信息
	 */
	public List<Product> findAllProductsWithPage(int startIndex, int pageSize) throws Exception {
		String sql="select * from product order by pdate desc limit  ? , ?";
		return qr.query(sql, new BeanListHandler<Product>(Product.class),startIndex,pageSize);
	}
	
	/**
	 * 查询总的商品数量
	 */
	@Override
	public int findTotalRecords() throws Exception {
		String sql="select count(*) from product";
		Long num=(Long)qr.query(sql, new ScalarHandler());
		return num.intValue();
	}
	/**
	 * 根据id查询商品
	 */
	@Override
	public Product findProductByPid(String pid) throws Exception {
		String sql="select * from product where pid=?";
		return qr.query(sql, new BeanHandler<Product>(Product.class),pid);
	}
	/**
	 * 查询页面比较火热的数据
	 */
	@Override
	public List<Product> findHots() throws Exception {
		String sql="SELECT * FROM product WHERE pflag=0 AND is_hot=1 ORDER BY pdate DESC LIMIT 0 ,9 ";
		return qr.query(sql, new BeanListHandler<Product>(Product.class));
	}
	/**
	 * 查询最新的商品信息
	 */
	@Override
	public List<Product> findNews() throws Exception {
		String sql="SELECT * FROM product WHERE pflag=0 ORDER BY pdate DESC LIMIT 0 , 9 ";
		return qr.query(sql, new BeanListHandler<Product>(Product.class));
		
	}
	/**
	 * 分类查询商品信息（分页）
	 */
	@Override
	public List findProductsByCidWithPage(String cid, int startIndex, int pageSize) throws Exception {
		String sql="select * from product where cid=? limit ? , ?";
		return qr.query(sql, new BeanListHandler<Product>(Product.class),cid,startIndex,pageSize);
	}
	/**
	 *根据商品分类查询总的商品数量
	 */
	@Override
	public int findTotalRecords(String cid) throws Exception {
		String sql="select count(*) from product where cid =?";
		Long num=(Long)qr.query(sql, new ScalarHandler(),cid);
		return num.intValue();
	}

}
