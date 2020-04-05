package cn.al.hax.store.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.al.hax.store.domain.PageModel;
import cn.al.hax.store.domain.Product;
import cn.al.hax.store.service.ProductService;
import cn.al.hax.store.service.serviceImp.ProductServiceImp;
import cn.al.hax.store.web.base.BaseServlet;

public class ProductServlet extends BaseServlet {
	private ProductService ProductService=new ProductServiceImp();
	/**
	 * 单个商品信息查询的显示
	 * @param request
	 * @param response
	 * @return 转发
	 * @throws Exception
	 */
	public String findProductByPid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取商品pid
		String pid=request.getParameter("pid");
		//根据商品pid查询商品信息
		ProductService ProductService=new ProductServiceImp();
		Product product=ProductService.findProductByPid(pid);
		//将获取到的商品放入request
		request.setAttribute("product", product);
		//转发到/jsp/product_info.jsp
		return "/jsp/product_info.jsp";
	}
	
	/**
	 * findProductsByCidWithPage(多个商品分页查询信息的显示)
	 * @param request
	 * @param response
	 * @return 转发
	 */
	public String findProductsByCidWithPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取cid,num
		String cid=request.getParameter("cid");
		int curNum=Integer.parseInt(request.getParameter("num"));
		//调用业务层功能:以分页形式查询当前类别下商品信息
		//返回PageModel对象(1_当前页商品信息2_分页3_url)
		PageModel pm=ProductService.findProductsByCidWithPage(cid,curNum);
		//将PageModel对象放入request
		request.setAttribute("page", pm);
		//转发到/jsp/product_list.jsp
		return  "/jsp/product_list.jsp";
	}
	
}
