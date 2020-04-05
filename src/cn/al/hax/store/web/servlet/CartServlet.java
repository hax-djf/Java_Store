package cn.al.hax.store.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.al.hax.store.domain.Cart;
import cn.al.hax.store.domain.CartItem;
import cn.al.hax.store.domain.Product;
import cn.al.hax.store.service.ProductService;
import cn.al.hax.store.service.serviceImp.ProductServiceImp;
import cn.al.hax.store.web.base.BaseServlet;

public class CartServlet extends BaseServlet {
	private ProductService ProductService=new ProductServiceImp();
	/**
	 * 添加购物项到购物车
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String addCartItemToCart(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//从session获取购物车
		Cart cart=(Cart)req.getSession().getAttribute("cart");
		if(null==cart){
		  //如果获取不到,创建购物车对象,放在session中
			cart=new Cart();
			req.getSession().setAttribute("cart", cart);
		}
   		//如果获取到,使用即可
		//获取到商品id,数量
		String pid=req.getParameter("pid");
		int num=Integer.parseInt(req.getParameter("quantity"));
		//通过商品id查询都商品对象
		Product product=ProductService.findProductByPid(pid);	
		//获取到待购买的购物项
		CartItem cartItem=new CartItem();
		cartItem.setNum(num);
		cartItem.setProduct(product);

		//调用购物车上的方法
		cart.addCartItemToCar(cartItem);
		
		//重定向到/jsp/cart.jsp
		resp.sendRedirect(req.getContextPath()+"/jsp/cart.jsp");
		//return "/jsp/cart.jsp";
		return  null;
	}
	
	/**
	 * removeCartItem
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String removeCartItem(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取待删除商品pid
		String pid=req.getParameter("id");
		//获取到购物车
		Cart cart=(Cart)req.getSession().getAttribute("cart");
		//调用购物车删除购物项方法
		cart.removeCartItem(pid);
		//重定向到/jsp/cart.jsp
		resp.sendRedirect(req.getContextPath()+"/jsp/cart.jsp");
		return null;
	}
	
	/**
	 * clearCart 清空购物车
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String clearCart(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取购物车
		Cart cart=(Cart)req.getSession().getAttribute("cart");
		//调用购物车上的清空购物车方法
		cart.clearCart();
		//重新定向到/jsp/cart.jsp
		resp.sendRedirect(req.getContextPath()+"/jsp/cart.jsp");
		return null;
	}
}




