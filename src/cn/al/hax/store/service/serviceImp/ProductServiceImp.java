package cn.al.hax.store.service.serviceImp;

import java.util.List;

import cn.al.hax.store.dao.ProductDao;
import cn.al.hax.store.dao.daoImp.ProductDaoImp;
import cn.al.hax.store.domain.PageModel;
import cn.al.hax.store.domain.Product;
import cn.al.hax.store.service.ProductService;
import cn.al.hax.store.utils.BeanFactory;

public class ProductServiceImp implements ProductService {
	private ProductDao ProductDao=(ProductDao)BeanFactory.createObject("ProductDao");
	//保存商品信息
	@Override
	public void saveProduct(Product product) throws Exception {
		ProductDao.saveProduct(product);
		
	}
	//根据id查询商品详情信息
	@Override
	public Product findProductByPid(String pid) throws Exception {
		return ProductDao.findProductByPid(pid);
		
	}
	//查询火热的商品
	@Override
	public List<Product> findHots() throws Exception {
		return ProductDao.findHots();
	}
	//查询最新的商品（根据的是商品上架时间来操作的）
	@Override
	public List<Product> findNews() throws Exception {
		return ProductDao.findNews();
	}
	//分类查询商品信息（分页）
	@Override
	public PageModel findProductsByCidWithPage(String cid, int curNum) throws Exception {
		//1_创建PageModel对象 目的:计算分页参数
		//统计当前分类下商品个数  select count(*) from product where cid=?
		int totalRecords=ProductDao.findTotalRecords(cid);
		PageModel pm=new PageModel(curNum,totalRecords,12);
		//2_关联集合 select * from product where cid =? limit ? ,?
		List list=ProductDao.findProductsByCidWithPage(cid,pm.getStartIndex(),pm.getPageSize());
		pm.setList(list);
		//3_关联url
		pm.setUrl("ProductServlet?method=findProductsByCidWithPage&cid="+cid);
		return pm;
	}
	//查询所有的商品信息
	@Override
	public PageModel findAllProductsWithPage(int curNum) throws Exception {
		//1_创建对象
		int totalRecords=ProductDao.findTotalRecords();
		PageModel pm=new PageModel(curNum,totalRecords,5);
		//2_关联集合 select * from product limit ? , ?
		List<Product> list=ProductDao.findAllProductsWithPage(pm.getStartIndex(),pm.getPageSize());
		pm.setList(list);
		//3_关联url
		pm.setUrl("AdminProductServlet?method=findAllProductsWithPage");
		return pm;
	}

}
