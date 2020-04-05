package cn.al.hax.store.web.servlet;

import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.al.hax.store.domain.Order;
import cn.al.hax.store.service.OrderService;
import cn.al.hax.store.service.serviceImp.OrderServiceImp;
import cn.al.hax.store.web.base.BaseServlet;
import net.sf.json.JSONArray;

public class AdminOrderServlet extends BaseServlet {
	private OrderService OrderService=new OrderServiceImp();
	/**
	 * findOrders 管理员查询所有的订单信息
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String findOrders(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String st=req.getParameter("state");
		List<Order> list=null;
		if(null==st||"".equals(st)){
			//获取到全部订单
			list=OrderService.findAllOrders();			
		}else{
			list=OrderService.findAllOrders(st);
		}
		//将全部订单放入request
		req.setAttribute("allOrders", list);
		//转发 /admin/order/list.jsp
		return "/admin/order/list.jsp";
	}
	/**
	 * findOrderByOidWithAjax 
	 * 是以ajax进行某个订单下的有的订单项和商品信息的获取 json数据格式返回
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String findOrderByOidWithAjax(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//服务端获取到订单ID,
		String oid=req.getParameter("id");
		//查询这个订单下所有的订单项以及订单项对应的商品信息,返回集合
		Order order=OrderService.findOrderByOid(oid);
		//将返回的集合转换为JSON格式字符串,响应到客户端
		String jsonStr=JSONArray.fromObject(order.getList()).toString();
		//响应到客户端
		resp.setContentType("application/json;charset=utf-8");
		resp.getWriter().println(jsonStr);
		return null;
	}
	
	/**
	 * updateOrderByOid 根据订单编号进行订单状态的修改
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String updateOrderByOid(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取订单ID
		String oid=req.getParameter("oid");
		//根据订单ID查询订单
		OrderService OrderService=new OrderServiceImp();
		Order order=OrderService.findOrderByOid(oid);
		//设置订单状态
		order.setState(3);
		//修改订单信息
		OrderService.updateOrder(order);
		//重新定向到查询已发货订单
		resp.sendRedirect(req.getContextPath()+"/AdminOrderServlet?method=findOrders&state=3");
		return null;
	}	
}
