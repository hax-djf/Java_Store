package cn.al.hax.store.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.al.hax.store.domain.Product;
import cn.al.hax.store.service.ProductService;
import cn.al.hax.store.service.serviceImp.ProductServiceImp;
import cn.al.hax.store.web.base.BaseServlet;

public class IndexServlet extends BaseServlet {
	/**
	 * 写一个浏览器开启就操作的一个响应操作(分类或者是热门商品的获取操作)
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*
		(分类方案一问题：这个时候只能是在打开index的时候这个会去加载这个servlet,
		然后将响应的数据设置页面进行显示操作，但是只能在这个页面出啦,为了让所有的页面
		都可以去加载到这个分类的话,并且不能使用session,我们需要使用Ajax技术在页面
		header.jsp加载完毕以后，进行一个异步处理的操作,每次加载都会去响应这个页面，
		数据的返回使用json技术，将返回的分类信息添加到一个json对象中,返回给客户端，
		客户端解析数据将数据输出到页面)(这个地方需要在categoryServlet实现)
		categoryService service=new categoryService();
		List<Category> catelist=service.findByCategory();
		//得到数据进行保存
		req.setAttribute("catelist", catelist);
		//转发这个页面
		return "f:/jsp/index.jsp";
		*/
		/*功能：现在需要对主页面中热门商品和最新的商品进行一个查询
		 * 实现思路：需要进行对商品的热度判断，需要对商品的上架时间
		 * 一共是查询9件商品热门
		 *  `pdate` date DEFAULT NULL,			#上架时间
  			`is_hot` int(11) DEFAULT NULL,		#是否热门：0=不热门,1=热门
		 */
		
		//调用业务层查询最新商品,查询最热商品,返回2个集合
		ProductService ProductService=new ProductServiceImp();
		List<Product> list01=ProductService.findHots();
		List<Product> list02=ProductService.findNews();
		//将2个集合放入到request
		request.setAttribute("hots", list01);
		request.setAttribute("news", list02);
		//转发到真实的首页
		return "/jsp/index.jsp";
	}
}
