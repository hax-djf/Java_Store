package cn.al.hax.store.dao.daoImp;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.al.hax.store.dao.OrderDao;
import cn.al.hax.store.domain.Order;
import cn.al.hax.store.domain.OrderItem;
import cn.al.hax.store.domain.Product;
import cn.al.hax.store.domain.User;
import cn.al.hax.store.utils.JDBCUtils;
public class OrderDaoImp implements OrderDao {
	private QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
	/**
	 * 查询所有的订单
	 */
	@Override
	public List<Order> findAllOrders() throws Exception {
		String sql="select * from orders";
		return qr.query(sql, new BeanListHandler<Order>(Order.class));
		
	}
	/**
	 * 修改订单信息
	 */
	@Override
	public void updateOrder(Order order) throws Exception {
		String sql="UPDATE orders SET ordertime=? ,total=? ,state= ?, address=?,NAME=?, telephone =? WHERE oid=?";
		Object[] params={order.getOrdertime(),order.getTotal(),order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getOid()};
		qr.update(sql,params);
		
	}
	/**
	 *查询订单的状态(查找某种状态的下的订单（如支付未支付）)
	 */
	@Override
	public List<Order> findAllOrders(String st) throws Exception {
		String sql="select * from orders where state= ?";
		return qr.query(sql, new BeanListHandler<Order>(Order.class),st);
	}

	@Override
	/**
	 * 进行所有的订单信息的查询操作
	 * @param user 用户
	 * @param startIndex limit查询的开始地方
	 * @param pageSize 查询的页数
	 * @return
	 * @throws Exception 
	 */
	public List findMyOrdersWithPage(User user, int startIndex, int pageSize) throws Exception {
		String sql="select * from orders where uid=? limit ? , ?";
		List<Order> list=qr.query(sql, new BeanListHandler<Order>(Order.class),user.getUid(),startIndex,pageSize);
		
		//遍历所有订单
		for (Order order : list) {
			//获取到每笔订单oid   查询每笔订单下的订单项以及订单项对应的商品信息
			String oid=order.getOid();
			sql="select * from orderItem o ,product p where o.pid=p.pid and oid=?";
			List<Map<String, Object>> list02 = qr.query(sql, new MapListHandler(),oid);
			//遍历list
			for (Map<String, Object> map : list02) {
				OrderItem orderItem=new OrderItem();
				Product product=new Product();
				// 由于BeanUtils将字符串"1992-3-3"向user对象的setBithday();方法传递参数有问题,手动向BeanUtils注册一个时间类型转换器
				// 1_创建时间类型的转换器
				DateConverter dt = new DateConverter();
				// 2_设置转换的格式
				dt.setPattern("yyyy-MM-dd");
				// 3_注册转换器
				ConvertUtils.register(dt, java.util.Date.class);
				
				//将map中属于orderItem的数据自动填充到orderItem对象上
				BeanUtils.populate(orderItem, map);
				//将map中属于product的数据自动填充到product对象上
				BeanUtils.populate(product, map);
				
				//让每个订单项和商品发生关联关系
				orderItem.setProduct(product);
				//将每个订单项存入订单下的集合中
				order.getList().add(orderItem);
				
			}
			//第二种形式
			//这个里面数据存储的格式为键值对的信息存储着，一个字段对应一个参数
			//遍历里面的所有的数据
			//for (Map<String, Object> map : list_ItemAndp) {
			//使用反射的技术,将map里面对应的元素赋值到对应的类中去
			//product product=MyBeanUtils.populateObject(product.class, map);
			//orderItem populateObject = MyBeanUtils.populateObject(orderItem.class, map);
			//将其设置到每一个order中去
			//让每个订单项和商品发生关联关系
			//populateObject.setProduct(product);
			//将每个订单项存入订单下的集合中
			//order.getListorder().add(populateObject);
			//}	
		}
		return list;
	}
	/**
	 * 查询到某个订单的详情信息
	 */
	@Override
	public Order findOrderByOid(String oid) throws Exception {
		String sql="select * from orders where oid= ?";
		Order order=qr.query(sql, new BeanHandler<Order>(Order.class),oid);
		
		//根据订单id查询订单下所有的订单项以及订单项对应的商品信息
		sql="select * from orderitem o, product p where o.pid=p.pid and oid=?";
		List<Map<String, Object>> list02 = qr.query(sql, new MapListHandler(),oid);
		//遍历list
		for (Map<String, Object> map : list02) {
			OrderItem orderItem=new OrderItem();
			Product product=new Product();
			// 由于BeanUtils将字符串"1992-3-3"向user对象的setBithday();方法传递参数有问题,手动向BeanUtils注册一个时间类型转换器
			// 1_创建时间类型的转换器
			DateConverter dt = new DateConverter();
			// 2_设置转换的格式
			dt.setPattern("yyyy-MM-dd");
			// 3_注册转换器
			ConvertUtils.register(dt, java.util.Date.class);
			
			//将map中属于orderItem的数据自动填充到orderItem对象上
			BeanUtils.populate(orderItem, map);
			//将map中属于product的数据自动填充到product对象上
			BeanUtils.populate(product, map);
			
			//让每个订单项和商品发生关联关系
			orderItem.setProduct(product);
			//将每个订单项存入订单下的集合中
			order.getList().add(orderItem);
		}
		return order;
	}
	
	/**
	 * 得到某个用户的总的订单数量
	 */
	@Override
	public int getTotalRecords(User user) throws Exception {
		String sql="select count(*) from orders where uid=?";
		Long num=(Long)qr.query(sql, new ScalarHandler(),user.getUid());
		return num.intValue();
	}
	/**
	 * 保存订单（事务处理）
	 */
	@Override
	public void saveOrder(Connection conn, Order order) throws Exception {
		String sql="INSERT INTO orders VALUES(?,?,?,?,?,?,?,?)";
		QueryRunner cqr=new QueryRunner();
		Object[] params={order.getOid(),order.getOrdertime(),order.getTotal(),order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getUser().getUid()};
		cqr.update(conn,sql,params);
	}
	/**
	 * 保存订单项（事务）
	 */
	@Override
	public void saveOrderItem(Connection conn, OrderItem item) throws Exception {
		String sql="INSERT INTO orderitem VALUES(?,?,?,?,?)";
		QueryRunner cqr=new QueryRunner();
		Object[] params={item.getItemid(),item.getQuantity(),item.getTotal(),item.getProduct().getPid(),item.getOrder().getOid()};
		cqr.update(conn,sql,params);
	}

}
