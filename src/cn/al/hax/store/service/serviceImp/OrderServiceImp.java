package cn.al.hax.store.service.serviceImp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import cn.al.hax.store.dao.OrderDao;
import cn.al.hax.store.dao.daoImp.OrderDaoImp;
import cn.al.hax.store.domain.Order;
import cn.al.hax.store.domain.OrderItem;
import cn.al.hax.store.domain.PageModel;
import cn.al.hax.store.domain.User;
import cn.al.hax.store.service.OrderService;
import cn.al.hax.store.utils.JDBCUtils;

public class OrderServiceImp implements OrderService {
	
	private OrderDao orderDao=new OrderDaoImp();
	//查询所有的商品
	@Override
	public List<Order> findAllOrders() throws Exception {
		return orderDao.findAllOrders();
	}
	//查询订单的状态(查找某种状态的下的订单（如支付未支付）)
	@Override
	public List<Order> findAllOrders(String st) throws Exception {
		return orderDao.findAllOrders(st);
	}
	//保存订单信息
	@Override
	public void saveOrder(Order order) throws SQLException {
		//（第一种方式）保存订单和订单下所有的订单项(同时成功,失败)
		/*try {
			JDBCUtils.startTransaction();
			OrderDao orderDao=new OrderDaoImp();
			orderDao.saveOrder(order);
			for(OrderItem item:order.getList()){
				orderDao.saveOrderItem(item);
			}
			JDBCUtils.commitAndClose();
		} catch (Exception e) {
			JDBCUtils.rollbackAndClose();
		}
		*/
		//第二种方式
		Connection conn=null;
		try {
			//获取连接
			conn=JDBCUtils.getConnection();
			//开启事务
			conn.setAutoCommit(false);
			//保存订单
			
			orderDao.saveOrder(conn,order);
			//保存订单项
			for (OrderItem item : order.getList()) {
				orderDao.saveOrderItem(conn,item);	
			}
			//提交
			conn.commit();
		} catch (Exception e) {
			//回滚
			conn.rollback();
		}
	}
	//分页查询（每一个用户的订单列表）
	@Override
	public PageModel findMyOrdersWithPage(User user, int curNum) throws Exception {
		//1_创建PageModel对象,目的:计算并且携带分页参数
		//select count(*) from orders where uid=?
		int totalRecords=orderDao.getTotalRecords(user);
		PageModel pm=new PageModel(curNum, totalRecords, 3);
		//2_关联集合  select * from orders where uid=? limit ? ,?
		List list=orderDao.findMyOrdersWithPage(user,pm.getStartIndex(),pm.getPageSize());
		pm.setList(list);
		//3_关联url绑定
		pm.setUrl("OrderServlet?method=findMyOrdersWithPage");
		return pm;
	}
	// 根据oid查询订单详情信息
	@Override
	public Order findOrderByOid(String oid) throws Exception {
		return orderDao.findOrderByOid(oid);
		
	}
	//修改订单信息
	@Override
	public void updateOrder(Order order) throws Exception {
		orderDao.updateOrder(order);
		
	}
}